package com.example.walletfortwo.model

import androidx.room.PrimaryKey

data class User(
    @PrimaryKey
    val name: String,
    val color: Int,
    val totalCost: Int,
)
