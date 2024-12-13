package com.example.artifact.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: Bookmark)

    @Delete
    suspend fun deleteBookmark(bookmark: Bookmark)

    @Query("SELECT * FROM bookmark")
    fun getBookmarkPaging(): LiveData<List<Bookmark>>

    @Query("SELECT * FROM bookmark WHERE id = :id")
    fun getBookmarkById(id: String): LiveData<Bookmark?>

    @Query("DELETE FROM bookmark WHERE id = :id")
    suspend fun deleteBookmarkById(id: String)
}
