package com.example.dictionaryapp.repository.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DictionaryService {

    companion object {
        private const val baseUrl = "https://mashape-community-urban-dictionary.p.rapidapi.com"

        private val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val urbanDictionaryRetrofit: UrbanDictionaryApi =
            retrofit.create(UrbanDictionaryApi::class.java)
    }
}