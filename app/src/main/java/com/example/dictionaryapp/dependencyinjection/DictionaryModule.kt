package com.example.dictionaryapp.dependencyinjection

import com.example.dictionaryapp.database.UrbanDictionaryDatabase
import com.example.dictionaryapp.repository.UrbanDictionaryRepository
import com.example.dictionaryapp.repository.service.DictionaryService
import com.example.dictionaryapp.viewmodel.UrbanDictionaryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val DictionaryModule = module {
    single { UrbanDictionaryDatabase.getDataBase(androidContext()) }
    single { DictionaryService() }
    single { UrbanDictionaryRepository(get()) }
    viewModel { UrbanDictionaryViewModel(get()) }
}
