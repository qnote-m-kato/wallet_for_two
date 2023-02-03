package com.example.walletfortwo.model.database.dao

import androidx.room.*
import com.example.walletfortwo.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getAll(): List<User>

    @Query("SELECT * FROM User WHERE name = :name")
    fun get(name: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun delete(user: User)
}