package com.example.android.qstack.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.android.qstack.api.NetworkApi
import com.example.android.qstack.db.QStacksDB
import com.example.android.qstack.model.NRemoteKey
import com.example.android.qstack.model.NewQuestion
import com.example.android.qstack.utils.OrderBY
import com.example.android.qstack.utils.SortBy
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewQuestionMediator @Inject constructor(
    private val qStacksDB: QStacksDB,
    private val networkApi: NetworkApi
) : RemoteMediator<Int, NewQuestion>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewQuestion>
    ): MediatorResult {
        val pageNumber: Int = when (loadType) {
            LoadType.PREPEND -> {
                val remoteKey = getFirstItemFromPage(state)
                val prevKey = remoteKey?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKey = getLastItemFromPage(state)
                val nextKey = remoteKey?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                nextKey
            }
            LoadType.REFRESH -> {
                val remoteKey = getKeyClosestToPosition(state)
                remoteKey?.nextKey?.minus(1) ?: GITHUB_STARTING_PAGE
            }
        }

        try {
            val response = networkApi.getNewestQuestionAsync(
                SITE,
                OrderBY.DESC.order, SortBy.CREATION.sortOrder,
                "newest", pageNumber, state.config.pageSize
            )
            val newQuestionResponse = response.await()
            val questionList = newQuestionResponse.items
            val endOfPaginationReached = questionList.isEmpty()
            qStacksDB.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    qStacksDB.getNRemoteKeyDao().nukeTable()
                    qStacksDB.getNewQuestionDao().nukeNewQuestionTable()
                }
                val prevKey = if (pageNumber == GITHUB_STARTING_PAGE) null else pageNumber.minus(1)
                val nextKey = if (endOfPaginationReached) null else pageNumber.plus(1)

                val remoteKey = questionList.map {
                    NRemoteKey(it.questionId, prevKey, nextKey)
                }
                qStacksDB.getNewQuestionDao().addNEwQuestions(questionList)
                qStacksDB.getNRemoteKeyDao().addAllNRemoteKey(remoteKey)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (ex: HttpException) {
            return MediatorResult.Error(ex)
        }


    }

    private suspend fun getFirstItemFromPage(state: PagingState<Int, NewQuestion>): NRemoteKey? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let {
            qStacksDB.getNRemoteKeyDao().getRemoteKeyById(it.questionId)
        }
    }

    private suspend fun getLastItemFromPage(state: PagingState<Int, NewQuestion>): NRemoteKey? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let {
            qStacksDB.getNRemoteKeyDao().getRemoteKeyById(it.questionId)
        }
    }

    private suspend fun getKeyClosestToPosition(state: PagingState<Int, NewQuestion>): NRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.questionId?.let {
                qStacksDB.getNRemoteKeyDao().getRemoteKeyById(it)
            }
        }
    }
}