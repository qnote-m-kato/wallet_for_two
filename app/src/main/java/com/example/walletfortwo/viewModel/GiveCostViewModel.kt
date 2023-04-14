package com.example.walletfortwo.viewModel

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.walletfortwo.model.GiveCost
import com.example.walletfortwo.model.LifeCost
import com.example.walletfortwo.model.repository.ExpenditureItemRepository
import com.example.walletfortwo.model.repository.GiveCostRepository
import com.example.walletfortwo.model.repository.LifeCostRepository
import kotlinx.coroutines.launch

class GiveCostViewModel(application: Application) : AndroidViewModel(application) {
    private val app: Application = application
    private val giveCosts: MutableList<GiveCost> = mutableListOf()
    private val filterDate: MutableList<GiveCost> = mutableListOf()
    private val filterItem: MutableList<GiveCost> = mutableListOf()
    private val changeFlag: MutableLiveData<Boolean> = MutableLiveData()

    init {
        viewModelScope.launch {
            giveCosts.clear()
            giveCosts.addAll(GiveCostRepository.getAll(app))
            changeFlag.postValue(true)
        }
    }

    fun add(giveCost: GiveCost) {
        viewModelScope.launch {
            GiveCostRepository.insert(app, giveCost)
            giveCosts.add(0, giveCost)
            changeFlag.postValue(true)
        }
    }

    fun delete(giveCost: GiveCost) {
        viewModelScope.launch {
            GiveCostRepository.delete(app, giveCost)
            giveCosts.remove(giveCost)
            changeFlag.postValue(true)
        }
    }

    fun filterDate(date: String): List<GiveCost> {
        filterDate.clear()
        if (date == "全期間") {
            filterDate.addAll(GiveCostRepository.getCurrentList())
        } else {
            filterDate.addAll(GiveCostRepository.filter(true, mutableListOf(date)))
        }

        return GiveCostRepository.filter(filterDate, filterItem)
    }

    fun filterItem(list: List<Boolean>, res: Resources): List<GiveCost> {
        filterItem.clear()
        if (list.all { it }) {
            filterItem.addAll(GiveCostRepository.getCurrentList())
        } else {
            val items = ExpenditureItemRepository.filterItem(list, res)
            if (items.isNotEmpty()) {
                filterItem.addAll(GiveCostRepository.filter(false, items))
            }
        }

        return GiveCostRepository.filter(filterDate, filterItem)
    }

    fun getList(): List<GiveCost> = giveCosts

    fun getFlag(): LiveData<Boolean> = changeFlag
}