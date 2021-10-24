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
package io.matthewnelson.component.request.sample.screen.c.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import io.matthewnelson.component.request.extension.navigation.Navigator
import io.matthewnelson.component.request.extension.navigation.androidx.PopBackStack
import io.matthewnelson.component.request.sample.screen.c.R
import io.matthewnelson.component.request.sample.screen.c.databinding.FragmentCBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
internal actual class ScreenC: Fragment(R.layout.fragment_c) {

    @Inject
    @Suppress("ProtectedInFinal")
    protected lateinit var navigator: Navigator<NavController>

    private val args: ScreenCArgs by navArgs()

    private val binding: FragmentCBinding by viewBinding(FragmentCBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            textViewScreenCArgs.text = args.text
            buttonScreenCBack.setOnClickListener {
                lifecycleScope.launch {
                    navigator.navigateBack(PopBackStack())
                }
            }
        }
    }
}
