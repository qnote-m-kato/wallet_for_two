package com.example.walletfortwo.model.database.dao

import androidx.room.*
import com.example.walletfortwo.model.GiveCost

@Dao
interface GiveCostDao {
    @Query("SELECT * FROM GiveCost")
    fun get(): GiveCost

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(giveCost: GiveCost)

    @Update
    fun updateUser(giveCost: GiveCost)

    @Delete
    fun delete(giveCost: GiveCost)
}