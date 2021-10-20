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

import io.matthewnelson.component.request.feature.drivers.CachedRequestDriver
import io.matthewnelson.component.request.concept.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
@Suppress("EXPERIMENTAL_API_USAGE")
class RequestCollectorUnitTest {

    private val driver = CachedRequestDriver<Any>(5)
    private val testDispatcher = TestCoroutineDispatcher()

    private val postRequestExecutionSharedFlow: MutableSharedFlow<Request<Any>?> by lazy {
        MutableSharedFlow(0, 1)
    }

    private val collector = RequestCollector(
        driverProvider = { driver },
        instanceProvider = { Any() },
        onPostRequestExecution = { request -> postRequestExecutionSharedFlow.emit(request) }
    )

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
    fun startStopExecutesUnExecutedRequestsOnlyFromReplay() =
        testDispatcher.runBlockingTest {
            collector.startCollecting(this, testDispatcher)

            // observe executed requests via callback
            val executedRequests = ArrayList<Request<Any>>(3)
            val observerJob = launch {
                postRequestExecutionSharedFlow.collect { request ->
                    if (request != null) {
                        executedRequests.add(request)
                    }
                }
            }

            val request = object : Request<Any>() {
                var executions = 0
                override fun execute(value: Any) {
                    this.executions++
                }
            }

            driver.submitRequest(request)

            assertEquals(1, request.executions)
            assertEquals(1, executedRequests.size)

            driver.submitRequest(request)

            assertEquals(2, request.executions)
            assertEquals(2, executedRequests.size)

            // same request
            assertTrue { executedRequests[0] == executedRequests[1] }

            collector.stopCollecting()

            // no longer collecting, but request was still added
            driver.submitRequest(request)
            assertEquals(2, request.executions)
            assertEquals(2, executedRequests.size)

            // execution of un-executed requests _only_ upon replay
            collector.startCollecting(this, testDispatcher)
            assertEquals(3, request.executions)
            assertEquals(3, executedRequests.size)

            collector.stopCollecting()
            observerJob.cancel()
        }
}
