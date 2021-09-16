package com.example.android.qstack.ui.users

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.android.qstack.api.NetworkApi
import com.example.android.qstack.data.SITE
import com.example.android.qstack.model.UserItem
import com.example.android.qstack.utils.FILTER_USER
import com.example.android.qstack.utils.OrderBY
import com.example.android.qstack.utils.SortBy
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

const val STACKOVERFLOW_STARTING_PAGE = 1
const val PAGE_SIZE = 100

class UserPagingSource @Inject constructor(private val networkApi: NetworkApi) :
    PagingSource<Int, UserItem>() {
    override fun getRefreshKey(state: PagingState<Int, UserItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserItem> {
        val position = params.key ?: STACKOVERFLOW_STARTING_PAGE
        try {
            val response = networkApi.getAllUsersAsync(SITE, OrderBY.DESC.order,
                SortBy.REPUTATION.sortOrder, position, PAGE_SIZE, FILTER_USER)
            val userResponse = response.await()
            val userItem = userResponse.items
            val nextKey = if (userItem.isEmpty()) null else position + (params.loadSize.div(
                PAGE_SIZE))
            val prevKey = if (position == STACKOVERFLOW_STARTING_PAGE) null else position.minus(1)
            return LoadResult.Page(
                userItem, prevKey, nextKey
            )
        }catch (exception: IOException){
            Timber.d(exception)
            return LoadResult.Error(exception)
        }catch (error: HttpException){
            Timber.e(error)
            return LoadResult.Error(error)
        }
    }
}