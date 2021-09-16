package com.example.android.qstack.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.android.qstack.data.NewQuestionMediator
import com.example.android.qstack.db.QStacksDB
import com.example.android.qstack.model.NewQuestion
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewQuestionRepo @Inject constructor(private val newQuestionMediator: NewQuestionMediator,
                                          private val qStacksDB: QStacksDB) {

    fun getNewQuestions() : Flow<PagingData<NewQuestion>>{
        return Pager(config = PagingConfig(PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = newQuestionMediator,
            pagingSourceFactory = {qStacksDB.getNewQuestionDao().getAllNewQuestion()}).flow
    }

    companion object{
        const val PAGE_SIZE = 99
    }


}