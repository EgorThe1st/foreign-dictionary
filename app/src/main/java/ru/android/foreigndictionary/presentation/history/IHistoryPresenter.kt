package ru.android.foreigndictionary.presentation.history

import ru.android.foreigndictionary.presentation.base.BasePresenter
import java.util.*

interface IHistoryPresenter : BasePresenter {

    fun search(chars: String)
    fun clear()

    interface View {
        fun showDictionary(dictionary: ArrayList<Pair<String, String>>)
        fun showEmptyDictionaryPlaceholder()
        fun showNotFoundPlaceholder()
        fun hidePlaceholder()
        fun clearSearchView()
        fun hideClearButton()
        fun showClearButton()
    }
}