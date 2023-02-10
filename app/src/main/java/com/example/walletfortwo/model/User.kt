package com.example.walletfortwo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    val id: Int,
    var name: String,
    var color: Int
)
