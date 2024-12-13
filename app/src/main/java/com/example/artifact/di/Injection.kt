package com.example.artifact.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.artifact.data.database.BookmarkDatabase
//import com.example.artifact.data.database.BookmarkDatabase
import com.example.artifact.data.pref.UserPreference
import com.example.artifact.data.pref.dataStore
import com.example.artifact.data.remote.ApiConfig
import com.example.artifact.data.repository.HistoryRepository
import com.example.artifact.view.ViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): HistoryRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getUser().first() }
        val apiService = ApiConfig.getApiService(user.token)
        val bookmarkDao = BookmarkDatabase.getDatabase(context).bookmarkDao()
        return HistoryRepository.getInstance(apiService, pref, bookmarkDao)
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        val repository = provideRepository(context)
        return ViewModelFactory(repository)
    }
}
