package comcesar1287.github.githubapp.api

import comcesar1287.github.githubapp.utils.BASE_URL_GITHUB_V3
import retrofit2.Retrofit

class APIUtils {

    companion object {
        fun getGithubV3Api(): Retrofit {
            return ApiService.getClient(BASE_URL_GITHUB_V3)
        }
    }
}