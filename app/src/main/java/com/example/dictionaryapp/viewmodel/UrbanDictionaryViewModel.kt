package com.example.dictionaryapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.models.WordDefinitions
import com.example.dictionaryapp.repository.UrbanDictionaryRepository
import com.example.dictionaryapp.utils.SortTypeEnum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UrbanDictionaryViewModel(
    private val urbanDictionaryRepository: UrbanDictionaryRepository
) : ViewModel() {

    val wordDefinitions = MutableLiveData<List<WordDefinitions>>()

    fun getWordDefinitions(enteredWord: String) = viewModelScope.launch(Dispatchers.IO) {
        wordDefinitions.postValue(
            if (enteredWord.isNotEmpty()) {
                urbanDictionaryRepository.getSearchResults(enteredWord)
            } else {
                emptyList()
            }
        )
    }

    // sorts word definitions and returns back a sorted list
    fun sortDefinitions(
        sortType: SortTypeEnum,
        wordDefinitions: List<WordDefinitions>
    ): List<WordDefinitions> {
        val map = mutableMapOf<Int, WordDefinitions>()
        when (sortType) {
            SortTypeEnum.UP -> {
                for (definition in wordDefinitions) {
                    map[definition.thumbsUp] = definition
                }
            }

            SortTypeEnum.DOWN -> {
                for (definition in wordDefinitions) {
                    map[definition.thumbsDown] = definition
                }
            }
        }

        // reverses sorted map natural increasing order
        return map.toSortedMap().values.reversed()
    }

}