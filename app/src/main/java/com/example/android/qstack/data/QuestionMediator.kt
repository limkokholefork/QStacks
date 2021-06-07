package com.example.android.qstack.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.android.qstack.api.NetworkApi
import com.example.android.qstack.db.QStacksDB
import com.example.android.qstack.model.Questions
import com.example.android.qstack.model.RemoteKey
import com.example.android.qstack.utils.OrderBY
import com.example.android.qstack.utils.SortBy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

const val GITHUB_STARTING_PAGE = 1
const val SITE = "stackoverflow"


@OptIn(ExperimentalPagingApi::class)
class QuestionMediator @Inject constructor(private val qStacksDB: QStacksDB,
                                           private val networkApi: NetworkApi)
    : RemoteMediator<Int, Questions>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Questions>
    ): MediatorResult {
        val pageNumber = when(loadType){
            LoadType.PREPEND -> {
                val remoteKey = getRemoteKeyFromFirstPage(state)
                val prevKey = remoteKey?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyFromLastPage(state)
                val nextKey = remoteKey?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                nextKey
            }
            LoadType.REFRESH -> {
                val remoteKey = getRemoteKeyClosestToPosition(state)
                remoteKey?.nextKey?.minus(1) ?: GITHUB_STARTING_PAGE
            }
        }

        try {
            val response = networkApi.getQuestionsAsync(site = SITE,
                OrderBY.DESC.order,
                SortBy.ACTIVITY.sortOrder, pageNumber, state.config.pageSize)
            val questions = response.await()
            val questionList = questions.items
            val endOfPaginationReached = questionList.isEmpty()
            qStacksDB.withTransaction {
                if (loadType == LoadType.REFRESH){
                    qStacksDB.getQuestionDao().nukeTable()
                    qStacksDB.getRemoteKeyDao().nukeRemoteKeyTable()
                }
                val prevKey = if (pageNumber == GITHUB_STARTING_PAGE) null else pageNumber.minus(1)
                val nextKey = if (endOfPaginationReached) null else pageNumber.plus(1)
                val remoteKeys = questionList.map {
                    RemoteKey(it.questionId, prevKey, nextKey)
                }
                qStacksDB.getQuestionDao().addQuestions(questionList)
                qStacksDB.getRemoteKeyDao().insertKey(remoteKeys)
            }
            return MediatorResult.Success(endOfPaginationReached)
        }catch (exception : Exception){
            Timber.d(exception)
            return MediatorResult.Error(exception)
        }

    }

    private suspend fun getRemoteKeyFromFirstPage(state: PagingState<Int, Questions>) : RemoteKey?{
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let {
            withContext(Dispatchers.IO){
                qStacksDB.getRemoteKeyDao().getRemoteKeyById(it.questionId)
            }
        }
    }

    private suspend fun getRemoteKeyFromLastPage(state: PagingState<Int, Questions>) : RemoteKey?{
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let {
            withContext(Dispatchers.IO){
                qStacksDB.getRemoteKeyDao().getRemoteKeyById(it.questionId)
            }
        }
    }

    private suspend fun getRemoteKeyClosestToPosition(state: PagingState<Int, Questions>) : RemoteKey?{
        return state.anchorPosition?.let {position->
            state.closestItemToPosition(position)?.questionId?.let { questionId->
                withContext(Dispatchers.IO){
                    qStacksDB.getRemoteKeyDao().getRemoteKeyById(questionId)
                }
            }
        }
    }

}