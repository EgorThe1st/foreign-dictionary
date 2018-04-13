package ru.android.foreigndictionary.data.dataSource.remote

import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single
import org.json.JSONObject

class RemoteDataSource : IRemoteDataSource {

    companion object {

        private const val URL = "https://translate.yandex.net/api/v1.5/tr.json/translate?key="
        private const val KEY = "trnsl.1.1.20180407T123622Z." +
                "b63d01c91304d4a8.86afe891de735913d30c657b8e824e743dfc42bb"
        private const val HEADER_TEXT = "text"
        private const val HEADER_LANG = "lang"
    }

    override fun translateWord(word: String, lang: String): Single<JSONObject> {
        return Rx2AndroidNetworking.post(URL + KEY)
                .addUrlEncodeFormBodyParameter(HEADER_TEXT, word)
                .addUrlEncodeFormBodyParameter(HEADER_LANG, lang)
                .build()
                .jsonObjectSingle
    }
}