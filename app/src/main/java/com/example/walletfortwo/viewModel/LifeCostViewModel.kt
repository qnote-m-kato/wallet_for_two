package com.example.walletfortwo.viewModel

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.walletfortwo.R
import com.example.walletfortwo.model.LifeCost
import com.example.walletfortwo.model.repository.ExpenditureItemRepository
import com.example.walletfortwo.model.repository.LifeCostRepository
import kotlinx.coroutines.launch

class LifeCostViewModel(application: Application) : AndroidViewModel(application) {
    private val app: Application = application
    private val lifeCosts: MutableList<LifeCost> = mutableListOf()
    private val filterDate: MutableList<LifeCost> = mutableListOf()
    private val filterItem: MutableList<LifeCost> = mutableListOf()

    private val changeFlag: MutableLiveData<Boolean> = MutableLiveData()

    init {
        viewModelScope.launch {
            lifeCosts.clear()
            lifeCosts.addAll(LifeCostRepository.getAll(app))
            filterDate.addAll(lifeCosts)
            filterItem.addAll(lifeCosts)
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

    fun filterDate(date: String): List<LifeCost> {
        filterDate.clear()
        if (date == "全期間") {
            filterDate.addAll(LifeCostRepository.getCurrentList())
        } else {

            filterDate.addAll(LifeCostRepository.filter(true, mutableListOf(date)))
        }

        return LifeCostRepository.filter(filterDate, filterItem)
    }

    fun filterItem(list: List<Boolean>, res: Resources): List<LifeCost> {
        filterItem.clear()
        if (list.all { it }) {
            filterItem.addAll(LifeCostRepository.getCurrentList())
        } else {
            val items = ExpenditureItemRepository.filterItem(list, res)
            if (items.isNotEmpty()) {
                filterItem.addAll(LifeCostRepository.filter(false, items))
            }
        }

        return LifeCostRepository.filter(filterDate, filterItem)
    }

}