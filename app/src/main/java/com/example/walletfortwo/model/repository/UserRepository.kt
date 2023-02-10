package com.example.walletfortwo.model.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.walletfortwo.model.User
import com.example.walletfortwo.model.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object UserRepository {
    private val edit: MutableLiveData<User> = MutableLiveData()
    private val users: MutableList<User> = mutableListOf()

    suspend fun getUser(app: Application, name: String): User {
       return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            db.UserDao().get(name)
        }
    }

    suspend fun initUserList(app: Application) {
        withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            db.UserDao().getAll()
            users.addAll(db.UserDao().getAll())
        }
    }

    suspend fun updateUser(app: Application, user: User) {
        withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(app.applicationContext)
            db.UserDao().insert(user)
            edit.postValue(user)
        }
    }

    fun getUserResource(id: Int): Int {
        users.forEach {
            if (it.id == id) return it.color
        }
        return 0
    }

    fun getUserName(id: Int): String {
        users.forEach {
            if (it.id == id) return it.name
        }
        return ""
    }

    fun getUsers(): List<User> = users

    fun getEdit(): LiveData<User> = edit
}