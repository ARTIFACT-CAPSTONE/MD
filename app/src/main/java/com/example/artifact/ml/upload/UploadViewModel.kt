package com.example.artifact.ml.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artifact.data.repository.HistoryRepository
import com.example.artifact.data.response.MLResponse
import com.example.artifact.data.response.UploadResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadViewModel(private val repository: HistoryRepository) : ViewModel() {

    private val _resultAddStory = MutableLiveData<MLResponse>()
    val resultAddStory: LiveData<MLResponse> = _resultAddStory

    private val _resultUploadImage = MutableLiveData<UploadResponse>()
    val resultUploadImage: LiveData<UploadResponse> = _resultUploadImage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun analyzeImage(file: MultipartBody.Part) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.analyze(file)
                _resultAddStory.value = response
            } catch (e: Exception) {
                _resultAddStory.value =
                    MLResponse(message = "Exception: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun uploadImage(file: MultipartBody.Part, imageTitle: RequestBody, name: RequestBody) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.uploadImage(file, imageTitle, name)
                _resultUploadImage.value = response
            } catch (e: Exception) {
                _resultUploadImage.value =
                    UploadResponse(message = "Exception: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
