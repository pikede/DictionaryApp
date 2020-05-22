package com.example.dictionaryapp.utils

import com.example.dictionaryapp.models.WordDefinitions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.ArrayList

val listType: Type = object : TypeToken<ArrayList<WordDefinitions>>() {}.type
val gson = Gson()

fun listToJson(list: List<WordDefinitions>): String = gson.toJson(list, listType)
fun listFromJson(string: String): List<WordDefinitions> = gson.fromJson(string, listType)