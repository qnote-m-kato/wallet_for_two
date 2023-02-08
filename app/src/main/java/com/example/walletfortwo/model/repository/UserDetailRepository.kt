package com.example.walletfortwo.model.repository

import android.app.Application
import com.example.walletfortwo.model.User
import com.example.walletfortwo.model.UserDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object UserDetailRepository {

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

    fun updateUserDetails(userDetails: List<UserDetail>): List<UserDetail> {
        val returnList: MutableList<UserDetail> = mutableListOf()
        val lifeAdds = LifeCostRepository.getAddList()
        val lifeRemoves = LifeCostRepository.getRemoveList()
        val giveAdds = GiveCostRepository.getAddList()
        val giveRemoves = GiveCostRepository.getRemoveList()

        userDetails.forEach { user ->
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
}