package com.example.walletfortwo.model.repository

import android.app.Application
import com.example.walletfortwo.model.GiveCost
import com.example.walletfortwo.model.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GiveCostRepository {
    private val addList: MutableList<GiveCost> = mutableListOf()
    private val remoteList: MutableList<GiveCost> = mutableListOf()

    suspend fun getAll(app: Application): List<GiveCost> {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            return@withContext db.GiveCostDao().getAll()
        }
    }

    suspend fun getListWhereFromName(app: Application, name: String): List<GiveCost> {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            return@withContext db.GiveCostDao().getListWhereFromName(name)
        }
    }

    suspend fun getListWhereToName(app: Application, name: String): List<GiveCost> {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            return@withContext db.GiveCostDao().getListWhereTomName(name)
        }
    }

    suspend fun insert(app: Application, giveCost: GiveCost) {
        withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            db.GiveCostDao().insert(giveCost)
            addList.add(giveCost)
        }
    }

    suspend fun delete(app: Application, giveCost: GiveCost) {
        withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            db.GiveCostDao().delete(giveCost)
            remoteList.add(giveCost)
        }
    }

    fun getAddList(): List<GiveCost> = addList
    fun getRemoveList(): List<GiveCost> = remoteList

    fun clearNotReflectedList() {
        addList.clear()
        remoteList.clear()
    }
}