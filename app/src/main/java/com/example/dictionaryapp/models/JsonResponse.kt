package com.example.dictionaryapp.models

import com.google.gson.annotations.SerializedName

data class JsonResponse(@SerializedName("list") val list: List<WordDefinitions>)
