package fr.yananlyu.movieandroid.activity

import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.ui.*
import fr.yananlyu.movieandroid.R
import fr.yananlyu.movieandroid.adapter.RecyclerViewAdapter
import fr.yananlyu.movieandroid.ui.favoris.FavorisFragment


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.name_tv, R.id.nav_person, R.id.nav_favoris
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        getSupportFragmentManager().addOnBackStackChangedListener(getListener());

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                val i = Intent(this, SearchActivity::class.java)
                this.startActivity(i)
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)

            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun getListener(): FragmentManager.OnBackStackChangedListener {
        return object : FragmentManager.OnBackStackChangedListener {
            override fun onBackStackChanged() {
                val manager = supportFragmentManager
                val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                val currentFragment: Fragment? = navHostFragment?.getChildFragmentManager()?.getFragments()?.get(0);
                if (FavorisFragment::class.isInstance(currentFragment))
                currentFragment?.onResume()
            }
        }
    }

}
