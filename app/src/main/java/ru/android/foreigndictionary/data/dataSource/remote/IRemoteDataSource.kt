package ru.android.foreigndictionary.data.dataSource.remote

import io.reactivex.Single
import org.json.JSONObject

interface IRemoteDataSource {

    fun translateWord(word: String, lang: String): Single<JSONObject>
}