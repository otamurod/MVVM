package uz.otamurod.mvvm.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey
    val id: Int,
    val login: String,
    val avatarUrl: String,
)
