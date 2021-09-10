package com.example.android.qstack.search

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.qstack.MainActivity
import com.example.android.qstack.R
import com.example.android.qstack.WebViewActivity
import com.example.android.qstack.databinding.ActivitySearchBinding
import com.example.android.qstack.ui.question.generalQuestions.WebViewClickListener
import com.example.android.qstack.utils.LINK_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    private val searchViewModel: SearchViewModel by viewModels()

    private lateinit var searchAdapter: ModifiedSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        setSupportActionBar(binding.searchToolbar)


        binding.searchToolbar.setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        handleIntent(intent)
        searchAdapter = ModifiedSearchAdapter(WebViewClickListener {
            it?.let { link ->
                val intent = Intent(applicationContext, WebViewActivity::class.java)
                intent.putExtra(LINK_KEY, link)
                startActivity(intent)
            }
        })

        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            adapter = searchAdapter
            addItemDecoration(
                DividerItemDecoration(
                    applicationContext,
                    LinearLayoutManager.VERTICAL
                )
            )
        }


    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (Intent.ACTION_SEARCH == intent?.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { queryString ->
                SearchRecentSuggestions(
                    this,
                    RecentSuggestionAdapter.AUTHORITY, RecentSuggestionAdapter.MODE
                )
                    .saveRecentQuery(queryString, null)
                lifecycleScope.launch {
                    searchQuestionWithQuery(queryString)
                }
            }
        }
    }

    private suspend fun searchQuestionWithQuery(queryString: String) {
        val result = searchViewModel.searchQuestions(queryString)
        result.collect {
            searchAdapter.submitList(it)
            binding.progressBar2.isVisible = false
        }
    }
}