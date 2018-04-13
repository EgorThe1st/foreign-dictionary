package ru.android.foreigndictionary.presentation.history


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import kotlinx.android.synthetic.main.fragment_history.*
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton
import ru.android.foreigndictionary.R
import ru.android.foreigndictionary.common.EMPTY
import ru.android.foreigndictionary.common.setGone
import ru.android.foreigndictionary.common.setVisible
import java.util.*

class HistoryFragment : Fragment(), IHistoryPresenter.View {

    private lateinit var presenter: IHistoryPresenter
    private lateinit var adapter: HistoryAdapter

    companion object {
        fun newInstance(): HistoryFragment = HistoryFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = HistoryPresenter(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        prepareUI()
        presenter.start()
    }

    override fun showDictionary(dictionary: ArrayList<Pair<String, String>>) {
        adapter.updateDictionary(dictionary)
    }

    override fun showEmptyDictionaryPlaceholder() {
        history_list.setGone()
        search_word.setGone()
        empty_placeholder.text = getString(R.string.error_empty_history)
        empty_placeholder.setVisible()
    }

    override fun showNotFoundPlaceholder() {
        history_list.setGone()
        search_word.setVisible()
        empty_placeholder.text = getString(R.string.error_not_found)
        empty_placeholder.setVisible()
    }

    override fun hidePlaceholder() {
        history_list.setVisible()
        search_word.setVisible()
        empty_placeholder.setGone()
    }

    override fun clearSearchView() {
        search_word.setQuery(String.EMPTY, false)
    }

    override fun hideClearButton() {
        clear_button.setGone()
    }

    override fun showClearButton() {
        clear_button.setVisible()
    }

    fun updateDictionary() {
        presenter.start()
    }

    private fun prepareUI() {
        adapter = HistoryAdapter()
        history_list.layoutManager = LinearLayoutManager(activity)
        history_list.setHasFixedSize(true)
        history_list.adapter = adapter

        search_word.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) {
                    presenter.search(p0)
                    return true
                }
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
        })

        clear_button.setOnClickListener {
            alert(
                    getString(R.string.alert_clear_dictionary),
                    getString(R.string.attention)
            ) {
                yesButton { presenter.clear() }
                noButton { }
            }.show()
        }
    }
}
