package comcesar1287.github.githubapp.models
import com.google.gson.annotations.SerializedName

data class UserRepo(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("language")
    val language: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("watchers")
    val watchers: Int
) {
    data class Owner(
        @SerializedName("login")
        val login: String
    )
}