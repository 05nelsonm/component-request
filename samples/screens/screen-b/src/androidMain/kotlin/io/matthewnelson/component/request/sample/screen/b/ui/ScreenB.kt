package io.matthewnelson.component.request.sample.screen.b.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import io.matthewnelson.component.request.sample.screen.b.navigation.ScreenBNavigator
import io.matthewnelson.component.request.sample.screen.b.R
import io.matthewnelson.component.request.sample.screen.b.databinding.FragmentBBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
internal actual class ScreenB: Fragment(R.layout.fragment_b) {

    @Inject
    @Suppress("ProtectedInFinal")
    protected lateinit var navigator: ScreenBNavigator

    private val args: ScreenBArgs by navArgs()

    private val binding: FragmentBBinding by viewBinding(FragmentBBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            textViewScreenBArgs.text = args.text
            buttonScreenBBack.setOnClickListener {
                lifecycleScope.launch {
                    navigator.navigateUp()
                }
            }
        }
    }
}