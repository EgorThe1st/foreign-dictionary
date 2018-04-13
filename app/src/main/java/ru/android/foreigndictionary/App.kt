package ru.android.foreigndictionary

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.androidnetworking.AndroidNetworking

class App : Application() {

    companion object {

        private lateinit var app: App

        fun isOnline(): Boolean {
            val connectivityManager =
                    app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return (networkInfo != null && networkInfo.isConnected)
        }

        fun getFilePath(): String = app.filesDir.path.toString()
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        AndroidNetworking.initialize(this)
    }
}