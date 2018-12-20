package comcesar1287.github.githubapp.api

import comcesar1287.github.githubapp.utils.BASE_URL_GITHUB_V3
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {

    companion object {
        fun getGithubV3ApiClient() : Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL_GITHUB_V3)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}