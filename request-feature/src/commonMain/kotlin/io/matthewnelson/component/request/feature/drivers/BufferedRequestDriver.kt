/*
 * Copyright (c) 2021 Matthew Nelson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
package io.matthewnelson.component.request.feature.drivers

import io.matthewnelson.component.request.feature.util.RequestHolder
import io.matthewnelson.component.request.concept.Request
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.job
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

// TODO: Rework
internal open class BufferedRequestDriver<T: Any>(
    private val whenTrueExecuteRequest: (suspend (instance: T, request: Request<T>) -> Boolean)? = null
): RequestDriver<T>() {

    private val stateFlow: MutableStateFlow<MutableSharedFlow<RequestHolder<T>>?> by lazy {
        MutableStateFlow(null)
    }

    private val sharedFlowCreationLock = Mutex()
    override suspend fun collect(action: suspend (value: RequestHolder<T>) -> Unit) {
        currentCoroutineContext().job.invokeOnCompletion {
            stateFlow.value?.subscriptionCount?.value?.let { subs ->
                if (subs < 1) {
                    stateFlow.value = null
                }
            }
        }

        stateFlow.collect { sharedFlow ->
            if (sharedFlow == null) {
                sharedFlowCreationLock.withLock {
                    if (stateFlow.value == null) {
                        stateFlow.value = MutableSharedFlow<RequestHolder<T>>(
                            replay = 0,
                            extraBufferCapacity = 0,
                            onBufferOverflow = BufferOverflow.SUSPEND,
                        )
                    }
                }
            } else {
                sharedFlow.collect { holder ->
                    action.invoke(holder)
                }
            }
        }
    }

    override suspend fun executeRequest(instance: T, holder: RequestHolder<T>): Boolean {
        if (whenTrueExecuteRequest?.invoke(instance, holder.request) == false) {
            return false
        }

        holder.request.execute(instance)
        return true
    }

    private val submissionLock = Mutex()
    override suspend fun submitRequest(request: Request<T>) {
        submissionLock.withLock {
            try {
                stateFlow.collect { sharedFlow ->
                    if (sharedFlow != null) {
                        delay(10L)
                        sharedFlow.emit(RequestHolder(request))
                        throw Exception()
                    }
                }
            } catch (_: Exception) {}
        }
    }
}
