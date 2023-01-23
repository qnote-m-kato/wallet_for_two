package com.example.walletfortwo.model

import androidx.room.PrimaryKey

data class ExpenditureItem(
    @PrimaryKey
    val name: String,
    val resource: Int
)
