package com.example.walletfortwo.model

import androidx.room.PrimaryKey

data class LifeCost(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: String,
    val user: User,
    val expenditureItem: ExpenditureItem,
    val cost: Int,
    val remarks: String?
)