package ge.bobokvadze.usersapp

import ge.bobokvadze.usersapp.model.UsersData
import retrofit2.Response
import retrofit2.http.GET

interface UsersService {

    @GET("users?page=2")
    suspend fun users(): Response<UsersData>
}
