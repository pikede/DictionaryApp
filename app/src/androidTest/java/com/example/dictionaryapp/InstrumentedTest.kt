package com.example.dictionaryapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dictionaryapp.database.DatabaseEntity
import com.example.dictionaryapp.database.UrbanDictionaryDao
import com.example.dictionaryapp.database.UrbanDictionaryDatabase
import com.example.dictionaryapp.models.WordDefinitions
import com.example.dictionaryapp.utils.sampleDefinitionList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class InstrumentedTest {

    private lateinit var urbanDictionaryDatabase: UrbanDictionaryDatabase
    private lateinit var urbanDictionaryDao: UrbanDictionaryDao
    private val gson = Gson()
    private val listType = object : TypeToken<ArrayList<WordDefinitions>>() {}.type

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        urbanDictionaryDatabase =
            Room.inMemoryDatabaseBuilder(context, UrbanDictionaryDatabase::class.java).build()
        urbanDictionaryDao = urbanDictionaryDatabase.wordDefinitionDao()
    }

    @After
    @Throws(IOException::class)
    fun finishedTesting() {
        urbanDictionaryDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun givenSampleDefinition_writeOneDefinitionToDatabase_databaseSizeIsOne() {
        val enteredWord = "enteredWord"
        val wordDefinition = sampleDefinitionList(enteredWord)

        val jsonString = gson.toJson(wordDefinition, listType)
        urbanDictionaryDao.insertWordDefinition(DatabaseEntity(enteredWord, jsonString))

        val databaseQueryResult = urbanDictionaryDao.getDefinitionsByWord(enteredWord)
        assertEquals(databaseQueryResult.size, 1)
    }

    @Test
    @Throws(Exception::class)
    fun givenSampleDefinition_writeAndReadDefinitionFromDatabase_databaseReadingIsEqualToEnteredWord() {
        val enteredWord = "enteredWord"
        val wordDefinition = sampleDefinitionList(enteredWord)

        val jsonString = gson.toJson(wordDefinition, listType)
        urbanDictionaryDao.insertWordDefinition(DatabaseEntity(enteredWord, jsonString))

        val databaseQueryResult = urbanDictionaryDao.getDefinitionsByWord(enteredWord)
        assertEquals(
            databaseQueryResult[0].run {
                gson.fromJson(this.json, listType) as List<WordDefinitions>
            }[0].word, enteredWord
        )
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.dictionaryapp", appContext.packageName)
    }
}