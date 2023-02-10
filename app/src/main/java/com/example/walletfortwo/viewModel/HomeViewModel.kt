package com.example.walletfortwo.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.walletfortwo.model.GiveCost
import com.example.walletfortwo.model.LifeCost
import com.example.walletfortwo.model.User
import com.example.walletfortwo.model.UserDetail
import com.example.walletfortwo.model.repository.GiveCostRepository
import com.example.walletfortwo.model.repository.LifeCostRepository
import com.example.walletfortwo.model.repository.UserDetailRepository
import com.example.walletfortwo.model.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val app: Application = application
    private val userDetails: MutableLiveData<List<UserDetail>> = MutableLiveData()

    init {
        viewModelScope.launch {
            val users = UserRepository.getUserList(app)
            userDetails.postValue(UserDetailRepository.getUserDetails(app, users))
        }
    }

    fun updateUserDetails() {
        if (userDetails.value != null) {
            userDetails.postValue(userDetails.value?.let { UserDetailRepository.updateUserDetails(it) })
        }
    }

    fun setUserDetails() {
        if (userDetails.value != null) {
            UserDetailRepository.setUserDetails(userDetails.value!!)
        }
    }

    fun getUserDetails(): LiveData<List<UserDetail>> = userDetails
}