package com.example.walletfortwo.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.walletfortwo.model.GiveCost
import com.example.walletfortwo.model.repository.GiveCostRepository
import kotlinx.coroutines.launch

class GiveCostViewModel(application: Application) : AndroidViewModel(application) {
    private val app: Application = application
    private val giveCosts: MutableList<GiveCost> = mutableListOf()
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
            giveCosts.add(giveCost)
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

    fun getList(): List<GiveCost> {
        giveCosts.reverse()
        return giveCosts
    }
    fun getFlag(): LiveData<Boolean> = changeFlag
}