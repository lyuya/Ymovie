package fr.yananlyu.movieandroid

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import fr.yananlyu.movieandroid.ui.home.HomeFragment
import fr.yananlyu.movieandroid.ui.home.PopularFragment
import fr.yananlyu.movieandroid.ui.home.TopRatedFragment
import fr.yananlyu.movieandroid.ui.home.UpcomingFragment

class TabAdapter( fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    private var totalTabs = 3
    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()
    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return PopularFragment()
            }
            1 -> {
                return PopularFragment()
            }
            2 -> {
                return PopularFragment()
            }
            else -> return PopularFragment()
        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title: String = ""
        when (position) {
            0 -> {
                title = "Popular"
            }
            1 -> {
                title = "Upcoming"
            }
            2 -> {
                title = "Top rated"
            }
        }
        return title
    }
    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }
}