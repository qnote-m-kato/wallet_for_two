package com.example.walletfortwo.model.repository

import android.app.Application
import android.content.res.Resources
import com.example.walletfortwo.R
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

    fun filterItem(items: List<Boolean>, res: Resources): List<String> {
        val list = mutableListOf<String>()
        items.forEachIndexed { index, b ->
            if (b) {
                when (index) {
                    0 -> list.add(res.getString(R.string.label_rent))
                    1 -> list.add(res.getString(R.string.label_food))
                    2 -> list.add(res.getString(R.string.label_expense))
                    3 -> list.add(res.getString(R.string.label_electric))
                    4 -> list.add(res.getString(R.string.label_gas))
                    5 -> list.add(res.getString(R.string.label_water))
                    6 -> list.add(res.getString(R.string.label_wifi))
                    else -> list.add(res.getString(R.string.label_other))
                }
            }
        }
        return list
    }
}