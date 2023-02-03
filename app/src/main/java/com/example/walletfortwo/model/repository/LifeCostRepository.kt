package com.example.walletfortwo.model.repository

import android.app.Application
import com.example.walletfortwo.model.LifeCost
import com.example.walletfortwo.model.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object LifeCostRepository {

    suspend fun getAll(app: Application): List<LifeCost> {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            return@withContext db.LifeCostDao().getAll()
        }
    }

    suspend fun getListWhereName(app: Application, name: String): List<LifeCost> {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            return@withContext db.LifeCostDao().getListWhereName(name)
        }
    }

    suspend fun insert(app: Application, lifeCost: LifeCost) {
        withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            db.LifeCostDao().insert(lifeCost)
        }
    }

}