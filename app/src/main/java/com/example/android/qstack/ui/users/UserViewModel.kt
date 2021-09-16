package com.example.android.qstack.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android.qstack.model.UserItem
import com.example.android.qstack.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository) :
    ViewModel() {

    fun getAllUsers(): Flow<PagingData<UserItem>> {
        return repository.getAllUsers().cachedIn(viewModelScope)
    }
}