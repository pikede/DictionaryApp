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
            urbanDictionaryRepository.getSearchResults(enteredWord)
        )
        // handle error scenario
    }

    fun clearWordDefinition() {
        wordDefinitions.postValue(emptyList())
    }

    // sorts word definitions and returns back a sorted list
    fun sortDefinitions(
        sortType: SortTypeEnum
    ) {
        // sort on Dispatcher IO as this list might be very large
        viewModelScope.launch(Dispatchers.IO) {
            wordDefinitions.value?.let {
                // sorts by thumbs up or thumbs down
                val map = mutableMapOf<Int, WordDefinitions>()
                when (sortType) {
                    SortTypeEnum.UP -> {
                        for (word in it) {
                            map[word.thumbsUp] = word
                        }
                    }

                    SortTypeEnum.DOWN -> {
                        for (word in it) {
                            map[word.thumbsDown] = word
                        }
                    }
                }

                // reverses increasing order
                wordDefinitions.postValue(map.toSortedMap().values.reversed())
            }
        }
    }

}