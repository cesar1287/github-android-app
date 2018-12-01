package comcesar1287.github.githubapp.api.callbacks

import comcesar1287.github.githubapp.models.User
import comcesar1287.github.githubapp.models.UserDetail
import comcesar1287.github.githubapp.models.UserRepo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CallbackUser {

    @GET("users")
    fun getAllUsers(): Call<List<User>>

    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Call<UserDetail>

    @GET("users/{username}/repos")
    fun getRepos(@Path("username") username: String): Call<List<UserRepo>>
}