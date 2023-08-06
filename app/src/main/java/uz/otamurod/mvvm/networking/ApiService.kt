package uz.otamurod.mvvm.networking

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import uz.otamurod.mvvm.models.UserData

interface ApiService {
    @GET("users")
    fun getAllUsers(): Flow<List<UserData>>
}