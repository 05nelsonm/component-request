package io.matthewnelson.component.request.sample.screen.b.navigation

import androidx.navigation.NavController
import io.matthewnelson.component.request.androidx.navigation.PopBackStack
import io.matthewnelson.component.request.slave.BaseRequestDriver
import javax.inject.Inject

internal actual class ScreenBNavigator @Inject constructor(
    private val requestDriver: BaseRequestDriver<NavController>
) {
    actual suspend fun navigateUp() {
        requestDriver.submitRequest(PopBackStack())
    }
}