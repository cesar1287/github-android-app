package comcesar1287.github.githubapp.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import comcesar1287.github.githubapp.R
import comcesar1287.github.githubapp.api.APIUtils
import comcesar1287.github.githubapp.api.callbacks.CallbackUser
import comcesar1287.github.githubapp.models.UserDetail
import comcesar1287.github.githubapp.utils.GlideApp
import kotlinx.android.synthetic.main.activity_user_details.*
import kotlinx.android.synthetic.main.user_fragment_content.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        APIUtils.getGithubV3Api().create(CallbackUser::class.java).getUser(login).enqueue(object : Callback<UserDetail> {
            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                //TODO
            }

            override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                val user = response.body()

                user?.let { userNonNull ->
                    updateUI(userNonNull)
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
