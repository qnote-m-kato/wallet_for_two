package com.example.walletfortwo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    val name: String,
    val color: Int
)
