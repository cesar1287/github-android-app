package comcesar1287.github.githubapp.api.callbacks

import comcesar1287.github.githubapp.models.User
import retrofit2.Call
import retrofit2.http.GET

interface CallbackUser {

    @GET("users")
    fun getAllUsers(): Call<List<User>>
}