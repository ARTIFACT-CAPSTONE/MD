package com.example.artifact.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.picodiploma.loginwithanimation.data.remote.retrofit.ApiService
import com.example.artifact.data.database.Bookmark
import com.example.artifact.data.database.BookmarkDao
import com.example.artifact.data.database.BookmarkDatabase
import com.example.artifact.data.pref.UserModel
import com.example.artifact.data.pref.UserPreference
import com.example.artifact.data.remote.ApiConfig.getApiService
import com.example.artifact.data.response.GetAllDataResponseItem
import com.example.artifact.data.response.LoginResponse
import com.example.artifact.data.response.MLResponse
import com.example.artifact.data.response.RegisterResponse
import com.example.artifact.data.response.UploadResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody
import okhttp3.RequestBody

class HistoryRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference,
    private val bookmarkDao: BookmarkDao
) {
    private suspend fun getApiServiceWithToken(): ApiService {
        val token = userPreference.getSession().first().token
        return getApiService(token)
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun register(body: RequestBody): RegisterResponse {
        return apiService.register(body)
    }

    suspend fun login(body: RequestBody): LoginResponse {
        return apiService.login(body)
    }

    suspend fun getStories(): List<GetAllDataResponseItem> {
        return getApiServiceWithToken().getStories()
    }

    suspend fun analyze(file: MultipartBody.Part): MLResponse {
        val apiService = getApiServiceWithToken()
        return apiService.analyze(file)
    }

    suspend fun uploadImage(file: MultipartBody.Part, imageTitle: RequestBody, name: RequestBody): UploadResponse {
        val apiService = getApiServiceWithToken()
        return apiService.uploadImage(file, imageTitle, name)
    }

    // Fungsi untuk mendapatkan semua bookmark
    fun getAllBookmarks(): LiveData<List<Bookmark>> {
        return bookmarkDao.getBookmarkPaging()
    }

    // Fungsi untuk menyisipkan bookmark
    suspend fun insertBookmark(bookmark: Bookmark) {
        bookmarkDao.insertBookmark(bookmark)
    }

    // Fungsi untuk menghapus bookmark
    suspend fun deleteBookmark(bookmark: Bookmark) {
        bookmarkDao.deleteBookmark(bookmark)
    }

    // Fungsi untuk mendapatkan bookmark berdasarkan id
    fun getBookmarkById(id: String): LiveData<Bookmark?> {
        return bookmarkDao.getBookmarkById(id)
    }

    companion object {
        @Volatile
        private var instance: HistoryRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference,
            bookmarkDao: BookmarkDao
        ): HistoryRepository =
            instance ?: synchronized(this) {
                instance ?: HistoryRepository(apiService, userPreference, bookmarkDao)
            }.also { instance = it }
    }
}
