package com.example.walletfortwo.model.database.dao

import androidx.room.*
import com.example.walletfortwo.model.GiveCost

@Dao
interface GiveCostDao {
    @Query("SELECT * FROM GiveCost")
    fun getAll(): List<GiveCost>

    @Query("SELECT * FROM GiveCost WHERE fromUserName = :name")
    fun getListWhereFromName(name: String): List<GiveCost>

    @Query("SELECT * FROM GiveCost WHERE toUserName = :name")
    fun getListWhereTomName(name: String): List<GiveCost>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(giveCost: GiveCost)

    @Update
    fun updateUser(giveCost: GiveCost)

    @Delete
    fun delete(giveCost: GiveCost)
}