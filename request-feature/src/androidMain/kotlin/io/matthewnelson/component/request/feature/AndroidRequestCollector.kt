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
package io.matthewnelson.component.request.feature

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import io.matthewnelson.component.request.feature.drivers.RequestDriver
import io.matthewnelson.component.request.concept.Request
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * A collector for Android that hooks into the lifecycle owner to start
 * and stop collection with LCE's as defined by [eventsToObserve] argument.
 * */
class AndroidRequestCollector<T: Any>(
    driverProvider: () -> RequestDriver<T>,
    instanceProvider: () -> T,
    onPostRequestExecution: (suspend (Request<T>) -> Unit)? = null,
    val eventsToObserve: Events = Events.StartStop,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
): RequestCollector<T>(
    driverProvider,
    instanceProvider,
    onPostRequestExecution,
) {

    enum class Events {
        StartStop,
        ResumePause,
        CreateDestroy
    }

    private inner class CollectionObserver: DefaultLifecycleObserver {

        // Events.CreateDestroy
        override fun onCreate(owner: LifecycleOwner) {
            super.onCreate(owner)
            if (eventsToObserve == Events.CreateDestroy) {
                startCollecting(owner.lifecycleScope, dispatcher)
            }
        }
        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
            if (eventsToObserve == Events.CreateDestroy) {
                stopCollecting()
            }
        }

        // Events.StartStop
        override fun onStart(owner: LifecycleOwner) {
            super.onStart(owner)
            if (eventsToObserve == Events.StartStop) {
                startCollecting(owner.lifecycleScope, dispatcher)
            }
        }
        override fun onStop(owner: LifecycleOwner) {
            super.onStop(owner)
            if (eventsToObserve == Events.StartStop) {
                stopCollecting()
            }
        }

        // Events.ResumePause
        override fun onResume(owner: LifecycleOwner) {
            super.onResume(owner)
            if (eventsToObserve == Events.ResumePause) {
                startCollecting(owner.lifecycleScope, dispatcher)
            }
        }

        override fun onPause(owner: LifecycleOwner) {
            super.onPause(owner)
            if (eventsToObserve == Events.ResumePause) {
                stopCollecting()
            }
        }
    }

    private val collectionObserver by lazy { CollectionObserver() }

    fun observeLifecycleForCollection(owner: LifecycleOwner) {
        owner.lifecycle.addObserver(collectionObserver)
    }

    fun removeObserver(owner: LifecycleOwner, stopCollecting: Boolean = true) {
        owner.lifecycle.removeObserver(collectionObserver)
        if (stopCollecting) {
            stopCollecting()
        }
    }
}
