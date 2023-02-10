package com.example.walletfortwo.model

data class UserDetail(
    val user: User,
    var totalCost: Int,
    val lifeCosts: MutableList<LifeCost>,
    val giveCostsFrom: MutableList<GiveCost>,
    val giveCostsTo: MutableList<GiveCost>
)
