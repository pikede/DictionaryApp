package com.example.dictionaryapp.models

import com.google.gson.annotations.SerializedName

data class WordDefinitions(
    @SerializedName("definition") val definition: String,
    @SerializedName("permalink") val permalink: String,
    @SerializedName("thumbs_up") val thumbsUp: Int,
    @SerializedName("sound_urls") val soundUrls: List<String>,
    @SerializedName("author") val author: String,
    @SerializedName("word") val word: String,
    @SerializedName("defid") val defId: Int,
    @SerializedName("current_vote") val currentVote: String,
    @SerializedName("written_on") val writtenOn: String,
    @SerializedName("example") val example: String,
    @SerializedName("thumbs_down") val thumbsDown: Int
)