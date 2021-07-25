package io.matthewnelson.component.request.sample.screen.c.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import io.matthewnelson.component.request.sample.screen.c.navigation.ScreenCNavigator
import io.matthewnelson.component.request.sample.screen.c.R
import io.matthewnelson.component.request.sample.screen.c.databinding.FragmentCBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
internal actual class ScreenC: Fragment(R.layout.fragment_c) {

    @Inject
    @Suppress("ProtectedInFinal")
    protected lateinit var navigator: ScreenCNavigator

    private val args: ScreenCArgs by navArgs()

    private val binding: FragmentCBinding by viewBinding(FragmentCBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            textViewScreenCArgs.text = args.text
            buttonScreenCBack.setOnClickListener {
                lifecycleScope.launch {
                    navigator.navigateUp()
                }
            }
        }
    }
}