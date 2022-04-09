package com.nislav.settleexpenses.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nislav.settleexpenses.R

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class PagerAdapter(
    private val fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    /**
     * Returns title for the corresponding Tab on [position].
     */
    fun getTitle(position: Int) = fragmentActivity.getString(TAB_TITLES[position])

    override fun getItemCount(): Int = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment =
        when (position) {
            TAB_1_EXPENSES -> ExpensesFragment()
            TAB_2_CONTACTS -> ContactsFragment()
            else -> error("Fragment of unkonwn position requested ($position)")
        }
}

private val TAB_TITLES = arrayOf(
    R.string.tab_1_expenses,
    R.string.tab_2_contacts
)

private const val TAB_1_EXPENSES = 0
private const val TAB_2_CONTACTS = 1