package ru.android.foreigndictionary.data.dataSource.local

import ru.android.foreigndictionary.App
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class LocalDataSource : ILocalDataSource {

    companion object {
        private val PATH = "${App.getFilePath()}/dictionary.txt"
        private val dictionary = ArrayList<Pair<String, String>>()
    }

    override fun addTranslation(first: String, second: String) {
        dictionary.add(0, Pair(first, second)) // add always to top of the list
    }

    override fun getDictionary(): ArrayList<Pair<String, String>> {
        return dictionary
    }

    override fun saveToStorage() {
        var objectOutputStream: ObjectOutputStream? = null
        try {
            val fileOutputStream = FileOutputStream(PATH)
            objectOutputStream = ObjectOutputStream(fileOutputStream)
            objectOutputStream.writeObject(dictionary)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            objectOutputStream?.close()
        }
    }

    override fun getFromStorage(): ArrayList<Pair<String, String>> {
        var list: ArrayList<Pair<String, String>> = ArrayList()
        try {
            val fileInputStream = FileInputStream(PATH)
            val objectInputStream = ObjectInputStream(fileInputStream)
            list = objectInputStream.readObject() as ArrayList<Pair<String, String>>
            objectInputStream.close()
            dictionary.clear()
            dictionary.addAll(list)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            return list
        }
    }

    override fun clearDictionary() {
        dictionary.clear()
    }
}