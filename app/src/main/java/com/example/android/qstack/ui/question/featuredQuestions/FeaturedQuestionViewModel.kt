package com.example.android.qstack.ui.question.featuredQuestions

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.android.qstack.model.FeaturedQuestion
import com.example.android.qstack.repository.FeaturedQuestionRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalPagingApi::class)
class FeaturedQuestionViewModel @Inject constructor(private val repository: FeaturedQuestionRepo) :
    ViewModel() {


    fun getDataFromRepo(): Flow<PagingData<FeaturedQuestion>>{
        return repository.getAllFeaturedQuestion()
    }
}