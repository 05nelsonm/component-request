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
package io.matthewnelson.component.request.sample.screen.b.navigation

import androidx.navigation.NavController
import io.matthewnelson.component.request.extension.navigation.androidx.DefaultNavAnims
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