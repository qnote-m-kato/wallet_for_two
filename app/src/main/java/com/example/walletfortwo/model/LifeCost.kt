package com.example.walletfortwo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LifeCost(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: String,
    val userName: String,
    val userResource: Int,
    val expenditureItem: String,
    val expenditureItemResource: Int,
    val cost: Int,
    val remarks: String?
)