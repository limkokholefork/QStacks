package com.example.android.qstack.api

import com.example.android.qstack.model.FeaturedQuestionResponse
import com.example.android.qstack.model.NewQuestionResponse
import com.example.android.qstack.model.QuestionResponse
import com.example.android.qstack.model.UnansweredQuestionResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {

    @GET("questions")
    fun getQuestionsAsync(
        @Query("site") site: String,
        @Query("order") order: String,
        @Query("sort") sortOrder: String,
        @Query("page") page: Int,
        @Query("pagesize") pageSize: Int
    )
            : Deferred<QuestionResponse>

    @GET("questions")
    fun getNewestQuestionAsync(
        @Query("site") site: String,
        @Query("order") order: String,
        @Query("sort") sortOrder: String,
        @Query("tab") newQuestion: String,
        @Query("page") page: Int,
        @Query("pagesize") pageSize: Int
    )
            : Deferred<NewQuestionResponse>

    @GET("questions/featured")
    fun getAllFeaturedQuestionAsync(
        @Query("site") site: String,
        @Query("order") order: String,
        @Query("sort") sortOrder: String,
        @Query("page") page: Int,
        @Query("pagesize") pageSize: Int
    )
            : Deferred<FeaturedQuestionResponse>

    @GET("questions/unanswered")
    fun getAllUnansweredQuestionAsync(
        @Query("site") site: String,
        @Query("order") order: String,
        @Query("sort") sortOrder: String,
        @Query("page") page: Int,
        @Query("pagesize") pageSize: Int
    )
            : Deferred<UnansweredQuestionResponse>


}