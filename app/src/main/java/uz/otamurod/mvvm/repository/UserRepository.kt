package uz.otamurod.mvvm.repository

import uz.otamurod.mvvm.database.dao.UserDao
import uz.otamurod.mvvm.database.entity.UserEntity
import uz.otamurod.mvvm.networking.ApiService

class UserRepository(private val apiService: ApiService, private val userDao: UserDao) {
    fun getUsersFromApi() = apiService.getAllUsers()
    fun addUsersToDatabase(list: List<UserEntity>) = userDao.addUsers(list)
    fun getUsersFromDatabase() = userDao.getAllUsers()
}