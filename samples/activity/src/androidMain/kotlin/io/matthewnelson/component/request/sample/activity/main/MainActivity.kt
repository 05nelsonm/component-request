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
package io.matthewnelson.component.request.sample.activity.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import io.matthewnelson.component.request.androidx.navigation.PopBackStack
import io.matthewnelson.component.request.feature.AndroidRequestCollector
import io.matthewnelson.component.request.feature.drivers.RequestDriver
import io.matthewnelson.component.request.sample.activity.main.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
internal actual class MainActivity: AppCompatActivity(R.layout.activity_main) {

    @Inject
    @Suppress("ProtectedInFinal")
    protected lateinit var driver: RequestDriver<NavController>

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    private val controller: NavController by lazy {
        binding.navHostFragment.findNavController()
    }

    private val collector: AndroidRequestCollector<NavController> = AndroidRequestCollector(
        driverProvider = { driver },
        instanceProvider = { controller },
        onPostRequestExecution = { /* save state */ },
        dispatcher = Dispatchers.Main.immediate
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collector.observeStartStopForCollection(owner = this)
    }

    override fun onBackPressed() {
        when {
            onBackPressedDispatcher.hasEnabledCallbacks() -> {
                super.onBackPressed()
            }
            controller.previousBackStackEntry != null -> {
                lifecycleScope.launch(Dispatchers.Main.immediate) {
                    driver.submitRequest(PopBackStack())
                }
            }
            else -> {
                super.onBackPressed()
            }
        }
    }
}