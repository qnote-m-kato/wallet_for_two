package com.example.walletfortwo.model.repository

import android.app.Application
import com.example.walletfortwo.model.GiveCost
import com.example.walletfortwo.model.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GiveCostRepository {

    suspend fun getAll(app: Application): List<GiveCost> {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            return@withContext db.GiveCostDao().getAll()
        }
    }

    suspend fun getListWhereName(app: Application, name: String): List<GiveCost> {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            return@withContext db.GiveCostDao().getListWhereName(name)
        }
    }

    suspend fun insert(app: Application, giveCost: GiveCost) {
        withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            db.GiveCostDao().insert(giveCost)
        }
    }
}