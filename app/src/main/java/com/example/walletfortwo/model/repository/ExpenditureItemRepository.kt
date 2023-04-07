package com.example.walletfortwo.model.repository

import android.app.Application
import android.content.res.Resources
import com.example.walletfortwo.R
import com.example.walletfortwo.model.ExpenditureItem
import com.example.walletfortwo.model.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ExpenditureItemRepository {
    private val itemList = mutableListOf(R.string.label_rent, R.string.label_food, R.string.label_expense, R.string.label_electric, R.string.label_gas, R.string.label_water, R.string.label_wifi, R.string.label_other)
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

    fun filterItem(items: List<Boolean>, res: Resources): List<String> {
        val list = mutableListOf<String>()
        items.forEachIndexed { index, isChecked ->
            if (isChecked) {
                list.add(res.getString(itemList[index]))
            }
        }
        return list
    }
}