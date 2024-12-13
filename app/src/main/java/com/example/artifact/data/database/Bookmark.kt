package com.example.artifact.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark")
data class Bookmark(
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var photoUrl: String = "",
    var isBookmarked: Boolean = false
)