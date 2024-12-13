package com.example.artifact.view.authentication.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.artifact.data.pref.UserModel
import com.example.artifact.data.repository.HistoryRepository
import com.example.artifact.data.response.LoginResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import retrofit2.HttpException

class LoginViewModel(private val repository: HistoryRepository) : ViewModel() {

    private val _result = MutableLiveData<LoginResponse>()
    val result: LiveData<LoginResponse> = _result

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun login(body: RequestBody) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.login(body)
                if (response.message == "Login successful") {
                    _result.value = response
                }
            } catch (e: Exception) {
                _result.value = LoginResponse("Username or password is incorrect", null)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}