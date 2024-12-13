package com.example.artifact.view.authentication.logout

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.artifact.data.pref.UserModel
import com.example.artifact.data.repository.HistoryRepository
import kotlinx.coroutines.launch

class LogoutViewModel(private val repository: HistoryRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}