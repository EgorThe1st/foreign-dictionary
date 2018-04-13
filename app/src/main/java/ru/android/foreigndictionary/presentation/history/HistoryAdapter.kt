package ru.android.foreigndictionary.presentation.history

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.history_item.view.*
import ru.android.foreigndictionary.R

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private val items: ArrayList<Pair<String, String>> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.history_item, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.first_word.text = items[position].first
        holder.itemView.second_word.text = items[position].second
    }

    fun updateDictionary(map: ArrayList<Pair<String, String>>) {

        val diffResult = DiffUtil.calculateDiff(HistoryDiffUtil(items, map))
        items.clear()
        items.addAll(map)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}

