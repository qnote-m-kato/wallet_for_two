package com.example.walletfortwo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GiveCost(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: String,
    val fromUserName: String,
    val fromUserResource: Int,
    val toUserName: String,
    val toUserResource: Int,
    val expenditureItem: Int,
    val cost: Int,
    val remarks: String?
)
