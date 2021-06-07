package com.example.android.qstack.ui.question.newQuestions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.qstack.databinding.FragmentNewQuestionsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewQuestionsFragment : Fragment() {

    private lateinit var binding : FragmentNewQuestionsBinding
    private val newQuestionViewModel : NewQuestionViewModel by viewModels()
    private lateinit var questionAdapter : NewQuestionAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNewQuestionsBinding.inflate(inflater, container, false)

        questionAdapter = NewQuestionAdapter()

        binding.recyclerView.apply {
            adapter = questionAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }

        lifecycleScope.launch {
            newQuestionViewModel.getDataFromRepo().collect {
                questionAdapter.submitData(it)
            }
        }

        return binding.root
    }

}