package com.example.walletfortwo.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.walletfortwo.model.User
import com.example.walletfortwo.model.repository.UserRepository
import kotlinx.coroutines.launch
import kotlin.math.cos

class AddFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val app: Application = application
    private val userList: MutableList<User> = mutableListOf()
    private val userNameList: ArrayList<String> = arrayListOf()
    val dateValidation = MutableLiveData(false)
    val userValidation = MutableLiveData(false)
    val costValidation = MutableLiveData(false)
    val addValidations = MediatorLiveData<Boolean>().apply {
        addSource(dateValidation) {postValue(isEnable())}
        addSource(userValidation) {postValue(isEnable())}
        addSource(costValidation) {postValue(isEnable())}
    }

    init {
        viewModelScope.launch {
            userList.addAll(UserRepository.getUserList(app))
            userList.forEach {
                userNameList.add(it.name)
            }
        }
    }

    fun getUserResource(name: String): Int {
        userList.forEach {
            if (it.name == name) {
                return it.color
            }
        }
        return 0
    }

    fun getUsers(): List<User> = userList

    fun getUserNames(): ArrayList<String> = userNameList


    private fun isEnable(): Boolean {
        return dateValidation.value ?: false &&
                userValidation.value ?: false &&
                costValidation.value ?: false
    }
}