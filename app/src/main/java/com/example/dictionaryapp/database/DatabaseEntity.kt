package com.example.dictionaryapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseEntity(
    @PrimaryKey val word: String,
    @ColumnInfo(name = "json") val json: String
)