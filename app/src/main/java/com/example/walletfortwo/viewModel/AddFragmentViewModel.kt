package com.example.walletfortwo.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.walletfortwo.model.ExpenditureItem
import com.example.walletfortwo.model.User
import com.example.walletfortwo.model.repository.ExpenditureItemRepository
import com.example.walletfortwo.model.repository.UserRepository
import kotlinx.coroutines.launch
import kotlin.math.cos

class AddFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val app: Application = application
    private val userList: MutableList<User> = mutableListOf()
    private val userNameList: ArrayList<String> = arrayListOf()
    private val expenditureItemList: MutableList<ExpenditureItem> = mutableListOf()
    val dateValidation = MutableLiveData(false)
    val userValidation = MutableLiveData(false)
    val costValidation = MutableLiveData(false)
    val expenditureItemValidation = MutableLiveData(false)
    val addValidations = MediatorLiveData<Boolean>().apply {
        addSource(dateValidation) {postValue(isEnable())}
        addSource(userValidation) {postValue(isEnable())}
        addSource(costValidation) {postValue(isEnable())}
        addSource(expenditureItemValidation) {postValue(isEnable())}
    }

    init {
        viewModelScope.launch {
            userList.addAll(UserRepository.getUserList(app))
            userList.forEach {
                userNameList.add(it.name)
            }
            expenditureItemList.addAll(ExpenditureItemRepository.getAll(app))
        }
    }

    fun getUser(name: String): User? {
        userList.forEach {
            if (it.name == name) {
                return it
            }
        }
        return null
    }

    fun getToUser(name: String): User? {
        userList.forEach {
            if (it.name != name) {
                return it
            }
        }
        return null
    }

    fun getUserNames(): ArrayList<String> = userNameList

    fun getExpenditureItems(): List<ExpenditureItem> = expenditureItemList

    private fun isEnable(): Boolean {
        return dateValidation.value ?: false &&
                userValidation.value ?: false &&
                costValidation.value ?: false &&
                expenditureItemValidation.value ?: false
    }
}