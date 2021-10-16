package io.matthewnelson.component.request.sample.screen.c.navigation

import androidx.navigation.NavController
import io.matthewnelson.component.request.androidx.navigation.PopBackStack
import io.matthewnelson.component.request.concept.BaseRequestDriver
import javax.inject.Inject

internal actual class ScreenCNavigator @Inject constructor(
    private val requestDriver: BaseRequestDriver<NavController>
) {
    actual suspend fun navigateUp() {
        requestDriver.submitRequest(PopBackStack())
    }
}