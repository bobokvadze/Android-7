package ge.bobokvadze.usersapp.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UsersData(
    @Json(name = "data")
    val data: List<Data?>?,
    @Json(name = "page")
    val page: Int?,
    @Json(name = "per_page")
    val perPage: Int?,
    @Json(name = "total")
    val total: Int?,
    @Json(name = "total_pages")
    val totalPages: Int?
)