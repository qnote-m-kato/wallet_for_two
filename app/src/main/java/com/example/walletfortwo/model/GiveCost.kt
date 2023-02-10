package com.example.walletfortwo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GiveCost(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: String,
    val fromUserId: Int,
    val toUserId: Int,
    val expenditureItem: String,
    val expenditureItemResource: Int,
    val cost: Int,
    val remarks: String?
)
