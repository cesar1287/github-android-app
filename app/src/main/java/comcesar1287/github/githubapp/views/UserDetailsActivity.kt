package comcesar1287.github.githubapp.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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

        GlideApp
            .with(this)
            .load(avatarUrl)
            .into(user_fragment_image)

        val viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        viewModel.getUserDetails(login).observe(this, Observer { resource ->
            when(resource?.status) {
                Status.LOADING -> {
                    user_fragment_content_progress_bar.visibility = View.VISIBLE
                    user_fragment_content_rl.visibility = View.GONE
                    no_content.visibility = View.GONE
                }
                Status.ERROR -> Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                Status.SUCCESS -> {
                    resource.data?.let {  usersDetailsNonNull ->
                            user_fragment_content_progress_bar.visibility = View.GONE
                            user_fragment_content_rl.visibility = View.VISIBLE
                            no_content.visibility = View.GONE
                            updateUI(usersDetailsNonNull)
                        } ?: run {
                            user_fragment_content_progress_bar.visibility = View.GONE
                            user_fragment_content_rl.visibility = View.GONE
                            no_content.visibility = View.VISIBLE
                        }
                }
            }
        })
    }

    private fun updateUI(user: UserDetail) {
        user_fragment_collapsing_toolbar.title = user.name
        user_fragment_content_fullname.text = user.name
        user_fragment_content_username.text = user.login
        user_fragment_content_bio.text = user.bio?.toString()
        user_fragment_content_mail.text = user.email.toString()
        user_fragment_content_location.text = user.location
        user_fragment_content_company.text = user.company
        user_fragment_content_blog.text = user.blog
    }
}
