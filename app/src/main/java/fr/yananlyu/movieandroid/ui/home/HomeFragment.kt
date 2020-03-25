package fr.yananlyu.movieandroid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import fr.yananlyu.movieandroid.*





class HomeFragment : Fragment() {

    lateinit var adapter: RecyclerViewAdapter
    var tabLayout: TabLayout? = null
    lateinit var viewPager: ViewPager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        tabLayout = root.findViewById<TabLayout>(R.id.tabs)
        viewPager = root.findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = TabAdapter(childFragmentManager)

        tabLayout!!.addTab(tabLayout!!.newTab().setText("Most popular"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Upcomings"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Top rated"))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL
        tabLayout!!.setupWithViewPager(viewPager);
        return root
    }

}
