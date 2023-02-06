package com.example.walletfortwo.model.repository

import android.app.Application
import com.example.walletfortwo.model.ExpenditureItem
import com.example.walletfortwo.model.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ExpenditureItemRepository {
    suspend fun getAll(app: Application): List<ExpenditureItem> {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            return@withContext db.ExpenditureItemDao().getAll()
        }
    }

    suspend fun insert(app: Application, expenditureItem: ExpenditureItem) {
        withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            db.ExpenditureItemDao().insert(expenditureItem)
        }
    }
}