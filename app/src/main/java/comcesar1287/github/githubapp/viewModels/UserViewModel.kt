package comcesar1287.github.githubapp.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import comcesar1287.github.githubapp.models.User
import comcesar1287.github.githubapp.models.UserDetail
import comcesar1287.github.githubapp.models.UserRepo
import comcesar1287.github.githubapp.repositories.UserRepository
import comcesar1287.github.githubapp.utils.Resource

class UserViewModel(application: Application): AndroidViewModel(application) {

    fun getAllUsers(): LiveData<Resource<List<User>>> {
        return UserRepository().loadAllUsers()
    }

    fun getUserDetails(login: String): LiveData<Resource<UserDetail>> {
        return UserRepository().loadUserDetails(login)
    }

    fun getUserRepos(login: String): LiveData<Resource<List<UserRepo>>> {
        return UserRepository().loadUserRepos(login)
    }
}