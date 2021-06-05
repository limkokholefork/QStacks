package com.example.android.qstack.api

import com.example.android.qstack.model.QuestionResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {

    @GET("questions")
    fun getQuestionsAsync(@Query("site")site : String,
                          @Query("order")order : String,
                          @Query("sort") sortOrder: String,
                          @Query("page")page : Int,
                          @Query("pagesize")pageSize : Int)
    : Deferred<QuestionResponse>
}