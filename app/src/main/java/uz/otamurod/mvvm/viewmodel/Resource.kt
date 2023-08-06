package uz.otamurod.mvvm.viewmodel

import uz.otamurod.mvvm.database.entity.UserEntity

sealed class Resource<T> {
    class Loading<T> : Resource<T>()
    class Success<T : Any>(val data: List<UserEntity>) : Resource<T>()
    class Error<T : Any>(val e: Throwable) : Resource<T>()
}