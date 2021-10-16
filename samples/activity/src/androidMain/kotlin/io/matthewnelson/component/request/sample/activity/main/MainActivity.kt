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