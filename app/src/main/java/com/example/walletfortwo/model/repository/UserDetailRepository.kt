package com.example.walletfortwo.model.repository

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.walletfortwo.R
import com.example.walletfortwo.model.GiveCost
import com.example.walletfortwo.model.LifeCost
import com.example.walletfortwo.model.User
import com.example.walletfortwo.model.UserDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object UserDetailRepository {
    private val userDetails: MutableList<UserDetail> = mutableListOf()
    private val update: MutableLiveData<Boolean> = MutableLiveData()

    suspend fun initUserDetail(app: Application) {
        withContext(Dispatchers.IO) {
            userDetails.clear()
            val users = UserRepository.getUsers()
            users.forEach { user ->
                val lifeCosts = LifeCostRepository.getListWhereName(app, user.id)
                val giveFromCosts = GiveCostRepository.getListWhereFromName(app, user.id)
                val giveToCosts = GiveCostRepository.getListWhereToName(app, user.id)
                var total = 0

                lifeCosts.forEach {
                    total += it.cost
                }

                giveFromCosts.forEach {
                    total += it.cost
                }

                giveToCosts.forEach {
                    total -= it.cost
                }
                userDetails.add(UserDetail(user, total,
                    lifeCosts.toMutableList(), giveFromCosts.toMutableList(), giveToCosts.toMutableList()))
            }
            update.postValue(true)
        }
    }

    fun updateUserDetailUser( user: User) {
        userDetails.forEach {
            if (it.user.id == user.id) {
                it.user.name = user.name
                it.user.color = user.color
                update.postValue(true)
                return
            }
        }
    }

    fun updateUserDetailLifeCost(isAdd: Boolean, lifeCost: LifeCost) {
        userDetails.forEach {
            if (it.user.id == lifeCost.userid) {
                if (isAdd) {
                    it.lifeCosts.add(lifeCost)
                    it.totalCost += lifeCost.cost
                } else {
                    it.lifeCosts.remove(lifeCost)
                    it.totalCost -= lifeCost.cost
                }
                update.postValue(true)
                return
            }
        }
    }

    fun updateUserDetailGiveCost(isAdd: Boolean, giveCost: GiveCost) {
        userDetails.forEach {
            if (it.user.id == giveCost.fromUserId) {
                if (isAdd) {
                    it.giveCostsFrom.add(giveCost)
                    it.totalCost += giveCost.cost
                } else {
                    it.giveCostsFrom.remove(giveCost)
                    it.totalCost -= giveCost.cost
                }
                update.postValue(true)
                return
            } else {
                if (isAdd) {
                    it.giveCostsTo.add(giveCost)
                    it.totalCost -= giveCost.cost
                } else {
                    it.giveCostsTo.remove(giveCost)
                    it.totalCost += giveCost.cost
                }
                update.postValue(true)
                return
            }
        }
    }

    fun getUserDetail(name: String, resources: Resources): UserDetail {
        if (userDetails.isNotEmpty()) {
            userDetails.forEach {
                if (it.user.name == name) {
                    return it
                }
            }
        }
        val color = resources.getColor(R.color.black)
        val life = mutableListOf<LifeCost>(LifeCost(0, "empty", 100,  "Empty", 0, 0, ""))
        val give = mutableListOf<GiveCost>(GiveCost(0, "empty", 200, 100, "Empty", 0, 0, ""))
        return UserDetail(User(100,"Empty", 0), 0, life, give, give)
    }

    fun getUserDetails(): List<UserDetail> = userDetails
    fun getUpdate(): LiveData<Boolean> = update
}