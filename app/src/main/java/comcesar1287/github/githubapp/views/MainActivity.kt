package comcesar1287.github.githubapp.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import comcesar1287.github.githubapp.R
import comcesar1287.github.githubapp.adapters.UsersAdapter
import comcesar1287.github.githubapp.models.User
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
