package comcesar1287.github.githubapp.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import comcesar1287.github.githubapp.R
import comcesar1287.github.githubapp.models.UserDetail
import comcesar1287.github.githubapp.utils.*
import comcesar1287.github.githubapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.item_user.*

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val query = intent.getStringExtra(KEY_EXTRA_QUERY)
        loadContent(query)

        setupToolbar()
    }

    private fun loadContent(query: String) {
        val viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        viewModel.getUserDetails(query)?.observe(this, Observer { resource ->
            when(resource?.status) {
                Status.LOADING -> {
                    setVisibility(View.VISIBLE, View.GONE, View.GONE, View.GONE)
                }
                Status.ERROR -> {
                    setVisibility(View.GONE, View.GONE, View.GONE, View.VISIBLE)

                    errorMessage.text = resource.message
                }
                Status.SUCCESS -> {
                    resource.data?.let {  usersDetailsNonNull ->
                        setVisibility(View.GONE, View.VISIBLE, View.GONE, View.GONE)
                        updateUI(usersDetailsNonNull)
                    } ?: run {
                        setVisibility(View.GONE, View.GONE, View.VISIBLE, View.GONE)
                    }
                }
            }
        })
    }

    private fun setVisibility(progress: Int, content: Int, noContent: Int, error: Int) {
        progressCircular.visibility = progress
        contentLayout.visibility = content
        no_content.visibility = noContent
        errorLayout.visibility = error
    }

    private fun updateUI(user: UserDetail) {
        login.text = user.login
        type.text = user.type

        GlideApp
            .with(this)
            .load(user.avatarUrl)
            .into(avatar)

        val intentDetails = Intent(this, UserDetailsActivity::class.java)
        intentDetails.putExtra(KEY_EXTRA_LOGIN, user.login)
        intentDetails.putExtra(KEY_EXTRA_AVATAR, user.avatarUrl)

        val intentRepos = Intent(this, UserReposActivity::class.java)
        intentRepos.putExtra(KEY_EXTRA_LOGIN, user.login)

        buttonDetails.setOnClickListener {
            startActivity(intentDetails)
        }

        buttonRepos.setOnClickListener {
            startActivity(intentRepos)
        }
    }

    private fun setupToolbar() {
        supportActionBar?.title = getString(R.string.title_search_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
