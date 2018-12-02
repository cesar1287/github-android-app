package comcesar1287.github.githubapp.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import comcesar1287.github.githubapp.R
import comcesar1287.github.githubapp.adapters.UserReposAdapter
import comcesar1287.github.githubapp.models.UserRepo
import comcesar1287.github.githubapp.utils.Status
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
        loadContent(login)

        buttonRetry.setOnClickListener {
            loadContent(login)
        }
    }

    private fun loadContent(login: String) {
        val viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        viewModel.getUserRepos(login).observe(this, Observer { resource ->
            when(resource?.status) {
                Status.LOADING -> {
                    setVisibility(View.VISIBLE, View.GONE, View.GONE, View.GONE)
                }
                Status.ERROR -> {
                    setVisibility(View.GONE, View.GONE, View.GONE, View.VISIBLE)
                    errorMessage.text = resource.message
                }
                Status.SUCCESS -> {
                    resource.data?.let {  userReposListNonNull ->
                        userReposList.addAll(userReposListNonNull)
                        userReposAdapter?.notifyDataSetChanged()

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
        val layoutManager = LinearLayoutManager(this@UserReposActivity)
        recyclerView.layoutManager = layoutManager
        userReposAdapter = UserReposAdapter(this@UserReposActivity, userReposList)
        recyclerView.adapter = userReposAdapter
    }
}
