package com.example.dictionaryapp.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.R
import com.example.dictionaryapp.models.WordDefinitions
import kotlinx.android.synthetic.main.searched_items.view.*
import kotlin.random.Random

class DataAdapter : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    private var dataset: ArrayList<WordDefinitions> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.searched_items, parent, false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    fun updateDataSet(newDataset: List<WordDefinitions>) {
        val diffUtil = WordDiffUtil(dataset, newDataset)
        val diffResult = DiffUtil.calculateDiff(diffUtil)

        dataset.clear()
        dataset.addAll(newDataset)

        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(wordDefinitions: WordDefinitions) {
            view.word_definition.text = wordDefinitions.definition
            view.thumbs_up.text = wordDefinitions.thumbsUp.toString()
            view.thumbs_down.text = wordDefinitions.thumbsDown.toString()
            view.author.text = wordDefinitions.author
            view.written_on.text = wordDefinitions.writtenOn

            view.setBackgroundColor(randomColor())
        }

        private fun randomColor() = Color.rgb(
            Random.nextInt(100) + 45,
            Random.nextInt(150) + 44,
            Random.nextInt(1650) + 30
        )
    }

    class WordDiffUtil(
        private val oldList: List<WordDefinitions>,
        private val newList: List<WordDefinitions>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].definition == newList[newItemPosition].definition &&
                    oldList[oldItemPosition].author == newList[newItemPosition].author &&
                    oldList[oldItemPosition].permalink == newList[newItemPosition].permalink &&
                    oldList[oldItemPosition].defId == newList[newItemPosition].defId &&
                    oldList[oldItemPosition].currentVote == newList[newItemPosition].currentVote &&
                    oldList[oldItemPosition].writtenOn == newList[newItemPosition].writtenOn
        }
    }
}