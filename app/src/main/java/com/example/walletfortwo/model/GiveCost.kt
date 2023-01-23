package com.example.walletfortwo.model

import androidx.room.PrimaryKey
data class GiveCost(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: String,
    val fromUser: User,
    val toUser: User,
    val expenditureItem: ExpenditureItem,
    val cost: Int,
    val remarks: String?
)
