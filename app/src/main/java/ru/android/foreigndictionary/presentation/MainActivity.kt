package ru.android.foreigndictionary.presentation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.android.foreigndictionary.R
import ru.android.foreigndictionary.presentation.history.HistoryFragment
import ru.android.foreigndictionary.presentation.translator.TranslatorFragment


class MainActivity : AppCompatActivity(), TranslatorFragment.Callback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prepareUI()
    }

    override fun updateFragment() {
        val historyFragment: HistoryFragment? =
                supportFragmentManager.fragments.find { it is HistoryFragment } as? HistoryFragment
        historyFragment?.updateDictionary()
    }

    private fun prepareUI() {

        supportActionBar?.elevation = 0f

        view_pager.adapter = PagerAdapter(supportFragmentManager)
        tab_layout.setupWithViewPager(view_pager)
        val tabTranslate = tab_layout.getTabAt(0)
        val tabHistory = tab_layout.getTabAt(1)
        tabTranslate?.icon = getDrawable(R.drawable.ic_translate_black_24dp)
        tabHistory?.icon = getDrawable(R.drawable.ic_view_list_black_24dp)

    }

    private inner class PagerAdapter(fragmentManager: FragmentManager) :
            FragmentPagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment {
            return if (position == 1) HistoryFragment.newInstance() else TranslatorFragment.newInstance()
        }

        override fun getCount() = 2
    }

}
