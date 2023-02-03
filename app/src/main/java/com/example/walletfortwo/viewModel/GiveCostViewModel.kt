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
    private val addFlag: MutableLiveData<Boolean> = MutableLiveData()

    init {
        viewModelScope.launch {
            giveCosts.clear()
            giveCosts.addAll(GiveCostRepository.getAll(app))
            addFlag.postValue(true)
        }
    }

    fun add(giveCost: GiveCost) {
        viewModelScope.launch {
            GiveCostRepository.insert(app, giveCost)
            giveCosts.add(giveCost)
            addFlag.postValue(true)
        }
    }

    fun getList(): List<GiveCost> = giveCosts

    fun getFlag(): LiveData<Boolean> = addFlag
}