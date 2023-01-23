package com.example.walletfortwo.model.database.dao

import androidx.room.*
import com.example.walletfortwo.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User LIMIT 1")
    fun get(): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Update
    fun updateUser(user: User)

    //TODO ログインかログアウトの時に削除する
    @Delete
    fun delete(user: User)
}