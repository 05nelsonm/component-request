package io.matthewnelson.component.request.feature

import io.matthewnelson.component.request.concept.Request
import io.matthewnelson.component.request.feature.drivers.CachedRequestDriver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class RequestCollectorUnitTest {

    private val driver = CachedRequestDriver<Any>(5)

    @Test
    fun givenDriver_whenStartStopCycled_replayedRequestsAreNotExecutedTwice() =
        runTest {
            val dispatcher = StandardTestDispatcher(testScheduler)

            val executedRequests = ArrayList<Request<Any>>(3)
            val collector = RequestCollector(
                driverProvider = { driver },
                instanceProvider = { Any() },
                onPostRequestExecution = { request ->
                    executedRequests.add(request)
                }
            )

            collector.startCollecting(this, dispatcher)

            val request = object : Request<Any>() {
                var executions = 0
                override fun execute(value: Any) {
                    this.executions++
                }
            }

            driver.submitRequest(request)
            delay(100L)

            assertEquals(1, request.executions)
            assertEquals(1, executedRequests.size)

            driver.submitRequest(request)
            delay(100L)

            assertEquals(2, request.executions)
            assertEquals(2, executedRequests.size)

            // same request
            assertTrue { executedRequests[0] == executedRequests[1] }

            collector.stopCollecting()

            // no longer collecting, but request was still added
            driver.submitRequest(request)
            delay(100L)

            assertEquals(2, request.executions)
            assertEquals(2, executedRequests.size)

            // execution of un-executed requests _only_ upon replay
            collector.startCollecting(this, dispatcher)
            delay(100L)

            assertEquals(3, request.executions)
            assertEquals(3, executedRequests.size)

            collector.stopCollecting()
        }
}
