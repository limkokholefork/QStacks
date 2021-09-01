package com.example.android.qstack.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.android.qstack.data.UnansweredQuestionMediator
import com.example.android.qstack.db.QStacksDB
import com.example.android.qstack.model.UnansweredQuestion
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class UnansweredQuestionRepository @Inject constructor(private val questionMediator: UnansweredQuestionMediator,
                                       private val qStacksDB: QStacksDB
) {

    fun getQuestions() : Flow<PagingData<UnansweredQuestion>> {
        return Pager(config = PagingConfig(PAGING_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {qStacksDB.getUnansweredQuestion().getAllQuestions()},
            remoteMediator = questionMediator).flow
    }

    companion object{
        const val PAGING_SIZE = 100
    }
}