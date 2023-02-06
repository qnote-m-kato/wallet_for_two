package com.example.walletfortwo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExpenditureItem(
    @PrimaryKey
    val name: String,
    val resource: Int
)
