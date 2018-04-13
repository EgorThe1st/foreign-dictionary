package ru.android.foreigndictionary.data.repository

import io.reactivex.Single
import ru.android.foreigndictionary.common.Languages
import ru.android.foreigndictionary.data.converter.Converter
import ru.android.foreigndictionary.data.dataSource.local.ILocalDataSource
import ru.android.foreigndictionary.data.dataSource.local.LocalDataSource
import ru.android.foreigndictionary.data.dataSource.remote.RemoteDataSource

class TranslatorRepository(
        private val remoteDataSource: RemoteDataSource = RemoteDataSource(),
        private val localDataSource: ILocalDataSource = LocalDataSource(),
        private val converter: Converter = Converter
) : ITranslatorRepository {

    override fun translateWord(word: String, langs: Pair<Languages, Languages>): Single<String> {
        return remoteDataSource
                .translateWord(word, parseLang(langs))
                .map { converter.toTranslatedWord(it) }
    }

    override fun saveTranslation(first: String, second: String) {
        val current = localDataSource.getDictionary()
        if (!current.contains(Pair(first, second))) {
            localDataSource.addTranslation(first, second)
        }
    }

    override fun saveSession() {
        localDataSource.saveToStorage()
    }

    override fun prepareDictionary() {
        localDataSource.getFromStorage()
    }

    private fun parseLang(langs: Pair<Languages, Languages>): String {

        fun toLowerCase(string: String) = string.take(2).toLowerCase()

        val first = toLowerCase(langs.first.name)
        val second = toLowerCase(langs.second.name)
        return "$first-$second" // to api "en-ru" pattern
    }
}