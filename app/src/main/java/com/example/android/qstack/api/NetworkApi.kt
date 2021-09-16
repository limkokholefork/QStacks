package com.example.android.qstack.api

import com.example.android.qstack.model.*
import com.example.android.qstack.utils.FILTER_QUESTION
import com.example.android.qstack.utils.FILTER_USER
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {

    @GET("questions")
    fun getQuestionsAsync(
        @Query("site") site: String,
        @Query("order") order: String,
        @Query("sort") sortOrder: String,
        @Query("tagged") taggedQuestion: String? = null,
        @Query("filter") filter: String = FILTER_QUESTION,
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
        @Query("filter") filter: String = FILTER_QUESTION,
        @Query("page") page: Int,
        @Query("pagesize") pageSize: Int
    )
            : Deferred<NewQuestionResponse>

    @GET("questions/featured")
    fun getAllFeaturedQuestionAsync(
        @Query("site") site: String,
        @Query("order") order: String,
        @Query("sort") sortOrder: String,
        @Query("filter") filter: String = FILTER_QUESTION,
        @Query("page") page: Int,
        @Query("pagesize") pageSize: Int
    )
            : Deferred<FeaturedQuestionResponse>

    @GET("questions/unanswered")
    fun getAllUnansweredQuestionAsync(
        @Query("site") site: String,
        @Query("order") order: String,
        @Query("sort") sortOrder: String,
        @Query("filter") filter: String = FILTER_QUESTION,
        @Query("page") page: Int,
        @Query("pagesize") pageSize: Int
    )
            : Deferred<UnansweredQuestionResponse>

    @GET("search")
    fun searchQuestionsAsync(
        @Query("site") site: String,
        @Query("order") order: String,
        @Query("sort") sortOrder: String,
        @Query("filter") filter: String = FILTER_QUESTION,
        @Query("pagesize") pageSize: Int,
        @Query("intitle") searchQuestion: String
    )
            : Deferred<QuestionResponse>

    @GET("tags")
    fun getAllTagAsync(
        @Query("site") site: String,
        @Query("order") order: String,
        @Query("sort") sortOrder: String,
        @Query("pagesize") pageSize: Int
    )
            : Deferred<TagResponse>

    @GET("users")
    fun getAllUsersAsync(
        @Query("site") site: String,
        @Query("order") order: String,
        @Query("sort") sort: String,
        @Query("page")page: Int,
        @Query("pagesize") pageSize: Int,
        @Query("filter") filter: String = FILTER_USER,
    ): Deferred<UserResponse>

}