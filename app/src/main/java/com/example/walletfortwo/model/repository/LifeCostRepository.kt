package com.example.walletfortwo.model.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.walletfortwo.model.GiveCost
import com.example.walletfortwo.model.LifeCost
import com.example.walletfortwo.model.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object LifeCostRepository {
    private val add: MutableLiveData<LifeCost> = MutableLiveData()
    private val remove: MutableLiveData<LifeCost> = MutableLiveData()
    private val addList: MutableList<LifeCost> = mutableListOf()
    private val removeList: MutableList<LifeCost> = mutableListOf()

    suspend fun getAll(app: Application): List<LifeCost> {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            return@withContext db.LifeCostDao().getAll()
        }
    }

    suspend fun getListWhereName(app: Application, id: Int): List<LifeCost> {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            return@withContext db.LifeCostDao().getListWhereName(id)
        }
    }

    suspend fun insert(app: Application, lifeCost: LifeCost) {
        withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            db.LifeCostDao().insert(lifeCost)
            add.postValue(lifeCost)
            addList.add(lifeCost)
        }
    }

    suspend fun delete(app: Application, lifeCost: LifeCost) {
        withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            db.LifeCostDao().delete(lifeCost)
            remove.postValue(lifeCost)
            removeList.add(lifeCost)
        }
    }

    fun getAdd(): LiveData<LifeCost> = add
    fun getRemove(): LiveData<LifeCost> = remove
}