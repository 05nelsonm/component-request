/*
*  Copyright 2021 Matthew Nelson
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
* */
package io.matthewnelson.component.request.feature

import io.matthewnelson.component.request.feature.drivers.RequestDriver
import io.matthewnelson.component.request.concept.Request
import kotlinx.coroutines.*

open class RequestCollector<T: Any>(
    protected val driverProvider: () -> RequestDriver<T>,
    protected val instanceProvider: () -> T,
    protected val onPostRequestExecution: (suspend (Request<T>) -> Unit)? = null,
) {

    @Suppress("MemberVisibilityCanBePrivate")
    protected var collectionJob: Job? = null

    open fun startCollecting(scope: CoroutineScope, dispatcher: CoroutineDispatcher) {
        val driver = driverProvider.invoke()

        collectionJob = scope.launch(dispatcher) {
            driver.collect { holder ->
                if (driver.executeRequest(instanceProvider.invoke(), holder)) {
                    onPostRequestExecution?.invoke(holder.request)
                }
            }
        }
    }

    open fun stopCollecting() {
        collectionJob?.cancel()
    }
}
