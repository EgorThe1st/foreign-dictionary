package ru.android.foreigndictionary.presentation.history

import ru.android.foreigndictionary.data.repository.HistoryRepository
import ru.android.foreigndictionary.data.repository.IHistoryRepository

class HistoryPresenter(private val view: IHistoryPresenter.View) : IHistoryPresenter {

    private val repository: IHistoryRepository = HistoryRepository()

    override fun start() {
        view.clearSearchView()
        val dictionary = repository.getDictionary()
        if (dictionary.isNotEmpty()) {
            view.hidePlaceholder()
            view.showClearButton()
            view.showDictionary(dictionary)
        } else {
            view.showEmptyDictionaryPlaceholder()
            view.hideClearButton()
        }
    }

    override fun search(chars: String) {
        val mapFiltered = repository.search(chars)
        if (mapFiltered.isNotEmpty()) {
            view.hidePlaceholder()
            view.showDictionary(mapFiltered)
        } else {
            view.showNotFoundPlaceholder()
        }
    }

    override fun clear() {
        repository.clearDictionary()
        start()
    }

    override fun destroy() {}
}