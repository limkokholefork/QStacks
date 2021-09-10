package com.example.android.qstack.repository

import com.example.android.qstack.api.NetworkApi
import com.example.android.qstack.data.SITE
import com.example.android.qstack.model.QuestionResponse
import com.example.android.qstack.utils.OrderBY
import com.example.android.qstack.utils.SortBy
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class SearchRepository @Inject constructor(private val networkApi: NetworkApi) {


    fun searchQuestionsAsync(searchQuery: String): Deferred<QuestionResponse>{
        return networkApi.searchQuestionsAsync(site = SITE,
            sortOrder = SortBy.ACTIVITY.sortOrder,
            order = OrderBY.DESC.order, searchQuestion = searchQuery, pageSize = 99)
    }
}