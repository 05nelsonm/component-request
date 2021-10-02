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
package io.matthewnelson.component.request.master.drivers

import io.matthewnelson.component.request.slave.Request
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@Suppress("EXPERIMENTAL_API_USAGE")
class CachedRequestDriverUnitTest {

    private val driver = CachedRequestDriver<Any>(5)
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun executeRequestReturnsFalseIfAlreadyExecuted() =
        testDispatcher.runBlockingTest {
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
            assertEquals(1, executions)

            driver.submitRequest(request)
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

            assertEquals(2, executions)
            assertEquals(2, replayNoExecute)

            driver.submitRequest(request)
            assertEquals(3, executions)

            collectionJob.cancel()
        }

    @Test
    fun requestExecutionOnlyWhenCoroutineIsActive() =
        testDispatcher.runBlockingTest {

            val whenTrueExecuteSuspend = MutableStateFlow(false)

            val localDriver = CachedRequestDriver<Any>(replayCacheSize = 5,) { _, _ ->
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
                true
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
            Assert.assertEquals(0, executions)
            Assert.assertEquals(0, notExecuted)

            // Proc execution
            whenTrueExecuteSuspend.value = true

            delay(500L)

            // Ensure execution was had
            Assert.assertEquals(1, executions)
            Assert.assertEquals(0, notExecuted)

            // suspension was reset to false
            Assert.assertFalse(whenTrueExecuteSuspend.value)

            // submission 2
            localDriver.submitRequest(request)

            // submission 3
            localDriver.submitRequest(request)
            delay(500L)

            // Ensure still suspending
            Assert.assertEquals(1, executions)
            Assert.assertEquals(0, notExecuted)

            collectionJob.cancelAndJoin()

            // Ensure no execution due to cancellation
            Assert.assertEquals(1, executions)

            // cancellation of collectorJob should only complete code block
            // for submission 2 returning false due to Job.isActive == false.
            // submission 3 should not be collected and passed to driver for execution.
            Assert.assertEquals(1, notExecuted)

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
            Assert.assertEquals(1, executions)

            // submission 1 in cache was replayed but not executed again
            Assert.assertEquals(2, notExecuted)

            // Cycle StateFlow for submission 2 from replay cache
            whenTrueExecuteSuspend.value = true
            delay(200L)
            Assert.assertFalse(whenTrueExecuteSuspend.value)

            // Ensure earlier request held in cache was properly executed upon re-collection.
            Assert.assertEquals(2, executions)
            Assert.assertEquals(2, notExecuted)

            // Cycle StateFlow for submission 3 from replay cache
            whenTrueExecuteSuspend.value = true
            delay(200L)

            // Ensure submission 2 & 3 were executed
            Assert.assertEquals(3, executions)

            // submission 4
            localDriver.submitRequest(request)
            whenTrueExecuteSuspend.value = true
            delay(100L)

            // Additional submission 4 executed properly
            Assert.assertEquals(4, executions)
            Assert.assertEquals(2, notExecuted)

            collectionJob.cancelAndJoin()
        }
}