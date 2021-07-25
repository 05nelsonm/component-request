package io.matthewnelson.component.request.master

import io.matthewnelson.component.request.master.drivers.CachedRequestDriver
import io.matthewnelson.component.request.slave.Request
import kotlinx.coroutines.Dispatchers
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
            val observerJob = launch(testDispatcher) {
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
