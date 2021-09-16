package com.example.android.qstack.repository

import androidx.lifecycle.LiveData
import com.example.android.qstack.api.NetworkApi
import com.example.android.qstack.data.SITE
import com.example.android.qstack.db.TagDbDao
import com.example.android.qstack.model.QuestionResponse
import com.example.android.qstack.model.TagDbModel
import com.example.android.qstack.model.TagResponse
import com.example.android.qstack.utils.OrderBY
import com.example.android.qstack.utils.SortBy
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class TagRepository @Inject constructor(private val networkApi: NetworkApi,
                                        private val tagDbDao: TagDbDao) {

    fun getAllTagFromApiAsync(): Deferred<TagResponse>{
        return networkApi.getAllTagAsync(
            site = SITE,
            order = OrderBY.DESC.order,
            sortOrder = SortBy.POPULAR.sortOrder,
            pageSize = 99
        )
    }

    fun getAllTaggedQuestionAsync(tags: String): Deferred<QuestionResponse> {
        return networkApi.getQuestionsAsync(
            site = SITE,
            order = OrderBY.DESC.order,
            taggedQuestion = tags,
            sortOrder = SortBy.ACTIVITY.sortOrder, page = 1,
            pageSize = 99
        )
    }

    suspend fun updateDbFromTagsApi(){
        val tagResponse = getAllTagFromApiAsync()
        try {
            val tags = tagResponse.await()
            val tagDbModel: List<TagDbModel>? = tags.items?.map {
                TagDbModel(tagName = it.name)
            }
            tagDbDao.saveAllTags(tagDbModel)
        }catch (exception: Exception){
            Timber.d(exception)
        }
    }
}