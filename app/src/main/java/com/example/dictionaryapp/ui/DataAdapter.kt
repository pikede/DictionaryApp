package com.example.dictionaryapp.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.R
import com.example.dictionaryapp.models.WordDefinitions
import kotlinx.android.synthetic.main.searched_items.view.*
import kotlin.random.Random

class DataAdapter(private var dataset: List<WordDefinitions> = emptyList()) :
    RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.searched_items, parent, false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset.count()
    }

    // use diffutil here and remove notify dataset changed
    fun updateDataSet(newDataset: List<WordDefinitions>) {
        dataset = newDataset
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataset[position])
        (holder.itemView as LinearLayout).setBackgroundColor(randomColor())
    }

    private fun randomColor() = Color.rgb(
        Random.nextInt(100) + 45,
        Random.nextInt(150) + 44,
        Random.nextInt(1650) + 30
    )

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(wordDefinitions: WordDefinitions) {
            itemView.word_definition.text = wordDefinitions.definition
            itemView.thumbs_up.text = wordDefinitions.thumbsUp.toString()
            itemView.thumbs_down.text = wordDefinitions.thumbsDown.toString()
            itemView.author.text = wordDefinitions.author
            itemView.written_on.text = wordDefinitions.writtenOn
        }
    }
}