package ru.android.foreigndictionary.data.dataSource.local

interface ILocalDataSource {

    fun saveToStorage()
    fun getFromStorage(): ArrayList<Pair<String, String>>
    fun getDictionary(): ArrayList<Pair<String, String>>
    fun addTranslation(first: String, second: String)
    fun clearDictionary()
}