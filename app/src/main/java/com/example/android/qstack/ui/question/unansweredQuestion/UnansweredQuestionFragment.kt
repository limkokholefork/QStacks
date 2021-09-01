package com.example.android.qstack.ui.question.unansweredQuestion

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.qstack.R
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
                    it.refresh is LoadState.NotLoading
                }.collect {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        }

        unansweredQuestionAdapter.retry()

        lifecycleScope.launch {
            init()
        }
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.swipe_refresh, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.swipe_refresh){
            binding.swipeRefreshLayout.isRefreshing = true
            lifecycleScope.launch {
                unansweredQuestionAdapter.loadStateFlow.distinctUntilChangedBy {
                    it.refresh
                }.filter {
                    it.refresh is LoadState.NotLoading
                }.collect {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
            true
        }else super.onOptionsItemSelected(item)
    }
}