package io.matthewnelson.component.request.master.drivers

import io.matthewnelson.component.request.master.drivers.BufferedRequestDriver
import io.matthewnelson.component.request.slave.Request
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@Suppress("EXPERIMENTAL_API_USAGE")
class BufferedRequestDriverUnitTest {

    private val driver = BufferedRequestDriver<Any>()
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
    fun submissionIsSuspendedWhileNoCollection() =
        testDispatcher.runBlockingTest {
            var submissions = 0

            val submissionJob: Job = launch {
                driver.submitRequest(object : Request<Any>() {
                    override fun execute(value: Any) {
                        submissions++
                    }
                })
            }

            val submissionJob2: Job = launch {
                driver.submitRequest(object : Request<Any>() {
                    override fun execute(value: Any) {
                        submissions++
                    }
                })
            }

            delay(500L)

            // ensure submission is still suspending until collection
            Assert.assertTrue(submissionJob.isActive)
            Assert.assertTrue(submissionJob2.isActive)
            Assert.assertEquals(0, submissions)

            val collectionJob1: Job = launch {
                driver.collect { request ->
                    driver.executeRequest(Any(), request)
                    currentCoroutineContext().job.cancel()
                }
            }

            submissionJob.join()
            Assert.assertEquals(1, submissions)
            Assert.assertFalse(collectionJob1.isActive)

            // ensure job2 is still suspending
            delay(500)
            Assert.assertTrue(submissionJob2.isActive)

            val collectionJob2: Job = launch {
                driver.collect { request ->
                    driver.executeRequest(Any(), request)
                    currentCoroutineContext().job.cancel()
                }
            }

            submissionJob2.join()
            Assert.assertEquals(2, submissions)
            Assert.assertFalse(collectionJob2.isActive)
        }
    
    @Test
    fun stateFlowIsClearedOnlyWhenLastSubscriberCompletes() =
        testDispatcher.runBlockingTest {
            
        }
}