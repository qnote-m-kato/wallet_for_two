package com.example.walletfortwo.model.repository

import android.app.Application
import android.content.res.Resources
import com.example.walletfortwo.R
import com.example.walletfortwo.model.GiveCost
import com.example.walletfortwo.model.LifeCost
import com.example.walletfortwo.model.User
import com.example.walletfortwo.model.UserDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object UserDetailRepository {
    private val userDetails: MutableList<UserDetail> = mutableListOf()

    suspend fun getUserDetails(app: Application, users: List<User>): List<UserDetail> {
        return withContext(Dispatchers.IO) {
            val returnList: MutableList<UserDetail> = mutableListOf()
            users.forEach { user ->
                val lifeCosts = LifeCostRepository.getListWhereName(app, user.name)
                val giveFromCosts = GiveCostRepository.getListWhereFromName(app, user.name)
                val giveToCosts = GiveCostRepository.getListWhereToName(app, user.name)
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

                LifeCostRepository.clearNotReflectedList()
                GiveCostRepository.clearNotReflectedList()
                returnList.add(UserDetail(user, total, lifeCosts, giveFromCosts, giveToCosts))
            }

            return@withContext returnList
        }
    }

    fun updateUserDetails(_userDetails: List<UserDetail>): List<UserDetail> {
        val returnList: MutableList<UserDetail> = mutableListOf()
        val lifeAdds = LifeCostRepository.getAddList()
        val lifeRemoves = LifeCostRepository.getRemoveList()
        val giveAdds = GiveCostRepository.getAddList()
        val giveRemoves = GiveCostRepository.getRemoveList()

        _userDetails.forEach { user ->
            lifeAdds.forEach {
                if (user.user.name == it.userName) {
                    user.totalCost += it.cost
                }
            }
            lifeRemoves.forEach {
                if (user.user.name == it.userName) {
                    user.totalCost -= it.cost
                }
            }

            giveAdds.forEach {
                if (user.user.name == it.fromUserName) {
                    user.totalCost += it.cost
                } else {
                    user.totalCost -= it.cost
                }
            }
            giveRemoves.forEach {
                if (user.user.name == it.fromUserName) {
                    user.totalCost -= it.cost
                } else {
                    user.totalCost += it.cost
                }
            }
            returnList.add(user)
        }

        GiveCostRepository.clearNotReflectedList()
        LifeCostRepository.clearNotReflectedList()

        return returnList
    }

    fun setUserDetails(_userDetails: List<UserDetail>) {
        userDetails.clear()
        userDetails.addAll(_userDetails)
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
        val life = mutableListOf<LifeCost>(LifeCost(0, "empty", "Empty", color, "Empty", 0, 0, ""))
        val give = mutableListOf<GiveCost>(GiveCost(0, "empty", "Empty", color, "Empty", color, "Empty", 0, 0, ""))
        return UserDetail(User("Empty", 0), 0, life, give, give)
    }
}