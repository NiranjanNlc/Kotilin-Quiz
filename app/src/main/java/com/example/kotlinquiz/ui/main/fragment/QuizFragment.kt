package com.example.kotlinquiz.ui.main.fragment

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.kotlinquiz.R
import com.example.kotlinquiz.databinding.FragmentQuizBinding
import com.example.kotlinquiz.local.entity.Answer
import com.example.kotlinquiz.ui.main.util.QuizPrefsHelper
import com.example.kotlinquiz.ui.main.viewmodal.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.niranjan.quiz.modal.AnswerEntity
import javax.inject.Inject

@AndroidEntryPoint
class QuizFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentQuizBinding
    private val viewModel: QuizViewModel by viewModels()

    @Inject
    lateinit var prefsHelper: QuizPrefsHelper


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = this@QuizFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        setupClickListeners()
        viewModel.getFirstQuestion()
        observeViewModel()
    }

    private fun setupClickListeners() {
        binding.tvOptionOne.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)
        binding.tvOptionThree.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)
    }

    private fun observeViewModel() {
        viewModel.currentQuestion.observe(viewLifecycleOwner) { question ->
            question?.let {
                binding.tvQuestion.text = it.text
                setOptionTexts(it.answers)
            }
            setDefaultOptionsView()
        }

        viewModel.isAnswered.observe(viewLifecycleOwner) { isLastQuestion ->
            binding.btnSubmit.text = if (isLastQuestion) {
                "Get next question"
            } else {
                "Answer"
            }
        }
        viewModel.isLastQuestion.observe(viewLifecycleOwner) { isLastQuestion ->
            if (isLastQuestion) {
               goToNextFragment()
            }
        }
    }
    private fun goToNextFragment() {
        Log.i("WelcomeFragment", "goToNextFragment: ")
        //navigate to the quiz fragment based on navigation component
        findNavController().navigate(R.id.action_quizFragment_to_resultFragment)
    }

    private fun setOptionTexts(answers: List<AnswerEntity>) {
        binding.tvOptionOne.text = answers.getOrNull(0)?.text
        binding.tvOptionTwo.text = answers.getOrNull(1)?.text
        binding.tvOptionThree.text = answers.getOrNull(2)?.text
    }

    private fun setDefaultOptionsView() {
        binding.optionContainer.children.forEach { option ->
            option as TextView
            option.setTextColor(Color.parseColor("#708089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.default_option_border_bg
            )
        }
    }
    private fun setSelectedOptionView(view: TextView, selectedOptionNum: Int) {
        setDefaultOptionsView()
        view.setTextColor(Color.parseColor("#363A43"))
        view.setTypeface(view.typeface, Typeface.BOLD)
        view.background = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.selected_option_border_bg
        )
    }

    private fun setAnswerView(view: TextView, drawableView: Int) {
        view.background = ContextCompat.getDrawable(
            requireContext(),
            drawableView
        )
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.tvOptionOne.id -> {
                handleOptionSelection(1)
            }
            binding.tvOptionTwo.id -> {
                handleOptionSelection(2)
            }
            binding.tvOptionThree.id -> {
                handleOptionSelection(3)
            }
            binding.btnSubmit.id -> {
                handleSubmission()
            }
        }
    }

    private fun handleOptionSelection(selectedOptionPosition: Int) {
        viewModel.onOptionSelected(selectedOptionPosition)
        setSelectedOptionView(getOptionViewByPosition(selectedOptionPosition), selectedOptionPosition)
    }

    private fun handleSubmission() {
        val selectedOptionPosition = viewModel.selectedOptionPosition.value ?: 0
        if (selectedOptionPosition != 0) {
            if (viewModel.isAnswered.value == true) {
                Log.i("startquiz", "handleSubmission:  answered ")
                viewModel.moveToNextQuestion()
                enableDisableOptionClicks(true)
                setDefaultOptionsView()
            }
            else
            {
                Log.i("startquiz", "handleSubmission: not answered ")
                val isCorrect = viewModel.checkAnswer(selectedOptionPosition)
                Log.i("startquiz", "handleSubmission: $isCorrect")
                showAnswerFeedback(isCorrect)
                // Disable clicking on options after submitting the answer
                enableDisableOptionClicks(false)
            }


        } else {
            // Show an error message to select an option
        }
    }

    private fun showAnswerFeedback(isCorrect: Boolean) {

        Log.i("startquiz", "handleSubmission: not answered "+isCorrect)
        val selectedOptionPosition = viewModel.selectedOptionPosition.value ?: 0
        val selectedOptionView = getOptionViewByPosition(selectedOptionPosition)
        Log.i("startquiz", "handleSubmission: $selectedOptionView "+ selectedOptionPosition)
        if (isCorrect) {
            selectedOptionView.background = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.correct_option_border_bg
            )
        } else {
            selectedOptionView?.background = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.incorrect_option_border_bg
            )
            // Highlight the correct answer if the user selected the wrong one
            val correctAnswerPosition = viewModel.currentQuestion.value?.answers?.indexOfFirst { it.isCorrect }
                ?: 0
            val correctAnswerView = getOptionViewByPosition(correctAnswerPosition)
            Log.i("startquiz", "handleSubmission snser checking : $correctAnswerView "+ correctAnswerPosition)
            correctAnswerView?.background = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.correct_option_border_bg
            )
        }
    }

    private fun enableDisableOptionClicks(clickable : Boolean) {
        binding.tvOptionOne.isClickable = clickable
        binding.tvOptionTwo.isClickable = clickable
        binding.tvOptionThree.isClickable = clickable
    }

    private fun getOptionViewByPosition(position: Int): TextView {
        return when (position) {
            1 -> binding.tvOptionOne
            2 -> binding.tvOptionTwo
            3 -> binding.tvOptionThree
            else -> throw IllegalArgumentException("Invalid option position")
        }
    }
}
