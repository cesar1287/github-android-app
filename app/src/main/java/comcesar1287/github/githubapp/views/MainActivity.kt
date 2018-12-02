package comcesar1287.github.githubapp.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
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

        val viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        viewModel.getAllUsers().observe(this, Observer { resource ->
            when(resource?.status) {
                Status.LOADING -> {
                    progressCircular.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                Status.SUCCESS -> {
                    resource.data?.let {  usersListNonNull ->
                        usersList.addAll(usersListNonNull)
                        usersAdapter?.notifyDataSetChanged()

                        progressCircular.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                    } ?: run {
                        //TODO
                    }
                }
            }
        })
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerView.layoutManager = layoutManager
        usersAdapter = UsersAdapter(this@MainActivity, usersList)
        recyclerView.adapter = usersAdapter
    }
}
