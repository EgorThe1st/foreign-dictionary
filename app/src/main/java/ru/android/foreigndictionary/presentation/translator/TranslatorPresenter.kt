package ru.android.foreigndictionary.presentation.translator

import com.androidnetworking.error.ANError
import io.reactivex.disposables.CompositeDisposable
import org.json.JSONObject
import ru.android.foreigndictionary.App
import ru.android.foreigndictionary.common.EMPTY
import ru.android.foreigndictionary.common.Languages
import ru.android.foreigndictionary.common.schedulersIoToMain
import ru.android.foreigndictionary.data.repository.TranslatorRepository

class TranslatorPresenter(val view: ITranslatorPresenter.View) : ITranslatorPresenter {

    private lateinit var repository: TranslatorRepository
    private var disposable = CompositeDisposable()

    override fun start() {
        repository = TranslatorRepository()
        repository.prepareDictionary()
    }

    override fun destroy() {
        disposable.clear()
    }

    override fun onTranslateClicked(word: String, langs: Pair<Languages, Languages>) {

        if (App.isOnline()) {
            view.showLoading()
            repository.translateWord(word, langs)
                    .schedulersIoToMain()
                    .doFinally { view.hideLoading() }
                    .subscribe(
                            {
                                repository.saveTranslation(word, it)
                                view.showTranslation(it)
                                view.notifyListener()
                            },
                            {
                                val errorMessage = parseError(it)
                                if (errorMessage != String.EMPTY) {
                                    view.showErrorMessage(errorMessage)
                                } else {
                                    view.showError()
                                }
                            }
                    )
        } else {
            view.showNetError()
        }
    }

    override fun stop() {
        repository.saveSession()
    }

    private fun parseError(error: Throwable): String {
        val anError = error as? ANError
        anError?.let {
            var errorObj: JSONObject? = null
            try {
                errorObj = JSONObject(it.errorBody.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return errorObj?.getString("message") ?: String.EMPTY
        } ?: return String.EMPTY
    }
}