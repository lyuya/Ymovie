package fr.yananlyu.movieandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import fr.yananlyu.movieandroid.ui.home.ListMovieFragment

class TabAdapter( fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    private var totalTabs = 4
    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()
    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        val numFragment : Int = position
        var bundle = Bundle()
        bundle.putInt("numFragment", numFragment)
        // mettre ListMoviefragment dans le fragmentHome
        var frag = ListMovieFragment()
        frag.arguments = bundle
        return frag
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
            3 -> {
                title = "Now"
            }
        }
        return title
    }
    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }
}