package comcesar1287.github.githubapp.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import comcesar1287.github.githubapp.api.APIUtils
import comcesar1287.github.githubapp.api.callbacks.CallbackUser
import comcesar1287.github.githubapp.models.User
import comcesar1287.github.githubapp.models.UserDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    fun loadAllUsers(): LiveData<List<User>> {
        val mAllUsers: MutableLiveData<List<User>> = MutableLiveData()

        APIUtils.getGithubV3Api().create(CallbackUser::class.java).getAllUsers().enqueue(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                mAllUsers.value = null
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val usersList = response.body()

                    usersList?.let { list ->
                        mAllUsers.value = list
                    }
                }
            }
        })

        return mAllUsers
    }

    fun loadUserDetails(login: String): LiveData<UserDetail> {
        val mUserDetail: MutableLiveData<UserDetail> = MutableLiveData()

        APIUtils.getGithubV3Api().create(CallbackUser::class.java).getUser(login).enqueue(object : Callback<UserDetail> {
            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                mUserDetail.value = null
            }

            override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                if (response.isSuccessful) {
                    val user = response.body()

                    user?.let { userNonNull ->
                        mUserDetail.value = userNonNull
                    }
                }
            }
        })

        return mUserDetail
    }
}