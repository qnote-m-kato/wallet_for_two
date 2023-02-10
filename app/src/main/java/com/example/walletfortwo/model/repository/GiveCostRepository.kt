package com.example.walletfortwo.model.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.walletfortwo.model.GiveCost
import com.example.walletfortwo.model.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GiveCostRepository {
    private val add: MutableLiveData<GiveCost> = MutableLiveData()
    private val remove: MutableLiveData<GiveCost> = MutableLiveData()
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
            add.postValue(giveCost)
            addList.add(giveCost)
        }
    }

    suspend fun delete(app: Application, giveCost: GiveCost) {
        withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            db.GiveCostDao().delete(giveCost)
            remove.postValue(giveCost)
            remoteList.add(giveCost)
        }
    }

    fun getAdd(): LiveData<GiveCost> = add
    fun getRemove(): LiveData<GiveCost> = remove

    fun getAddList(): List<GiveCost> = addList
    fun getRemoveList(): List<GiveCost> = remoteList

    fun clearNotReflectedList() {
        addList.clear()
        remoteList.clear()
    }
}