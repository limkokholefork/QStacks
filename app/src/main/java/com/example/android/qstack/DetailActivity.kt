package com.example.android.qstack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.qstack.databinding.ActivityDetailBinding
import com.example.android.qstack.search.ModifiedSearchAdapter
import com.example.android.qstack.ui.question.generalQuestions.WebViewClickListener
import com.example.android.qstack.ui.tag.DETAIL
import com.example.android.qstack.utils.LINK_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel: DetailViewModel by viewModels()

    private lateinit var searchAdapter: ModifiedSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.searchToolbar)


        binding.searchToolbar.setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        val intentBundle = intent.extras
        val tags: String? = intentBundle?.getString(DETAIL)

        lifecycleScope.launch {
            val result = detailViewModel.getAllTaggedQuestion(tags)
            result.collect {
                searchAdapter.submitList(it)
                binding.progressBar2.visibility = View.GONE
            }
        }

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
}