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
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class BufferedRequestDriverUnitTest {

    private val driver = BufferedRequestDriver<Any>()

    @Test
    fun givenSubmission_whenNoCollectors_suspendsUntilCollectorStarts() =
        runTest {
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
            assertTrue(submissionJob.isActive)
            assertTrue(submissionJob2.isActive)
            assertEquals(0, submissions)

            val collectionJob1: Job = launch {
                driver.collect { request ->
                    driver.executeRequest(Any(), request)
                    currentCoroutineContext().job.cancel()
                }
            }

            submissionJob.join()
            assertEquals(1, submissions)
            assertFalse(collectionJob1.isActive)

            // ensure job2 is still suspending
            delay(500)
            assertTrue(submissionJob2.isActive)

            val collectionJob2: Job = launch {
                driver.collect { request ->
                    driver.executeRequest(Any(), request)
                    currentCoroutineContext().job.cancel()
                }
            }

            submissionJob2.join()
            assertEquals(2, submissions)
            assertFalse(collectionJob2.isActive)
        }
}