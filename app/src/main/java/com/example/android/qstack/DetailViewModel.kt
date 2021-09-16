package com.example.android.qstack

import androidx.lifecycle.ViewModel
import com.example.android.qstack.model.Questions
import com.example.android.qstack.repository.SearchRepository
import com.example.android.qstack.repository.TagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: TagRepository):
    ViewModel() {

    suspend fun getAllTaggedQuestion(tags: String?): Flow<List<Questions>> {
        return if (tags != null){
            val response = repository.getAllTaggedQuestionAsync(tags)
            try {
                val result =  response.await()
                flow {
                    emit(result.items)
                }
            }catch (error: Exception){
                Timber.d(error)
                emptyFlow()
            }
        }else emptyFlow()
    }
}