package com.yawyan.githubuser.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yawyan.githubuser.data.response.ItemsItem


@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favuser: FavoriteUser)

    @Query("DELETE from favoriteuser where favoriteuser.id = :id")
    suspend fun deleteFavorite(id: Int): Int

    @Query("SELECT * from favoriteuser")
    fun getAllFavorite(): LiveData<List<ItemsItem>>

    @Query("SELECT count(*) from favoriteuser where favoriteuser.id = :id ")
    suspend fun checkFavorite(id: Int): Int
}