package comcesar1287.github.githubapp.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import comcesar1287.github.githubapp.R
import comcesar1287.github.githubapp.models.UserDetail
import comcesar1287.github.githubapp.utils.GlideApp
import comcesar1287.github.githubapp.utils.Status
import comcesar1287.github.githubapp.viewModels.UserViewModel
import kotlinx.android.synthetic.main.activity_user_details.*
import kotlinx.android.synthetic.main.user_fragment_content.*

class UserDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        val avatarUrl = intent.getStringExtra("avatar")
        val login = intent.getStringExtra("login")

        loadAvatar(avatarUrl)
        loadContent(login)
    }

    private fun loadAvatar(avatarUrl: String) {
        GlideApp
            .with(this)
            .load(avatarUrl)
            .into(user_fragment_image)
    }

    private fun loadContent(login: String) {
        val viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        viewModel.getUserDetails(login).observe(this, Observer { resource ->
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
        user_fragment_content_progress_bar.visibility = progress
        user_fragment_content_rl.visibility = content
        no_content.visibility = noContent
        errorLayout.visibility = error
    }

    private fun updateUI(user: UserDetail) {
        user_fragment_collapsing_toolbar.title = user.name
        user_fragment_content_fullname.text = user.name
        user_fragment_content_username.text = user.login
        user_fragment_content_bio.text = user.bio?.toString() ?: getString(R.string.label_no_content)
        user_fragment_content_mail.text = user.email?.toString() ?: getString(R.string.label_no_content)
        user_fragment_content_location.text = user.location
        user_fragment_content_company.text = user.company
        user_fragment_content_blog.text = user.blog
    }
}
