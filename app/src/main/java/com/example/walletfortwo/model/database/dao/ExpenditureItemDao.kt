package com.example.walletfortwo.model.database.dao

import androidx.room.*
import com.example.walletfortwo.model.ExpenditureItem

@Dao
interface ExpenditureItemDao {
    @Query("SELECT * FROM ExpenditureItem")
    fun getAll(): List<ExpenditureItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(expenditureItem: ExpenditureItem)

    @Delete
    fun delete(expenditureItem: ExpenditureItem)
}