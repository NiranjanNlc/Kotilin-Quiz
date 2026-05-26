package com.example.kotlinquiz.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.kotlinquiz.R
import com.example.kotlinquiz.ui.compose.quiz.QuizEvent
import com.example.kotlinquiz.ui.compose.quiz.QuizScreen
import com.example.kotlinquiz.ui.compose.theme.QuizTheme
import com.example.kotlinquiz.ui.main.viewmodal.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@AndroidEntryPoint
class QuizFragment : Fragment() {

    private val viewModel: QuizViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                QuizTheme {
                    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
                    QuizScreen(
                        uiState = uiState,
                        onAnswerTap = viewModel::onAnswerTapped,
                        onQuitConfirmed = viewModel::onQuitConfirmed,
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.events.collect { event ->
                    when (event) {
                        QuizEvent.NavigateToResults -> {
                            findNavController().navigate(R.id.action_quizFragment_to_resultFragment)
                        }
                        QuizEvent.NavigateToWelcome -> {
                            findNavController().navigate(R.id.action_quizFragment_to_welcomeFragment)
                        }
                    }
                }
            }
        }
    }
}
