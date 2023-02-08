package com.example.walletfortwo.model

data class UserDetail(
    val user: User,
    var totalCost: Int,
    val lifeCosts: List<LifeCost>,
    val giveCostsFrom: List<GiveCost>,
    val giveCostsTo: List<GiveCost>
)
