package com.example.kotlinquiz.ui.main.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.kotlinquiz.R
import com.example.kotlinquiz.databinding.FragmentWelcomeBinding
import com.example.kotlinquiz.ui.main.util.QuizPrefsHelper
import com.example.kotlinquiz.ui.main.util.QuizState
import com.example.kotlinquiz.ui.main.viewmodal.WelcomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeFragment : Fragment() {

    companion object {
        fun newInstance() = WelcomeFragment()
    }

    private val viewModel: WelcomeViewModel by viewModels()
    @Inject
    lateinit var prefsHelper: QuizPrefsHelper
    lateinit var binding: FragmentWelcomeBinding
//    private var shouldNavigateTonext = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("WelcomeFragment", "onCreateView: ")
       binding = FragmentWelcomeBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }.root
    }

    private fun getStartQuiz() {
        viewModel.startQuiz(binding.etName.text.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnStart.setOnClickListener(View.OnClickListener {
            getStartQuiz()
        })
        if (prefsHelper.getQuizStatus()) goToNextFragment()
        Log.i("WelcomeFragment", "onViewCreated: ")
        viewModel.quizState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is QuizState.Loading -> {
                    // Show loading UI
                    // do nothing for mow as we are not showing any loading UI
                }
                is QuizState.Success -> {
                    val quiz = state.quiz
                    prefsHelper.saveQuizStatus(true, quiz.id)
                    // Update UI with the created quiz data
                    goToNextFragment()
                }
                is QuizState.Failure -> {
                    val errorMessage = state.error
                    // Show error message in the UI
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }

                else -> println(" arey waha ")
            }
        })
//        if (shouldNavigateTonext) goToNextFragment()
    }

    private fun goToNextFragment() {
        Log.i("WelcomeFragment", "goToNextFragment: ")
        viewModel.resetquizState()
        //navigate to the quiz fragment based on navigation component
        findNavController().navigate(R.id.action_welcomeFragment_to_quizFragment)
    }

    override fun onStart() {
        super.onStart()
    }
}