package io.matthewnelson.component.request.sample.screen.a.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import io.matthewnelson.component.request.sample.screen.a.navigation.ScreenANavigator
import io.matthewnelson.component.request.sample.screen.a.navigation.ScreenBText
import io.matthewnelson.component.request.sample.screen.a.navigation.ScreenCText
import io.matthewnelson.component.request.sample.screen.a.R
import io.matthewnelson.component.request.sample.screen.a.databinding.FragmentABinding
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
internal actual class ScreenA: Fragment(R.layout.fragment_a) {

    @Inject
    @Suppress("ProtectedInFinal")
    protected lateinit var navigator: ScreenANavigator<NavController>

    private val binding: FragmentABinding by viewBinding(FragmentABinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            buttonScreenANavigateToB.setOnClickListener {
                lifecycleScope.launch {
                    navigator.toScreenB(
                        editTextScreenAArgumentB.text?.toString()?.let { text ->
                            if (text.isEmpty()) {
                                null
                            } else {
                                ScreenBText(text)
                            }
                        }
                    )
                }
            }
            buttonScreenANavigateToC.setOnClickListener {
                lifecycleScope.launch {
                    navigator.toScreenC(
                        editTextScreenAArgumentC.text?.toString()?.let { text ->
                            if (text.isEmpty()) {
                                null
                            } else {
                                ScreenCText(text)
                            }
                        }
                    )
                }
            }
        }

    }

}