package com.example.artifact.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.artifact.data.repository.HistoryRepository
import com.example.artifact.di.Injection
import com.example.artifact.ml.result.ResultViewModel
import com.example.artifact.view.ui.history.HistoryViewModel
import com.example.artifact.view.authentication.login.LoginViewModel
import com.example.artifact.view.authentication.logout.LogoutViewModel
import com.example.artifact.view.authentication.register.RegisterViewModel
import com.example.artifact.ml.upload.UploadViewModel
import com.example.artifact.view.ui.history.BookmarkViewModel

//import com.example.artifact.view.ui.history.BookmarkViewModel

class ViewModelFactory(private val repository: HistoryRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LogoutViewModel::class.java) -> {
                LogoutViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(repository) as T
            }
            modelClass.isAssignableFrom(UploadViewModel::class.java) -> {
                UploadViewModel(repository) as T
            }
            modelClass.isAssignableFrom(BookmarkViewModel::class.java) -> {
                BookmarkViewModel(repository) as T
            }
//            modelClass.isAssignableForm(ResultViewModel::class.java) -> {
//                ResultViewModel(repository) as T
//            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}
