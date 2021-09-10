package com.example.android.qstack.search

import androidx.lifecycle.ViewModel
import com.example.android.qstack.model.Questions
import com.example.android.qstack.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository) :
    ViewModel() {

    suspend fun searchQuestions(queryString: String): Flow<List<Questions>> {
        val questionResponse = repository.searchQuestionsAsync(queryString)
        return try {
            val questions = questionResponse.await()
            val question = questions.items
            flow {
                emit(question)
            }
        }catch (exception: Exception){
            Timber.d(exception)
            emptyFlow()
        }
    }
}