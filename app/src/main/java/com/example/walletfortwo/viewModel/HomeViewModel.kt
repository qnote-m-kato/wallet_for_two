package com.example.walletfortwo.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.walletfortwo.R
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
    val colorList: List<Int> = mutableListOf(R.color.user_red, R.color.user_blue, R.color.user_green,
        R.color.user_pink, R.color.user_light_blue, R.color.user_light_green,
        R.color.user_orange, R.color.user_purple, R.color.user_yellow)

    init {
        val a = UserDetailRepository.getUserDetails()
        userDetails.postValue(a)
    }

    fun update() {
        userDetails.postValue(UserDetailRepository.getUserDetails())
    }

    fun editUser(id: Int, name: String, color: Int, id2: Int, name2: String, color2: Int) {
        viewModelScope.launch {
            UserRepository.updateUser(app, User(id, name, color))
            UserRepository.updateUser(app, User(id2, name2, color2))
        }
    }

    fun getUserDetails(): LiveData<List<UserDetail>> = userDetails
}