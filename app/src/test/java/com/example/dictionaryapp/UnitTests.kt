package com.example.dictionaryapp

import com.example.dictionaryapp.repository.UrbanDictionaryRepository
import com.example.dictionaryapp.utils.SortTypeEnum
import com.example.dictionaryapp.utils.sampleDefinitionListForUnitTest
import com.example.dictionaryapp.viewmodel.UrbanDictionaryViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class UnitTests {

    @Mock
    private lateinit var urbanDictionaryRepository: UrbanDictionaryRepository
    private lateinit var urbanDictionaryViewModel: UrbanDictionaryViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        urbanDictionaryViewModel = UrbanDictionaryViewModel(urbanDictionaryRepository)
    }

    @Test
    fun sampleDefinitions_sortsDefinitionsByThumbsUp_indexAtZeroHasNineThumbsUp() {
        val enteredWord = "enteredWord"
        var wordDefinitions = sampleDefinitionListForUnitTest(enteredWord)

        Assert.assertEquals(wordDefinitions[0].thumbsUp, 1)
        wordDefinitions = urbanDictionaryViewModel.sortDefinitions(SortTypeEnum.UP, wordDefinitions)
        Assert.assertEquals(wordDefinitions[0].thumbsUp, 9)
    }

    @Test
    fun sampleDefinitions_sortsDefinitionsByThumbsDown_definitionsListSortedbyMostThumbsUp() {
        val enteredWord = "enteredWord"
        var wordDefinitions = sampleDefinitionListForUnitTest(enteredWord)

        Assert.assertEquals(wordDefinitions[2].thumbsDown, 5)
        wordDefinitions = urbanDictionaryViewModel.sortDefinitions(SortTypeEnum.UP, wordDefinitions)
        Assert.assertEquals(wordDefinitions[2].thumbsUp, 1)
    }

    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2)
    }
}