package ru.android.foreigndictionary.data.repository

import ru.android.foreigndictionary.data.dataSource.local.ILocalDataSource
import ru.android.foreigndictionary.data.dataSource.local.LocalDataSource

class HistoryRepository(private val localDataSource: ILocalDataSource = LocalDataSource()) :
        IHistoryRepository {

    override fun getDictionary(): ArrayList<Pair<String, String>> {
        return localDataSource.getDictionary()
    }

    override fun search(chars: String): ArrayList<Pair<String, String>> {
        val dictionary = localDataSource.getDictionary()
        return dictionary.filter { it.first.contains(chars) || it.second.contains(chars) } as ArrayList
    }

    override fun clearDictionary() {
        localDataSource.clearDictionary()
    }
}