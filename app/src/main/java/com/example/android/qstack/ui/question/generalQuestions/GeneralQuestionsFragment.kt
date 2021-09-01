package com.example.android.qstack.ui.question.generalQuestions

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
                    it.refresh is LoadState.NotLoading
                }.collect {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        }

        loadData()
        lifecycleScope.launch {
            init()
        }

        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.swipe_refresh, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.swipe_refresh){
            questionAdapter.refresh()
            lifecycleScope.launch {
                questionAdapter.loadStateFlow.distinctUntilChangedBy {
                    it.refresh
                }.filter {
                    it.refresh is LoadState.NotLoading
                }.collect {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
            true
        }else{
            super.onOptionsItemSelected(item)
        }
    }

}