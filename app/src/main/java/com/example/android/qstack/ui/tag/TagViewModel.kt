package com.example.android.qstack.ui.tag

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.qstack.model.TagResponse
import com.example.android.qstack.repository.TagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TagViewModel @Inject constructor(private val repository: TagRepository):
    ViewModel() {

    private val _uiState = MutableLiveData<TagResponse>()
    val uiState: LiveData<TagResponse>
    get() = _uiState

    init {
        viewModelScope.launch {
            _uiState.value = getAllTags()
        }
    }

    private suspend fun getAllTags(): TagResponse?{
        val response = repository.getAllTagFromApiAsync()
        return try {
            return response.await()
        }catch (exception: Exception){
            Timber.d(exception)
            null
        }
    }
}