package io.matthewnelson.component.request.sample.screen.c.navigation

import androidx.navigation.NavController
import io.matthewnelson.component.request.androidx.navigation.DefaultNavAnims
import io.matthewnelson.component.request.concept.Request
import io.matthewnelson.component.request.sample.screen.c.R
import io.matthewnelson.component.request.sample.screen.c.ui.ScreenCArgs

actual class ScreenCNavigationRequest(
    private val textArg: String?
): Request<NavController>() {
    override fun execute(value: NavController) {
        value.navigate(
            R.id.nav_graph_screen_c,
            ScreenCArgs.Builder(textArg ?: "No Argument Passed").build().toBundle(),
            DefaultNavAnims.builder.build()
        )
    }
}