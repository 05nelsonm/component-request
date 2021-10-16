package io.matthewnelson.component.request.sample.screen.b.navigation

import androidx.navigation.NavController
import io.matthewnelson.component.request.androidx.navigation.DefaultNavAnims
import io.matthewnelson.component.request.concept.Request
import io.matthewnelson.component.request.sample.screen.b.R
import io.matthewnelson.component.request.sample.screen.b.ui.ScreenBArgs

actual class ScreenBNavigationRequest(
    private val textArg: String?
): Request<NavController>() {
    override fun execute(value: NavController) {
        value.navigate(
            R.id.nav_graph_screen_b,
            ScreenBArgs.Builder(textArg ?: "No Argument Passed").build().toBundle(),
            DefaultNavAnims.builder.build()
        )
    }
}