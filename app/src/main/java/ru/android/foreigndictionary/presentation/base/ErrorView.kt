package ru.android.foreigndictionary.presentation.base

interface ErrorView {
    fun showErrorMessage(error: String)
    fun showError()
    fun showNetError()
}