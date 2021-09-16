package com.example.android.qstack.ui.users

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.qstack.R
import com.example.android.qstack.UserDetailActivity
import com.example.android.qstack.databinding.FragmentUsersBinding
import com.example.android.qstack.search.UserSearchActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

const val USER_DETAIL_KEY = "userDetailKey"
const val SEARCH_USER_KEY = "searchUserkey"

@AndroidEntryPoint
class UsersFragment : Fragment() {

    private lateinit var binding: FragmentUsersBinding

    private val userViewModel: UserViewModel by viewModels()

    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUsersBinding.inflate(layoutInflater, container, false)

        binding.retryButton.setOnClickListener {
            userAdapter.refresh()
        }



        userAdapter = UserAdapter(UserClickItemListener { userId->
            userId?.let {
                val intent = Intent(requireContext(), UserDetailActivity::class.java)
                intent.putExtra(USER_DETAIL_KEY, it)
                startActivity(intent)
            }
        })

        binding.lifecycleOwner = viewLifecycleOwner

        binding.recyclerUserView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = userAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
        lifecycleScope.launch {
            userAdapter.loadStateFlow.collect {loadState->
                val isEmpty: Boolean = loadState.source.refresh is LoadState.NotLoading
                        && userAdapter.itemCount == 0
                binding.emptyView.isVisible = isEmpty
                val isLoading = loadState.source.refresh is LoadState.Loading
                binding.progressBar.isVisible = isLoading

                val errorLoading = loadState.source.refresh is LoadState.Error
                binding.retryButton.isVisible= errorLoading


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
            userViewModel.getAllUsers().collect {
                userAdapter.submitData(it)
            }
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.search_menu, menu)
        val searchView = (menu.findItem(R.id.search_menu)?.actionView as
                androidx.appcompat.widget.SearchView)
        searchView.queryHint = "Search users"
        val listener = object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchQuery: String?): Boolean {
                if (searchQuery?.isBlank() != true){
                    Intent(requireContext(), UserSearchActivity::class.java).apply {
                        putExtra(SEARCH_USER_KEY, searchQuery)
                        startActivity(this)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        }
        searchView.setOnQueryTextListener(listener)
    }


}