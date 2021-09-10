package com.example.android.qstack.ui.question.unansweredQuestion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android.qstack.model.UnansweredQuestion
import com.example.android.qstack.repository.UnansweredQuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalPagingApi::class)
class UnansweredQuestionViewModel @Inject constructor(private val repository: UnansweredQuestionRepository)
    : ViewModel() {

    fun getDataFromRepo(): Flow<PagingData<UnansweredQuestion>> {
        return repository.getQuestions().cachedIn(viewModelScope)
    }

}