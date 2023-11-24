package com.example.kotlinquiz.ui.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.kotlinquiz.R
import com.example.kotlinquiz.databinding.FragmentResultBinding
import com.example.kotlinquiz.ui.main.util.QuizPrefsHelper
import com.example.kotlinquiz.ui.main.util.ResultState
import com.example.kotlinquiz.ui.main.viewmodal.QuizViewModel
import com.example.kotlinquiz.ui.main.viewmodal.ResultViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ResultFragment : Fragment() {

    companion object {
        fun newInstance() = ResultFragment()
    }
    
    private lateinit var binding : FragmentResultBinding

    private val viewModel: ResultViewModel by viewModels()
    @Inject
    lateinit var prefsHelper: QuizPrefsHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvName: TextView = binding.tvName
        val tvScore: TextView = binding.tvScore
        val btnFinish: Button = binding.btnFinish

        viewModel.resultState.observe(viewLifecycleOwner){
            when(it){
                is ResultState.Success -> {
                    tvName.text = it.user
                    tvScore.text = "Your Score is ${it.score}  "
                }
                is ResultState.Failure -> {
                    tvName.text = it.errorMessage
                }

                else -> {
                    tvName.text = "Loading......"
                }
            }
        }

        btnFinish.setOnClickListener {
            prefsHelper.saveQuizStatus(false,"")
            findNavController().navigate(R.id.action_resultFragment_to_welcomeFragment)

        }
    }

}