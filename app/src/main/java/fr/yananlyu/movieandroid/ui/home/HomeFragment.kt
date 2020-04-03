package fr.yananlyu.movieandroid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import fr.yananlyu.movieandroid.*
import fr.yananlyu.movieandroid.adapter.TabAdapter


class HomeFragment : Fragment() {
    var tabLayout: TabLayout? = null
    lateinit var viewPager: ViewPager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        tabLayout = root.findViewById<View>(R.id.tabs) as TabLayout
        viewPager = root.findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = TabAdapter(childFragmentManager)

        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL
        tabLayout!!.setupWithViewPager(viewPager);
        return root
    }

}
