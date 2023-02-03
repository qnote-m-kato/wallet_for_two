package com.example.walletfortwo.model.repository

import android.app.Application
import android.util.Log
import com.example.walletfortwo.model.User
import com.example.walletfortwo.model.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object UserRepository {

    suspend fun getUser(app: Application, name: String): User {
       return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            db.UserDao().get(name)
        }
    }

    suspend fun getUserList(app: Application): List<User> {
        return  withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            db.UserDao().getAll()
        }
    }

    suspend fun updateUser(app: Application, user: User) {
        withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            db.UserDao().insert(user)
        }
    }

}