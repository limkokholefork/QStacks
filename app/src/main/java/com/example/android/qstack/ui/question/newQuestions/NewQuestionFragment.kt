package com.example.android.qstack.ui.question.newQuestions

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
class NewQuestionFragment : Fragment() {

    private lateinit var binding: FragmentNewQuestionsBinding

    private val viewModel : NewQuestionViewModel by viewModels()

    private lateinit var nAdapter : NewQuestionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNewQuestionsBinding.inflate(layoutInflater, container, false)

        nAdapter = NewQuestionAdapter(NewQuestionListener { link->
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            intent.putExtra(LINK_KEY, link)
            startActivity(intent)
        })

        binding.recyclerView.apply {
            adapter = nAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            nAdapter.refresh()
            lifecycleScope.launch {
                nAdapter.loadStateFlow.distinctUntilChangedBy {
                    it.refresh
                }.filter {
                    it.refresh is LoadState.NotLoading
                }.collect {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        }

        lifecycleScope.launch {
            viewModel.getAllNewQuestion().collectLatest {
                nAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            init()
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.swipe_refresh, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.swipe_refresh){
            binding.swipeRefreshLayout.isRefreshing = true
            lifecycleScope.launch {
                nAdapter.loadStateFlow.distinctUntilChangedBy {
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

    private suspend fun init(){
        nAdapter.loadStateFlow
            .distinctUntilChangedBy {
                it.refresh
            }.filter {
                it.refresh is LoadState.NotLoading
            }.collect { binding.recyclerView.scrollToPosition(0) }
    }

}