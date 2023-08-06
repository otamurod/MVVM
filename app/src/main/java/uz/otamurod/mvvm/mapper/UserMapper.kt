package uz.otamurod.mvvm.mapper

import uz.otamurod.mvvm.database.entity.UserEntity
import uz.otamurod.mvvm.models.UserData

fun UserData.mapToUserEntity(userData: UserData): UserEntity {
    return UserEntity(userData.id ?: 0, userData.login ?: "", userData.avatarUrl ?: "")
}