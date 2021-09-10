package com.example.android.qstack.ui.question.generalQuestions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android.qstack.model.Questions
import com.example.android.qstack.repository.QuestionRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
@HiltViewModel
class GeneralQuestionViewModel @Inject constructor(private val repository: QuestionRepo)
    : ViewModel() {

    fun getDataFromRepo() : Flow<PagingData<Questions>>{
        return repository.getQuestions().cachedIn(viewModelScope)
    }
}