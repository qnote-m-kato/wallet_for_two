package com.example.walletfortwo.viewModel

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.walletfortwo.model.UserDetail
import com.example.walletfortwo.model.repository.UserDetailRepository

class UserDetailViewModel(application: Application, userName: String) : AndroidViewModel(application) {
    private val name = userName
    private val userDetail: MutableLiveData<UserDetail> = MutableLiveData()
    private var totalLife: Int = 0
    private var totalFrom: Int = 0
    private var totalTo: Int = 0

    init {
        val user = UserDetailRepository.getUserDetail(name)
        user.lifeCosts.forEach {
            totalLife += it.cost
        }
        user.giveCostsFrom.forEach {
            totalFrom += it.cost
        }
        user.giveCostsTo.forEach {
            totalTo += it.cost
        }
        userDetail.postValue(user)
    }

    fun getUserDetail(): LiveData<UserDetail> = userDetail

    fun getTotalLife(): Int = totalLife
    fun getTotalFrom(): Int = totalFrom
    fun getTotalTo(): Int = totalTo
}