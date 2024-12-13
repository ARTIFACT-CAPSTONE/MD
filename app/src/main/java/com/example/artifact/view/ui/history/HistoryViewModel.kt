package com.example.artifact.view.ui.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.artifact.data.repository.HistoryRepository
import com.example.artifact.data.response.GetAllDataResponse
import com.example.artifact.data.response.GetAllDataResponseItem
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel() {
    private var _listStory = MutableLiveData<List<GetAllDataResponseItem>>()
    val listStory: LiveData<List<GetAllDataResponseItem>> = _listStory

    private val _resultMessage = MutableLiveData<String?>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getStories() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getStories()
                _listStory.value = response
            } catch (e: Exception) {  e.printStackTrace()
                _resultMessage.value = "Exception: ${e.message}"
            }
        }
    }
}