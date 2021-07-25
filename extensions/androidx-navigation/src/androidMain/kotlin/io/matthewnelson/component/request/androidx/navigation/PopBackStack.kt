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
package io.matthewnelson.component.request.androidx.navigation

import androidx.annotation.IdRes
import androidx.navigation.NavController
import io.matthewnelson.component.request.slave.Request

open class PopBackStack(
    @IdRes
    private val destinationId: Int? = null,

    private val inclusive: Boolean = false,
//    private val onNavigationFailure: (() -> Unit)? = null,
): Request<NavController>() {
    override fun execute(value: NavController) {
        destinationId?.let { destination ->
            value.popBackStack(destination, inclusive)
//            if (!controller.popBackStack(destination, inclusive)) {
//                onNavigationFailure?.invoke()
//            }
        } ?: value.previousBackStackEntry?.let {
            value.popBackStack()
//            if (!controller.popBackStack()) {
//                onNavigationFailure?.invoke()
//            }
        }
    }
}
