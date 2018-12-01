package comcesar1287.github.githubapp.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import comcesar1287.github.githubapp.R
import comcesar1287.github.githubapp.api.APIUtils
import comcesar1287.github.githubapp.api.callbacks.CallbackUser
import comcesar1287.github.githubapp.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        APIUtils.getGithubV3Api().create(CallbackUser::class.java).getAllUsers().enqueue(object : Callback<List<User>>{
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.i("teste", "teste")
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                Log.i("teste", response.body().toString())
            }
        })
    }
}
