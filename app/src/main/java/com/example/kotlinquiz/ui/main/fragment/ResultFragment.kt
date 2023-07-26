package com.example.kotlinquiz.ui.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.kotlinquiz.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    companion object {
        fun newInstance() = ResultFragment()
    }
    
    private lateinit var binding : FragmentResultBinding 

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Todo: connect to each view in the layout through its id
        val tvName: TextView = binding.tvName
        val tvScore: TextView = binding.tvScore
        val btnFinish: Button = binding.btnFinish
//        reterive the value though the viewmodeland fragment
//        tvName.text = userName
//        val userName = intent.getStringExtra(Constants.USER_NAME)
//        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
//        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)

//        tvScore.text = "Your Score is $correctAnswers out of $totalQuestions."

        btnFinish.setOnClickListener {
           // transfer to the welcome fragment using navigation
        }
    }

}