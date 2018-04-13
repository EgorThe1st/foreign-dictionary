package ru.android.foreigndictionary.data.repository

interface IHistoryRepository {
    fun getDictionary(): ArrayList<Pair<String, String>>
    fun search(chars: String): ArrayList<Pair<String, String>>
    fun clearDictionary()
}