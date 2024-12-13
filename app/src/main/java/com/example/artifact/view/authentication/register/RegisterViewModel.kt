package com.example.artifact.view.authentication.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artifact.R
import com.example.artifact.data.repository.HistoryRepository
import com.example.artifact.data.response.RegisterResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import retrofit2.HttpException

class RegisterViewModel(private val repository: HistoryRepository) : ViewModel() {
    private val _result = MutableLiveData<RegisterResponse>()
    val result: LiveData<RegisterResponse> = _result

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun register(body: RequestBody) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.register(body)
                if (response.message.equals("Jury registered successfully")) {
                    val message = response.message
                    _result.value = RegisterResponse(message)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _result.value = RegisterResponse("Username is already exist")
            } finally {
                _isLoading.value = false
            }
        }
    }
}