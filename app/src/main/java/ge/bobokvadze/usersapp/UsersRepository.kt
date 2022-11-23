package ge.bobokvadze.usersapp

import javax.inject.Inject

interface UsersRepository {

    suspend fun users(): UsersState

    class Base @Inject constructor(
        private val usersService: UsersService
    ) : UsersRepository {
        override suspend fun users(): UsersState = try {
            val service = usersService.users()
            val body = service.body()?.data
            UsersState.SuccessUsers(body)
        } catch (e: Exception) {
            UsersState.ErrorUsers(e.message.toString())
        }
    }
}
