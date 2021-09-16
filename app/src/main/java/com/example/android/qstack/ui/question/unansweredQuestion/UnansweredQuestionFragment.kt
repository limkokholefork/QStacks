package com.example.android.qstack.ui.question.unansweredQuestion

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
class UnansweredQuestionFragment : Fragment() {

    private lateinit var binding: FragmentNewQuestionsBinding
    private val unansweredQuestionViewModel: UnansweredQuestionViewModel by viewModels()
    private lateinit var unansweredQuestionAdapter: UnansweredQuestionAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNewQuestionsBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        unansweredQuestionAdapter = UnansweredQuestionAdapter(ClickListener {
            it?.let {link->
                val intent = Intent(requireContext(), WebViewActivity::class.java)
                intent.putExtra(LINK_KEY, link)
                startActivity(intent)
            }
        })

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            adapter = unansweredQuestionAdapter
        }

        lifecycleScope.launch {
            unansweredQuestionViewModel.getDataFromRepo().collectLatest {
                unansweredQuestionAdapter.submitData(it)
            }
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            unansweredQuestionAdapter.refresh()
            lifecycleScope.launch {
                unansweredQuestionAdapter.loadStateFlow.distinctUntilChangedBy {
                    it.refresh
                }.filter {
                    it.refresh is LoadState.NotLoading || it.refresh is LoadState.Error
                }.collect {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        }
        lifecycleScope.launch {
            unansweredQuestionAdapter.loadStateFlow.collect { loadState: CombinedLoadStates ->
                val isEmpty = loadState.source.refresh is LoadState.NotLoading &&
                        unansweredQuestionAdapter.itemCount == 0
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


        lifecycleScope.launch {
            init()
        }
        setHasOptionsMenu(false)
        return binding.root
    }
    private suspend fun init(){
        unansweredQuestionAdapter.loadStateFlow
            .distinctUntilChangedBy {
                it.refresh
            }.filter {
                it.refresh is LoadState.NotLoading
            }.collect { binding.recyclerView.scrollToPosition(0) }
    }
}