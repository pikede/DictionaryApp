package com.example.dictionaryapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UrbanDictionaryDao {
    @Query("SELECT * FROM DatabaseEntity WHERE word LIKE :searchedWord")
    fun getDefinitionsByWord(searchedWord: String): List<DatabaseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWordDefinition(vararg databaseEntity: DatabaseEntity)
}