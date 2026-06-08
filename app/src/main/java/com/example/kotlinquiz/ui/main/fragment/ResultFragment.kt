package com.example.kotlinquiz.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.example.kotlinquiz.R
import com.example.kotlinquiz.ui.compose.result.ResultScreen
import com.example.kotlinquiz.ui.compose.theme.QuizTheme
import com.example.kotlinquiz.ui.main.util.QuizPrefsHelper
import com.example.kotlinquiz.ui.main.viewmodal.ResultViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ResultFragment : Fragment() {

    private val viewModel: ResultViewModel by viewModels()

    @Inject
    lateinit var prefsHelper: QuizPrefsHelper

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
                    ResultScreen(
                        uiState = uiState,
                        onAnimationsStarted = viewModel::onAnimationsStarted,
                        onPlayAgain = {
                            prefsHelper.saveQuizStatus(false, "")
                            findNavController().navigate(R.id.action_resultFragment_to_welcomeFragment)
                        },
                        onBackToHome = {
                            prefsHelper.saveQuizStatus(false, "")
                            findNavController().navigate(R.id.action_resultFragment_to_welcomeFragment)
                        },
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadResult(prefsHelper.getQuizId())
    }
}
