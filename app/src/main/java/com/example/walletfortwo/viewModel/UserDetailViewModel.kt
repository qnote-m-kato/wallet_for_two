package com.example.walletfortwo.viewModel

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.walletfortwo.model.UserDetail
import com.example.walletfortwo.model.repository.UserDetailRepository

class UserDetailViewModel(application: Application, userName: String, resources: Resources) : AndroidViewModel(application) {
    private val name = userName
    private val res = resources
    private val userDetail: MutableLiveData<UserDetail> = MutableLiveData()

    init {
        userDetail.postValue(UserDetailRepository.getUserDetail(name, res))
    }

    fun getUserDetail(): LiveData<UserDetail> = userDetail
}