package fr.yananlyu.movieandroid

import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.widget.Toast
import fr.yananlyu.movieandroid.model.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.MenuItem
import androidx.navigation.ui.*





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
                R.id.nav_home, R.id.nav_favoris
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
/*        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.action_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }*/

        // return true

/*        val searchItem = menu.findItem(R.id.action_search)
        if (searchItem!=null) {
            val searchView = searchItem.actionView as SearchView
*//*            searchView.setOnSearchClickListener(object :View.OnClickListener {
                override fun onClick(p0: View?) {
                    val fragment = ListMovieFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit()                }
            })*//*
*//*            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    println("Search" + p0)
                    if (p0!!.isNotEmpty()) {
                        val query = p0.toLowerCase()
                        searchQuery(query)

                    }
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return true
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })*//*
        }*/
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
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
    fun searchQuery(query: String) {
        val service = RetrofitInstance.getInstance().create(MovieService::class.java)
        service.searchMovie(query).enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                if (response.isSuccessful && response.body() != null) { //Manage data
                    val collection = response.body()
                    if(collection != null) {
                        // displayData(collection)
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.app_error), Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<Result>, t: Throwable) { //Manage errors
            }
        })
    }

}
