package uz.otamurod.mvvm.database.dao

import androidx.room.*
import uz.otamurod.mvvm.database.entity.UserEntity

@Dao
interface UserDao {
    @Insert
    fun addUser(userEntity: UserEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUsers(list: List<UserEntity>)

    @Query("SELECT * FROM UserEntity")
    fun getAllUsers(): List<UserEntity>

    @Query("SELECT * FROM UserEntity where id=:id")
    fun getUserById(id: Int): UserEntity

    @Update
    fun updateUser(userEntity: UserEntity)

    @Delete
    fun deleteUser(userEntity: UserEntity)
}