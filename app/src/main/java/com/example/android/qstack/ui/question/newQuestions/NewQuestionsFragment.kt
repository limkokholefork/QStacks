package com.example.android.qstack.ui.question.newQuestions

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.qstack.R
import com.example.android.qstack.databinding.FragmentNewQuestionsBinding

class NewQuestionsFragment : Fragment() {

    private lateinit var binding : FragmentNewQuestionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewQuestionsBinding.inflate(inflater, container, false)

        return binding.root
    }

}