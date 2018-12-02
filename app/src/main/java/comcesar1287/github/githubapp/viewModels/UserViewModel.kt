package comcesar1287.github.githubapp.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import comcesar1287.github.githubapp.models.User
import comcesar1287.github.githubapp.models.UserDetail
import comcesar1287.github.githubapp.repositories.UserRepository

class UserViewModel(application: Application): AndroidViewModel(application) {

    fun getAllUsers(): LiveData<List<User>> {
        return UserRepository().loadAllUsers()
    }

    fun getUserDetails(login: String): LiveData<UserDetail> {
        return UserRepository().loadUserDetails(login)
    }
}