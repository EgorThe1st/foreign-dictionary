package ru.android.foreigndictionary.presentation.translator

import ru.android.foreigndictionary.common.Languages
import ru.android.foreigndictionary.presentation.base.BasePresenter
import ru.android.foreigndictionary.presentation.base.ErrorView
import ru.android.foreigndictionary.presentation.base.LoadingView

interface ITranslatorPresenter : BasePresenter {

    fun onTranslateClicked(word: String, langs: Pair<Languages, Languages>)
    fun stop()

    interface View : LoadingView, ErrorView {

        fun showTranslation(translation: String)
        fun notifyListener()
    }
}