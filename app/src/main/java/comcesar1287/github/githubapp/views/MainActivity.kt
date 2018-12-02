package comcesar1287.github.githubapp.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import comcesar1287.github.githubapp.R
import comcesar1287.github.githubapp.adapters.UsersAdapter
import comcesar1287.github.githubapp.models.User
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
        viewModel.getAllUsers().observe(this, Observer { list ->
            list?.let {  usersListNonNull ->
                usersList.addAll(usersListNonNull)
                usersAdapter?.notifyDataSetChanged()
            } ?: run {
                //TODO
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
