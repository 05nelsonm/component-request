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

import io.matthewnelson.component.request.concept.Request
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

@OptIn(ExperimentalCoroutinesApi::class)
class CachedRequestDriverUnitTest {

    private val driver = CachedRequestDriver<Any>(5)

    @Test
    fun givenAlreadyExecutedRequest_whenExecuteCalledAgain_returnsFalse() =
        runTest {
            var executions = 0
            var replayNoExecute = 0

            var collectionJob: Job = launch {
                driver.collect { request ->
                    driver.executeRequest(Any(), request).let {
                        if (it) {
                            executions++
                        } else {
                            replayNoExecute++
                        }
                    }
                }
            }

            val request = object : Request<Any>() {
                override fun execute(value: Any) {}
            }

            driver.submitRequest(request)
            delay(100L)
            assertEquals(1, executions)

            driver.submitRequest(request)
            delay(100L)
            assertEquals(2, executions)

            collectionJob.cancel()

            collectionJob = launch {
                driver.collect { request ->
                    driver.executeRequest(Any(), request).let {
                        if (it) {
                            executions++
                        } else {
                            replayNoExecute++
                        }
                    }
                }
            }
            delay(100L)

            assertEquals(2, executions)
            assertEquals(2, replayNoExecute)

            driver.submitRequest(request)
            delay(100L)
            assertEquals(3, executions)

            collectionJob.cancel()
        }

    @Test
    fun givenRequest_whenCoroutineIsCancelled_requestExecutionIsInhibited() =
        runTest {

            val whenTrueExecuteSuspend = MutableStateFlow(false)

            val localDriver = object : CachedRequestDriver<Any>(replayCacheSize = 5) {

                override suspend fun whenTrueExecuteRequest(
                    instance: Any,
                    request: Request<Any>
                ): Boolean {
                    try {
                        // suspend until cancelled or StateFlow changes to true to continue
                        whenTrueExecuteSuspend.collect {
                            if (it) {
                                throw Exception()
                            }
                        }
                    } catch (_: Exception) {}

                    whenTrueExecuteSuspend.value = false

                    // return true to execute request
                    return true
                }
            }

            var executions = 0
            var notExecuted = 0

            var collectionJob: Job = launch {
                localDriver.collect { request ->
                    localDriver.executeRequest(Any(), request).let {
                        if (!it) {
                            notExecuted++
                        }
                    }
                }
            }

            val request = object : Request<Any>() {
                override fun execute(value: Any) {
                    executions++
                }
            }

            // submission 1
            localDriver.submitRequest(request)
            delay(500L)

            // Ensure still suspending
            assertEquals(0, executions)
            assertEquals(0, notExecuted)

            // Proc execution
            whenTrueExecuteSuspend.value = true

            delay(500L)

            // Ensure execution was had
            assertEquals(1, executions)
            assertEquals(0, notExecuted)

            // suspension was reset to false
            assertFalse(whenTrueExecuteSuspend.value)

            // submission 2
            localDriver.submitRequest(request)

            // submission 3
            localDriver.submitRequest(request)
            delay(500L)

            // Ensure still suspending
            assertEquals(1, executions)
            assertEquals(0, notExecuted)

            collectionJob.cancelAndJoin()

            // Ensure no execution due to cancellation
            assertEquals(1, executions)

            // cancellation of collectorJob should only complete code block
            // for submission 2 returning false due to Job.isActive == false.
            // submission 3 should not be collected and passed to driver for execution.
            assertEquals(1, notExecuted)

            collectionJob = launch {
                localDriver.collect { request ->
                    localDriver.executeRequest(Any(), request).let {
                        if (!it) {
                            notExecuted++
                        }
                    }
                }
            }
            delay(500L)

            // Ensure previously submitted, not executed due to cancellation, request
            // is still un-executed.
            assertEquals(1, executions)

            // submission 1 in cache was replayed but not executed again
            assertEquals(2, notExecuted)

            // Cycle StateFlow for submission 2 from replay cache
            whenTrueExecuteSuspend.value = true
            delay(200L)
            assertFalse(whenTrueExecuteSuspend.value)

            // Ensure earlier request held in cache was properly executed upon re-collection.
            assertEquals(2, executions)
            assertEquals(2, notExecuted)

            // Cycle StateFlow for submission 3 from replay cache
            whenTrueExecuteSuspend.value = true
            delay(200L)

            // Ensure submission 2 & 3 were executed
            assertEquals(3, executions)

            // submission 4
            localDriver.submitRequest(request)
            whenTrueExecuteSuspend.value = true
            delay(100L)

            // Additional submission 4 executed properly
            assertEquals(4, executions)
            assertEquals(2, notExecuted)

            collectionJob.cancelAndJoin()
        }
}