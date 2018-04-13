package ru.android.foreigndictionary.data.repository

import io.reactivex.Single
import ru.android.foreigndictionary.common.Languages

interface ITranslatorRepository {

    fun translateWord(word: String, langs: Pair<Languages, Languages>): Single<String>
    fun saveTranslation(first: String, second: String)
    fun saveSession()
    fun prepareDictionary()
}