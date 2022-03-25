package com.example.githubuserapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun addFavoriteUser(userEntity: UserEntity)

    @Query("DELETE FROM user WHERE user.id = :id")
    suspend fun deleteFavoriteUser(id: Int): Int

    @Query("SELECT count(*) FROM user WHERE user.id = :id")
    suspend fun isFavoriteUser(id: Int): Int

    @Query("SELECT * FROM user ORDER BY id DESC")
    fun getFavoriteUser(): LiveData<List<UserEntity>>
}