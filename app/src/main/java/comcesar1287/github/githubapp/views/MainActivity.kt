package comcesar1287.github.githubapp.views

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import comcesar1287.github.githubapp.R
import comcesar1287.github.githubapp.adapters.UsersAdapter
import comcesar1287.github.githubapp.models.User
import comcesar1287.github.githubapp.utils.KEY_EXTRA_QUERY
import comcesar1287.github.githubapp.utils.Status
import comcesar1287.github.githubapp.viewModels.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var usersAdapter: UsersAdapter? = null
    private val usersList: MutableList<User> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        loadContent()

        buttonRetry.setOnClickListener {
            loadContent()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the options menu from XML
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        setupSearchView(menu)

        return true
    }

    private fun setupSearchView(menu: Menu) {
        // Get the SearchView and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = (menu.findItem(R.id.search).actionView as SearchView)
        searchView.apply {
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false) // Do not iconify the widget; expand it by default
        }

        setupQueryTextListener(searchView)
    }

    private fun setupQueryTextListener(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { queryNonNull ->
                    val intent = Intent(this@MainActivity, SearchActivity::class.java)
                    intent.putExtra(KEY_EXTRA_QUERY, queryNonNull)
                    startActivity(intent)
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.search -> {
                onSearchRequested()
                true
            }
            else ->  return super.onOptionsItemSelected(item)
        }
    }

    private fun loadContent() {
        val viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        viewModel.getAllUsers().observe(this, Observer { resource ->
            when(resource?.status) {
                Status.LOADING -> {
                    setVisibility(View.VISIBLE, View.GONE, View.GONE, View.GONE)
                }
                Status.ERROR -> {
                    errorMessage.text = resource.message

                    setVisibility(View.GONE, View.GONE, View.GONE, View.VISIBLE)
                }
                Status.SUCCESS -> {
                    resource.data?.let {  usersListNonNull ->
                        usersList.addAll(usersListNonNull)
                        usersAdapter?.notifyDataSetChanged()

                        setVisibility(View.GONE, View.VISIBLE, View.GONE, View.GONE)
                    } ?: run {
                        setVisibility(View.GONE, View.GONE, View.VISIBLE, View.GONE)
                    }
                }
            }
        })
    }

    private fun setVisibility(progress: Int, recycler: Int, noContent: Int, error: Int) {
        progressCircular.visibility = progress
        recyclerView.visibility = recycler
        no_content.visibility = noContent
        errorLayout.visibility = error
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerView.layoutManager = layoutManager
        usersAdapter = UsersAdapter(this@MainActivity, usersList)
        recyclerView.adapter = usersAdapter
    }
}
