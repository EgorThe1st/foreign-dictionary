package ru.android.foreigndictionary.presentation.translator

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.PopupMenu
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_translator.*
import org.jetbrains.anko.design.snackbar
import ru.android.foreigndictionary.R
import ru.android.foreigndictionary.common.EMPTY
import ru.android.foreigndictionary.common.Languages
import ru.android.foreigndictionary.common.setGone
import ru.android.foreigndictionary.common.setVisible

class TranslatorFragment : Fragment(), ITranslatorPresenter.View {

    private lateinit var presenter: ITranslatorPresenter
    private var listener: Callback? = null

    companion object {
        fun newInstance() = TranslatorFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Callback) {
            listener = context
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = TranslatorPresenter(this)
        presenter.start()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_translator, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        prepareUI()
    }

    override fun onStop() {
        super.onStop()
        presenter.stop()

    }

    override fun onDetach() {
        super.onDetach()
        presenter.destroy()
    }

    override fun showError() {
        snackbar(parent_coord, getString(R.string.error_abs))
    }

    override fun showNetError() {
        snackbar(parent_coord, getString(R.string.error_no_connection))
    }

    override fun showErrorMessage(error: String) {
        snackbar(parent_coord, error)
    }

    override fun showLoading() {
        translate_button.isEnabled = false
        translated_word.setGone()
        text_input.setGone()
        progress_bar.setVisible()
    }

    override fun hideLoading() {
        translate_button.isEnabled = true
        translated_word.setVisible()
        text_input.setVisible()
        progress_bar.setGone()
    }

    override fun showTranslation(translation: String) {

        translated_word.text = translation
    }

    override fun notifyListener() {
        listener?.updateFragment()
    }

    private fun prepareUI() {

        first_lang.text = getString(R.string.lang_russian)
        second_lang.text = getString(R.string.lang_english)

        first_lang.setOnClickListener { popupMenu(first_lang, second_lang).show() }
        second_lang.setOnClickListener { popupMenu(second_lang, first_lang).show() }

        swap_button.setOnClickListener { swapLangs() }

        translate_button.setOnClickListener { onTranslateClicked() }

        edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (text_input.error != null) text_input.error = null

                if (!translated_word.text.isNullOrEmpty()) translated_word.text = String.EMPTY

                if (!p0.isNullOrEmpty()) {
                    if (clear_button.visibility != View.VISIBLE) clear_button.setVisible()
                } else if (clear_button.visibility != View.GONE) {
                    clear_button.setGone()
                }
            }
        })

        edit_text.setOnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE) {
                onTranslateClicked()
                return@setOnEditorActionListener false

            }
            return@setOnEditorActionListener true
        }

        clear_button.setOnClickListener { edit_text.text.clear() }
    }

    private fun onTranslateClicked() {
        if (!edit_text.text.isNullOrEmpty()) {
            presenter.onTranslateClicked(parseEditText(), parseLangs())
        } else {
            text_input.error = getString(R.string.error_empty_field)
        }
    }

    private fun popupMenu(firstView: TextView, secondView: TextView): PopupMenu {
        val menu = PopupMenu(activity, firstView)
        menu.inflate(R.menu.languages)
        menu.setOnMenuItemClickListener {
            if (it.title == secondView.text) swapLangs() else firstView.text = it.title
            return@setOnMenuItemClickListener true
        }
        return menu
    }

    private fun swapLangs() {
        val first = first_lang.text
        first_lang.text = second_lang.text
        second_lang.text = first
    }

    private fun parseEditText(): String {
        return edit_text.text
                .takeWhile { !it.isWhitespace() }
                .toString()
    }

    private fun parseLangs(): Pair<Languages, Languages> {

        fun toLanguage(text: String): Languages {
            return when (text) {
                getString(R.string.lang_russian) -> Languages.RUSSIAN
                getString(R.string.lang_de) -> Languages.DEUTSCHLAND
                else -> Languages.ENGLISH
            }
        }

        val firstLang = toLanguage(first_lang.text.toString())
        val secondLang = toLanguage(second_lang.text.toString())
        return Pair(firstLang, secondLang)
    }

    interface Callback {
        fun updateFragment()
    }
}
