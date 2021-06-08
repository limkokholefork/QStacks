package com.example.android.qstack.repository

import androidx.paging.*
import com.example.android.qstack.data.QuestionMediator
import com.example.android.qstack.db.QStacksDB
import com.example.android.qstack.model.Questions
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class QuestionRepo @Inject constructor(private val questionMediator: QuestionMediator,
                                       private val qStacksDB: QStacksDB) {

    fun getQuestions() : Flow<PagingData<Questions>> {
        return Pager(config = PagingConfig(PAGING_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {qStacksDB.getQuestionDao().getAllQuestions()},
            remoteMediator = questionMediator).flow
    }

    companion object{
        const val PAGING_SIZE = 100
    }
}