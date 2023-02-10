package com.example.walletfortwo.model.database.dao

import androidx.room.*
import com.example.walletfortwo.model.LifeCost

@Dao
interface LifeCostDao {
    @Query("SELECT * FROM LifeCost")
    fun getAll(): List<LifeCost>

    @Query("SELECT * FROM LifeCost WHERE userId = :id")
    fun getListWhereName(id: Int): List<LifeCost>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(lifeCost: LifeCost)

    @Update
    fun updateUser(lifeCost: LifeCost)

    @Delete
    fun delete(lifeCost: LifeCost)
}