package com.example.android.qstack.ui.tag

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.qstack.model.TagItem
import com.example.android.qstack.repository.TagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TagViewModel @Inject constructor(private val repository: TagRepository):
    ViewModel() {

    private val _uiState = MutableLiveData<List<TagItem>>()
    val uiState: LiveData<List<TagItem>>
    get() = _uiState

    init {
        viewModelScope.launch {
            _uiState.value = getAllTags()
        }
    }


    private suspend fun getAllTags(): List<TagItem>? {
        val tagResponse = repository.getAllTagFromApiAsync()
        return try {
            val response = tagResponse.await()
            response.items
        }catch (error: Exception){
            Timber.d(error)
            null
        }
    }

}


