package ru.android.foreigndictionary.common

import android.view.View
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun View.setGone() {
    visibility = View.GONE
}

fun View.setVisible() {
    visibility = View.VISIBLE
}

fun <T> Single<T>.schedulersIoToMain(): Single<T> {
    return subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread())
}

inline val String.Companion.EMPTY: String
    get() = ""
