package com.example.walletfortwo.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.walletfortwo.model.LifeCost
import com.example.walletfortwo.model.repository.LifeCostRepository
import kotlinx.coroutines.launch
import java.time.Year

class LifeCostViewModel(application: Application) : AndroidViewModel(application) {
    private val app: Application = application
    private val lifeCosts: MutableList<LifeCost> = mutableListOf()
    private val changeFlag: MutableLiveData<Boolean> = MutableLiveData()

    init {
        viewModelScope.launch {
            lifeCosts.clear()
            lifeCosts.addAll(LifeCostRepository.getAll(app))
            changeFlag.postValue(true)
        }
    }

    fun delete(lifeCost: LifeCost) {
        viewModelScope.launch {
            LifeCostRepository.delete(app, lifeCost)
            lifeCosts.remove(lifeCost)
            changeFlag.postValue(true)
        }
    }

    fun add(lifeCost: LifeCost) {
        viewModelScope.launch {
            LifeCostRepository.insert(app, lifeCost)
            lifeCosts.add(0, lifeCost)
            changeFlag.postValue(true)
        }
    }

    fun getList(): List<LifeCost> = lifeCosts

    fun getFlag(): LiveData<Boolean> = changeFlag

    fun searchDate(date: String): List<LifeCost> {
        return if (date == "全期間") {
            lifeCosts
        } else {
            val newList = mutableListOf<LifeCost>()
            lifeCosts.forEach {
                if (it.date.startsWith(date)) {
                    newList.add(it)
                }
            }
            newList
        }
    }
}