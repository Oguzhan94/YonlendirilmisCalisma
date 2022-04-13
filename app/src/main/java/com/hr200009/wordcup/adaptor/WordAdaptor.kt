package com.hr200009.wordcup.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hr200009.wordcup.R
import com.hr200009.wordcup.models.Word


class WordAdapter(private val dataSet: ArrayList<Word>) :
    RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class WordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textCardViewSource: TextView = view.findViewById(R.id.textCardViewSource)
        val textCardViewTarget: TextView = view.findViewById(R.id.textCardViewTarget)

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): WordViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.word_card_view, viewGroup, false)

        return WordViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(wordViewHolder: WordViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val currentItem = dataSet[position]
        wordViewHolder.textCardViewSource.text = currentItem.source
        wordViewHolder.textCardViewTarget.text = currentItem.translation
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}