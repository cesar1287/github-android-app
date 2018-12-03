package comcesar1287.github.githubapp.utils

import comcesar1287.github.githubapp.api.APIError
import comcesar1287.github.githubapp.api.ApiService
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException


object ErrorUtils {

    fun parseError(response: Response<*>): APIError {
        val converter: Converter<ResponseBody, APIError> = ApiService.getClient(BASE_URL_GITHUB_V3)
            .responseBodyConverter(APIError::class.java, arrayOfNulls<Annotation>(0))

        val error: APIError

        try {
            error = converter.convert(response.errorBody())!!
        } catch (e: IOException) {
            return APIError()
        }

        return error
    }
}