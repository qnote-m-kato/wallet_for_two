package com.example.walletfortwo.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.walletfortwo.R
import com.example.walletfortwo.model.ExpenditureItem
import com.example.walletfortwo.model.User
import com.example.walletfortwo.model.repository.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                val preferences = applicationContext.getSharedPreferences("PREFERENCE_KEY", Context.MODE_PRIVATE)
                if (preferences.getBoolean("FIRST_BOOT", true)) {
                    firstLaunch()
                    preferences.edit().putBoolean("FIRST_BOOT", false).apply()
                }
                UserRepository.initUserList(application)
                UserDetailRepository.initUserDetail(application)
            }
        }
        observeChanged()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        findViewById<BottomNavigationView>(R.id.bottom_navigation).setupWithNavController(navHostFragment.navController)
    }

    private suspend fun firstLaunch() {
        UserRepository.updateUser(application, User(0,"User1", R.color.user_red))
        UserRepository.updateUser(application, User(1,"User2", R.color.user_blue))
        insertItem("家賃", R.drawable.ic_home)
        insertItem("食費", R.drawable.ic_food)
        insertItem("消耗品費", R.drawable.ic_store)
        insertItem("電気代", R.drawable.ic_light)
        insertItem("ガス代", R.drawable.ic_fire)
        insertItem("水道代", R.drawable.ic_water)
        insertItem("回線代", R.drawable.ic_wifi)
        insertItem("その他", R.drawable.ic_more)
    }

    private fun observeChanged() {
        LifeCostRepository.getAdd().observe(this) {
            UserDetailRepository.updateUserDetailLifeCost(true, it)
        }
        LifeCostRepository.getRemove().observe(this) {
            UserDetailRepository.updateUserDetailLifeCost(false, it)
        }
        GiveCostRepository.getAdd().observe(this) {
            UserDetailRepository.updateUserDetailGiveCost(true, it)
        }
        GiveCostRepository.getRemove().observe(this) {
            UserDetailRepository.updateUserDetailGiveCost(false, it)
        }
        UserRepository.getEdit().observe(this) {
            UserDetailRepository.updateUserDetailUser(it)
        }
    }

    private suspend fun insertItem(name: String, id: Int) {
        ExpenditureItemRepository.insert(application, ExpenditureItem(name, id))
    }
}