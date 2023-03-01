package com.example.walletfortwo.model.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.walletfortwo.model.GiveCost
import com.example.walletfortwo.model.User
import com.example.walletfortwo.model.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GiveCostRepository {
    private val add: MutableLiveData<GiveCost> = MutableLiveData()
    private val remove: MutableLiveData<GiveCost> = MutableLiveData()

    suspend fun getAll(app: Application): List<GiveCost> {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            return@withContext db.GiveCostDao().getAll()
        }
    }

    suspend fun getListWhereFromName(app: Application, id: Int): List<GiveCost> {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            return@withContext db.GiveCostDao().getListWhereFromName(id)
        }
    }

    suspend fun getListWhereToName(app: Application, id: Int): List<GiveCost> {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            return@withContext db.GiveCostDao().getListWhereTomName(id)
        }
    }

    suspend fun insert(app: Application, giveCost: GiveCost) {
        withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            db.GiveCostDao().insert(giveCost)
            add.postValue(giveCost)
        }
    }

    suspend fun delete(app: Application, giveCost: GiveCost) {
        withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            db.GiveCostDao().delete(giveCost)
            remove.postValue(giveCost)
        }
    }

    fun getAdd(): LiveData<GiveCost> = add
    fun getRemove(): LiveData<GiveCost> = remove

}