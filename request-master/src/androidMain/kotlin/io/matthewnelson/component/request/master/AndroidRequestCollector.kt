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
package io.matthewnelson.component.request.master

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import io.matthewnelson.component.request.master.drivers.RequestDriver
import io.matthewnelson.component.request.slave.Request
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * A collector for Android that hooks into the lifecycle owner's onStart/onStop
 * events. This is safe for use with UI/navigation components.
 * */
class AndroidRequestCollector<T: Any>(
    driverProvider: () -> RequestDriver<T>,
    instanceProvider: () -> T,
    onPostRequestExecution: (suspend (Request<T>) -> Unit)? = null,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
): RequestCollector<T>(
    driverProvider,
    instanceProvider,
    onPostRequestExecution,
) {

    private inner class StartStopObserver: DefaultLifecycleObserver {
        override fun onStart(owner: LifecycleOwner) {
            super.onStart(owner)
            startCollecting(owner.lifecycleScope, dispatcher)
        }

        override fun onStop(owner: LifecycleOwner) {
            super.onStop(owner)
            stopCollecting()
        }
    }

    private val startStopObserver by lazy { StartStopObserver() }

    fun observeStartStopForCollection(owner: LifecycleOwner) {
        owner.lifecycle.addObserver(startStopObserver)
    }

    fun removeObserver(owner: LifecycleOwner, stopCollecting: Boolean = true) {
        owner.lifecycle.removeObserver(startStopObserver)
        if (stopCollecting) {
            stopCollecting()
        }
    }
}
