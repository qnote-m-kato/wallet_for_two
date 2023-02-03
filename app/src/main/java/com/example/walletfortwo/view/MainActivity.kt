package com.example.walletfortwo.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.walletfortwo.R
import com.example.walletfortwo.model.User
import com.example.walletfortwo.model.repository.UserRepository
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
                UserRepository.updateUser(application, User("Myu", R.color.teal_200, 0))
                UserRepository.updateUser(application, User("kobayashi", R.color.purple_700, 0))
            }
        }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        findViewById<BottomNavigationView>(R.id.bottom_navigation).setupWithNavController(navHostFragment.navController)
    }
}