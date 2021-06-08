package com.example.android.qstack.ui.question.generalQuestions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.qstack.databinding.FragmentNewQuestionsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GeneralQuestionsFragment : Fragment() {

    private lateinit var binding : FragmentNewQuestionsBinding
    private val generalQuestionViewModel : GeneralQuestionViewModel by viewModels()
    private lateinit var questionAdapter : GeneralQuestionAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNewQuestionsBinding.inflate(inflater, container, false)

        questionAdapter = GeneralQuestionAdapter()

        binding.recyclerView.apply {
            adapter = questionAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }

        loadData()
        lifecycleScope.launch {
            init()
        }

        return binding.root
    }

    companion object{
        fun newInstance() = GeneralQuestionsFragment()
    }

    private fun loadData() {
        lifecycleScope.launch {
            generalQuestionViewModel.getDataFromRepo().collectLatest {
                questionAdapter.submitData(it)
            }
        }
    }
    private suspend fun init(){
        questionAdapter.loadStateFlow
            .distinctUntilChangedBy {
                it.refresh
            }.filter {
                it.refresh is LoadState.NotLoading
            }.collect { binding.recyclerView.scrollToPosition(0) }
    }

}