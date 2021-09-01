package com.example.android.qstack.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.android.qstack.data.FeaturedQuestionMediator
import com.example.android.qstack.db.QStacksDB
import com.example.android.qstack.model.FeaturedQuestion
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class FeaturedQuestionRepo @Inject constructor(private val mediator: FeaturedQuestionMediator,
                                               private val qStacksDB: QStacksDB) {


    fun getAllFeaturedQuestion(): Flow<PagingData<FeaturedQuestion>>{
       return Pager(config = PagingConfig(NewQuestionRepo.PAGE_SIZE,
           enablePlaceholders = false),
           remoteMediator = mediator,
           pagingSourceFactory = {qStacksDB.getFeaturedQuestionDao().getAllFeaturedQuestions()}).flow
    }

}