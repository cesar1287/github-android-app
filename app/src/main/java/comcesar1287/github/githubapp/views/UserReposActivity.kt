package comcesar1287.github.githubapp.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import comcesar1287.github.githubapp.R
import comcesar1287.github.githubapp.adapters.ReposAdapter
import comcesar1287.github.githubapp.api.APIUtils
import comcesar1287.github.githubapp.api.callbacks.CallbackUser
import comcesar1287.github.githubapp.models.UserRepo
import kotlinx.android.synthetic.main.activity_user_repos.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserReposActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_repos)

        val login = intent.getStringExtra("login")

        APIUtils.getGithubV3Api().create(CallbackUser::class.java).getRepos(login).enqueue(object : Callback<List<UserRepo>> {
            override fun onFailure(call: Call<List<UserRepo>>, t: Throwable) {
                //TODO
            }

            override fun onResponse(call: Call<List<UserRepo>>, response: Response<List<UserRepo>>) {
                if (response.isSuccessful) {
                    val reposList = response.body()

                    reposList?.let { list ->
                        val layoutManager = LinearLayoutManager(this@UserReposActivity)
                        recyclerView.layoutManager = layoutManager
                        val usersAdapter = ReposAdapter(this@UserReposActivity, list)
                        recyclerView.adapter = usersAdapter
                    }
                }
            }
        })
    }
}
