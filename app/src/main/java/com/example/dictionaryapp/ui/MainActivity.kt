package com.example.dictionaryapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.ProgressBar
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.R
import com.example.dictionaryapp.databinding.ActivityMainBinding
import com.example.dictionaryapp.models.WordDefinitions
import com.example.dictionaryapp.utils.SortTypeEnum
import com.example.dictionaryapp.viewmodel.UrbanDictionaryViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val urbanDictionaryViewModel: UrbanDictionaryViewModel by viewModel()
    private lateinit var mAdapter: DataAdapter
    private lateinit var dataSet: List<WordDefinitions>
    private lateinit var recyclerView: RecyclerView

    private lateinit var progress_Bar: ProgressBar
    private var handlerThread = Handler()

    companion object {
        private const val ENTERED_TEXT = "ENTERED_TEXT"
        private const val SELECTED_RADIO_BUTTON_ID = "SELECTED_RADIO_BUTTON_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        initRecyclerView()
        initRadioGroup()

        btnSearch.setOnClickListener { setupSearchButton() }
        btnClear.setOnClickListener { clearAll() }

        if (savedInstanceState != null) {
            val enteredText = savedInstanceState.getString(ENTERED_TEXT)
            val buttonId = savedInstanceState.getInt(SELECTED_RADIO_BUTTON_ID)
            if (enteredText != null) {
                entered_word.text = SpannableStringBuilder(enteredText)
            }
            if (buttonId != -1) {
                findViewById<RadioButton>(buttonId).isChecked = true
            }
        }
    }

    private fun initRecyclerView() {
        recyclerView = recycleview
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        dataSet = urbanDictionaryViewModel.wordDefinitions.value ?: emptyList()
        mAdapter = DataAdapter(dataSet)
        urbanDictionaryViewModel.wordDefinitions.observe(this@MainActivity, Observer {
            updateRecycleView(it ?: emptyList())
        })
    }

    private fun setupSearchButton() {
        updateProgressBar()
        urbanDictionaryViewModel.getWordDefinitions(entered_word.text.toString())
    }

    private fun initRadioGroup() = radio_sort.setOnCheckedChangeListener { radioGroup, _ ->
        run {
            val selectedButton = radioGroup.checkedRadioButtonId
            if (selectedButton != -1) {
                dataSet = when (selectedButton) {
                    thumbsUp.id -> {
                        urbanDictionaryViewModel.sortDefinitions(SortTypeEnum.UP, dataSet)
                    }
                    thumbsDown.id -> {
                        urbanDictionaryViewModel.sortDefinitions(SortTypeEnum.DOWN, dataSet)
                    }
                    else -> dataSet
                }
                updateRecycleView(dataSet)
            }
        }
    }

    private fun clearAll() {
        entered_word.text = SpannableStringBuilder("")
        thumbsUp.isChecked = false
        thumbsDown.isChecked = false
        updateRecycleView(emptyList())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            putString(ENTERED_TEXT, entered_word.text.toString())
            putInt(SELECTED_RADIO_BUTTON_ID, radio_sort.checkedRadioButtonId)
        }
    }

    private fun updateProgressBar() {
        val wordEntered = entered_word
        if (wordEntered.text.isEmpty()) {
            return
        }
        progress_Bar = progressBar
        progress_Bar.visibility = View.VISIBLE
        var progressStatus = 0
        Thread(Runnable {
            run {
                while (progressStatus < 100) {
                    progressStatus += 30
                    android.os.SystemClock.sleep(200)
                    handlerThread.post { progress_Bar.progress = progressStatus }
                }
                handlerThread.post {
                    progress_Bar.setProgress(100)
                    progress_Bar.visibility = View.GONE
                }
            }
        }).start()
        recyclerView.adapter = mAdapter
    }

    private fun updateRecycleView(newData: List<WordDefinitions>) {
        dataSet = newData
        mAdapter.updateDataSet(dataSet)
    }
}
