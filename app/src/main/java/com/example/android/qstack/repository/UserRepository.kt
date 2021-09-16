package com.example.android.qstack.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.android.qstack.model.UserItem
import com.example.android.qstack.ui.users.PAGE_SIZE
import com.example.android.qstack.ui.users.UserPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(private val pagingSource: UserPagingSource) {

    fun getAllUsers(): Flow<PagingData<UserItem>> {
        return Pager(
            PagingConfig(
                PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { pagingSource }
        ).flow
    }
}