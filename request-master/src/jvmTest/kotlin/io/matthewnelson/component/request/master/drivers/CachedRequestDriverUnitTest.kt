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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
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
}