package ru.android.foreigndictionary.presentation.history

import android.support.v7.util.DiffUtil

class HistoryDiffUtil(
        private val oldMap: ArrayList<Pair<String, String>>,
        private val newMap: ArrayList<Pair<String, String>>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldMap.size

    override fun getNewListSize(): Int = newMap.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldMap.size == newMap.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldMap == newMap
    }
}