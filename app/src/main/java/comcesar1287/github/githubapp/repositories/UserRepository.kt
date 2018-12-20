package comcesar1287.github.githubapp.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import comcesar1287.github.githubapp.api.ApiService
import comcesar1287.github.githubapp.api.callbacks.CallbackUser
import comcesar1287.github.githubapp.models.User
import comcesar1287.github.githubapp.models.UserDetail
import comcesar1287.github.githubapp.models.UserRepo
import comcesar1287.github.githubapp.utils.ERROR_DEFAULT
import comcesar1287.github.githubapp.utils.ErrorUtils
import comcesar1287.github.githubapp.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    fun loadAllUsers(): LiveData<Resource<List<User>>> {
        val mAllUsers: MutableLiveData<Resource<List<User>>> = MutableLiveData()
        mAllUsers.value = Resource.loading(null)

        ApiService.getGithubV3ApiClient().create(CallbackUser::class.java).getAllUsers().enqueue(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                mAllUsers.value = Resource.error(t.localizedMessage, null)
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val usersList = response.body()
                    mAllUsers.value = Resource.success(usersList)
                } else {
                    val error = ErrorUtils.parseError(response)

                    error?.message?.let {  message ->
                        mAllUsers.value = Resource.error(message, null)
                    } ?: run {
                        mAllUsers.value = Resource.error(ERROR_DEFAULT, null)
                    }
                }
            }
        })

        return mAllUsers
    }

    fun loadUserDetails(login: String): LiveData<Resource<UserDetail>> {
        val mUserDetail: MutableLiveData<Resource<UserDetail>> = MutableLiveData()
        mUserDetail.value = Resource.loading(null)

        ApiService.getGithubV3ApiClient().create(CallbackUser::class.java).getUser(login).enqueue(object : Callback<UserDetail> {
            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                mUserDetail.value = Resource.error(t.localizedMessage, null)
            }

            override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    mUserDetail.value = Resource.success(user)
                } else {
                    val error = ErrorUtils.parseError(response)

                    error?.message?.let {  message ->
                        mUserDetail.value = Resource.error(message, null)
                    } ?: run {
                        mUserDetail.value = Resource.error(ERROR_DEFAULT, null)
                    }
                }
            }
        })

        return mUserDetail
    }

    fun loadUserRepos(login: String): LiveData<Resource<List<UserRepo>>> {
        val mUserRepos: MutableLiveData<Resource<List<UserRepo>>> = MutableLiveData()
        mUserRepos.value = Resource.loading(null)

        ApiService.getGithubV3ApiClient().create(CallbackUser::class.java).getRepos(login).enqueue(object : Callback<List<UserRepo>> {
            override fun onFailure(call: Call<List<UserRepo>>, t: Throwable) {
                mUserRepos.value = Resource.error(t.localizedMessage, null)
            }

            override fun onResponse(call: Call<List<UserRepo>>, response: Response<List<UserRepo>>) {
                if (response.isSuccessful) {
                    val reposList = response.body()
                    mUserRepos.value = Resource.success(reposList)
                } else {
                    val error = ErrorUtils.parseError(response)

                    error?.message?.let {  message ->
                        mUserRepos.value = Resource.error(message, null)
                    } ?: run {
                        mUserRepos.value = Resource.error(ERROR_DEFAULT, null)
                    }
                }
            }
        })

        return mUserRepos
    }
}