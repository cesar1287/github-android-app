package comcesar1287.github.githubapp.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import comcesar1287.github.githubapp.R
import comcesar1287.github.githubapp.adapters.UserReposAdapter
import comcesar1287.github.githubapp.models.UserRepo
import comcesar1287.github.githubapp.viewModels.UserViewModel
import kotlinx.android.synthetic.main.activity_user_repos.*

class UserReposActivity : AppCompatActivity() {

    private var userReposAdapter: UserReposAdapter? = null
    private val userReposList: MutableList<UserRepo> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_repos)

        val login = intent.getStringExtra("login")

        setupRecyclerView()

        val viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        viewModel.getUserRepos(login).observe(this, Observer { list ->
            list?.let {  userReposListNonNull ->
                userReposList.addAll(userReposListNonNull)
                userReposAdapter?.notifyDataSetChanged()
            } ?: run {
                //TODO
            }
        })
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this@UserReposActivity)
        recyclerView.layoutManager = layoutManager
        userReposAdapter = UserReposAdapter(this@UserReposActivity, userReposList)
        recyclerView.adapter = userReposAdapter
    }
}
