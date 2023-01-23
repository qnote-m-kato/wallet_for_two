package com.example.walletfortwo.model

data class UserDetail(
    val user: User,
    val lifeCosts: List<LifeCost>,
    val giveCosts: List<GiveCost>
)
