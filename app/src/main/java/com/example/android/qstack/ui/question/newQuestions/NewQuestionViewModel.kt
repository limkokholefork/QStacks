package com.example.android.qstack.ui.question.newQuestions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android.qstack.model.NewQuestion
import com.example.android.qstack.repository.NewQuestionRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class NewQuestionViewModel @Inject constructor(private val repo : NewQuestionRepo)
    : ViewModel() {

        fun getAllNewQuestion() : Flow<PagingData<NewQuestion>>{
            return repo.getNewQuestions().cachedIn(viewModelScope)
        }
}