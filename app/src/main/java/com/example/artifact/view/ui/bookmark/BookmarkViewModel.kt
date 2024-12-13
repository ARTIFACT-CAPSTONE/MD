package com.example.artifact.view.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artifact.data.database.Bookmark
import com.example.artifact.data.repository.HistoryRepository
import kotlinx.coroutines.launch

class BookmarkViewModel(private val repository: HistoryRepository) : ViewModel() {

    val allBookmarks: LiveData<List<Bookmark>> = repository.getAllBookmarks()

    fun addBookmark(story: Bookmark) {
        viewModelScope.launch {
            val bookmark = Bookmark(
                id = story.id ?: "",
                name = story.name ?: "",
                description = story.description ?: "",
                photoUrl = story.photoUrl ?: "",
                isBookmarked = true
            )
            repository.insertBookmark(bookmark)
        }
    }

    fun removeBookmark(story: Bookmark) {
        viewModelScope.launch {
            val bookmark = Bookmark(
                id = story.id ?: "",
                name = story.name ?: "",
                description = story.description ?: "",
                photoUrl = story.photoUrl ?: "",
                isBookmarked = false
            )
            repository.deleteBookmark(bookmark)  // Hapus dari database
        }
    }


    fun isBookmarked(storyId: String): LiveData<Bookmark?> {
        return repository.getBookmarkById(storyId)
    }
}

