package com.example.dictionaryapp.ui

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionaryapp.R
import com.example.dictionaryapp.databinding.ActivityMainBinding
import com.example.dictionaryapp.models.WordDefinitions
import com.example.dictionaryapp.utils.SortTypeEnum
import com.example.dictionaryapp.viewmodel.UrbanDictionaryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val urbanDictionaryViewModel: UrbanDictionaryViewModel by viewModel()
    private lateinit var dataAdapter: DataAdapter

    companion object {
        private const val ENTERED_TEXT = "ENTERED_TEXT"
        private const val SELECTED_RADIO_BUTTON_ID = "SELECTED_RADIO_BUTTON_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViews()

        if (savedInstanceState != null) {
            savedInstanceState.getString(ENTERED_TEXT)
                ?.let { binding.enteredWord.text = SpannableStringBuilder(it) }
            savedInstanceState.getInt(SELECTED_RADIO_BUTTON_ID).let {
                if (it != -1) {
                    findViewById<RadioButton>(it).isChecked = true
                }
            }
        }

        urbanDictionaryViewModel.wordDefinitions.observe(this@MainActivity, definitionObserver)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            putString(ENTERED_TEXT, binding.enteredWord.text.toString().toLowerCase())
            putInt(SELECTED_RADIO_BUTTON_ID, binding.radioSort.checkedRadioButtonId)
        }
    }

    private fun setUpViews() {
        initRecyclerView()
        initRadioGroup()
        binding.btnSearch.setOnClickListener { handleSearch() }
        binding.btnClear.setOnClickListener { clearAll() }
    }

    private fun initRecyclerView() {
        with(binding.recycleview) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            dataAdapter = DataAdapter()
            binding.recycleview.adapter = dataAdapter
        }
    }

    private fun initRadioGroup() = binding.radioSort.setOnCheckedChangeListener { radioGroup, _ ->
        run {
            val selectedButton = radioGroup.checkedRadioButtonId
            if (selectedButton != -1) {
                when (selectedButton) {
                    binding.thumbsUp.id -> {
                        urbanDictionaryViewModel.sortDefinitions(SortTypeEnum.UP)
                    }
                    binding.thumbsDown.id -> {
                        urbanDictionaryViewModel.sortDefinitions(SortTypeEnum.DOWN)
                    }
                }
            }
        }
    }

    private fun handleSearch() {
        val wordEntered = binding.enteredWord.text.toString().toLowerCase().trim()
        if (wordEntered.isEmpty()) {
            Toast.makeText(this, getString(R.string.searchHint), Toast.LENGTH_LONG).show()
            return
        }
        binding.progressBar.visibility = View.VISIBLE

        urbanDictionaryViewModel.getWordDefinitions(wordEntered)
    }

    private fun clearAll() {
        with(binding) {
            enteredWord.text = SpannableStringBuilder("")
            thumbsUp.isChecked = false
            thumbsDown.isChecked = false
        }
        urbanDictionaryViewModel.clearWordDefinition()
    }

    private val definitionObserver = Observer<List<WordDefinitions>> {
        binding.progressBar.visibility = View.GONE
        dataAdapter.updateDataSet(it)
    }
}
