package ru.android.foreigndictionary.data.converter

import org.json.JSONObject


object Converter {

    fun toTranslatedWord(jsonObject: JSONObject): String {
        return jsonObject
                .getJSONArray("text")
                .getString(0)
                .toLowerCase()
    }
}