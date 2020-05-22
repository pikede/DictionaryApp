package com.example.dictionaryapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Database to store word definitions from api call for offline operations
@Database(entities = [DatabaseEntity::class], version = 1, exportSchema = false)
abstract class UrbanDictionaryDatabase : RoomDatabase() {

    abstract fun wordDefinitionDao(): UrbanDictionaryDao

    companion object {
        private val databaseName = "urban_dictionary"

        @Volatile
        private var INSTANCE: UrbanDictionaryDatabase? = null

        fun getDataBase(context: Context): UrbanDictionaryDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            UrbanDictionaryDatabase::class.java,
            databaseName
        ).build()
    }
}