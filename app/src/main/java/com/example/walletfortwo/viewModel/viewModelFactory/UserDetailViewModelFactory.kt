package com.example.walletfortwo.viewModel.viewModelFactory

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.walletfortwo.viewModel.UserDetailViewModel

class UserDetailViewModelFactory(private val application: Application, private val userName: String, private val resources: Resources): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = UserDetailViewModel(application, userName, resources) as T
}