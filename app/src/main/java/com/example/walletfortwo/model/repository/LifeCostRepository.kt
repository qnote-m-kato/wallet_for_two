package com.example.walletfortwo.model.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.walletfortwo.model.LifeCost
import com.example.walletfortwo.model.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object LifeCostRepository {
    private val add: MutableLiveData<LifeCost> = MutableLiveData()
    private val remove: MutableLiveData<LifeCost> = MutableLiveData()
    private val currentList: MutableList<LifeCost> = mutableListOf()

    suspend fun getAll(app: Application): List<LifeCost> {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            currentList.clear()
            currentList.addAll(db.LifeCostDao().getAll())
            return@withContext currentList
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
            currentList.add(lifeCost)
        }
    }

    suspend fun delete(app: Application, lifeCost: LifeCost) {
        withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            db.LifeCostDao().delete(lifeCost)
            remove.postValue(lifeCost)
            currentList.remove(lifeCost)
        }
    }

    fun filter(isFilterDate: Boolean, filter: List<String>): List<LifeCost> {
        val newList = mutableListOf<LifeCost>()
        currentList.forEach {
            kotlin.run loop@{
                filter.forEach { item ->
                    if (isFilterDate && it.date.startsWith(item)) {
                        newList.add(it)
                        return@loop
                    } else if (!isFilterDate && it.expenditureItem == item) {
                        newList.add(it)
                        return@loop
                    }
                }
            }
        }

        return newList
    }

    fun filter(date: List<LifeCost>, item: List<LifeCost>): List<LifeCost> {
        val newList = mutableListOf<LifeCost>()
        date.forEach {
            kotlin.run loop@{
                item.forEach { item ->
                   if (it.id == item.id) {
                       newList.add(item)
                       return@loop
                   }
                }
            }
        }
        return newList
    }

    fun getAdd(): LiveData<LifeCost> = add
    fun getRemove(): LiveData<LifeCost> = remove
    fun getCurrentList(): List<LifeCost> = currentList
}