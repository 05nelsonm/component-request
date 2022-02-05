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
package io.matthewnelson.component.request.extension.navigation.androidx

import androidx.annotation.IdRes
import androidx.navigation.NavController
import io.matthewnelson.component.request.extension.navigation.NavigateBack

/**
 * Specify either a [destinationId] to pop up to and whether it should be
 * [inclusive] or not, or simply pop the backstack if able.
 *
 * [ensureSingleExecution] is an additional parameter that aids in the
 * submission of requests from a ViewModel, where if set to `true`, a
 * [PopBackStack] request can be instantiated as a global variable and
 * multiple submissions had with the result of only popping the back stack
 * once.
 *
 * Example:
 *
 * class WorkViewModel(private val navigator: WorkNavigator): ViewModel() {
 *
 *     private val popBackStackRequest = PopBackStack(ensureSingleExecution = true)
 *
 *     fun doWork() {
 *         viewModelScope.launch {
 *             // ... work
 *             navigator.navigateBack(popBackStackRequest)
 *         }
 *     }
 *
 *     fun doMoreWork() {
 *         viewModelScope.launch {
 *             // ... more work
 *             navigator.navigateBack(popBackStackRequest)
 *         }
 *     }
 *
 *     override fun onForeground() {
 *         if (myCondition == ThisCondition) {
 *             viewModelScope.launch {
 *                 navigator.navigateBack(popBackStackRequest)
 *             }
 *         }
 *     }
 * }
 * */
open class PopBackStack @JvmOverloads constructor(
    @IdRes
    val destinationId: Int? = null,
    val inclusive: Boolean = false,
    val ensureSingleExecution: Boolean = false
): NavigateBack<NavController>() {

    var hasBeenExecuted: Boolean = false
        protected set

    override fun execute(value: NavController) {
        if (ensureSingleExecution && hasBeenExecuted) return

        if (destinationId != null) {
            if (value.popBackStack(destinationId, inclusive)) {
                hasBeenExecuted = true
            }
        } else if (value.previousBackStackEntry != null) {
            if (value.popBackStack()) {
                hasBeenExecuted = true
            }
        }
    }

}
