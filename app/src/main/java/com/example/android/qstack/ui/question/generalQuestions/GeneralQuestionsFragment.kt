package com.example.android.qstack.ui.question.generalQuestions

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.qstack.WebViewActivity
import com.example.android.qstack.databinding.FragmentNewQuestionsBinding
import com.example.android.qstack.utils.LINK_KEY
import dagger.hilt.android.AndroidEntryPoint
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

        questionAdapter = GeneralQuestionAdapter(WebViewClickListener {
            it?.let {link->
                val intent = Intent(requireContext(), WebViewActivity::class.java)
                intent.putExtra(LINK_KEY, link)
                startActivity(intent)
            }
        })

        binding.retryButton.setOnClickListener {
            questionAdapter.refresh()
        }
        binding.recyclerView.apply {
            adapter = questionAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            questionAdapter.refresh()
            binding.swipeRefreshLayout.isRefreshing = true
            lifecycleScope.launch {
                questionAdapter.loadStateFlow.distinctUntilChangedBy {
                    it.refresh
                }.filter {
                    it.refresh is LoadState.NotLoading || it.refresh is LoadState.Error
                }.collect {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        }

        lifecycleScope.launch {
            questionAdapter.loadStateFlow.collect { loadState: CombinedLoadStates ->
                val isEmpty = loadState.source.refresh is LoadState.NotLoading &&
                        questionAdapter.itemCount == 0
//                binding.recyclerView.isVisible = !isEmpty
                binding.emptyList.isVisible = isEmpty

                val isLoading = loadState.source.refresh is LoadState.Loading
                binding.progressCircular.isVisible = isLoading

                val errorLoading = loadState.source.refresh is LoadState.Error
                binding.retryButton.isVisible = errorLoading

                val errorMessage = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error

                errorMessage?.let {
                    Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        loadData()
        lifecycleScope.launch {
            init()
        }

        setHasOptionsMenu(false)
        return binding.root
    }

    companion object{
        fun newInstance() = GeneralQuestionsFragment()
    }

    private fun loadData() {
        lifecycleScope.launch {
            generalQuestionViewModel.getDataFromRepo().collectLatest {
                questionAdapter.submitData(it)
                binding.progressCircular.isVisible = false
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