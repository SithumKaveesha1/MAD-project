package com.example.selfemployeesmanagement

data class BudgetModel (
    var budgetId: String? = null,
    var name: String? = null,
    var period: String? = null,
    var amount: String? = null,
    var currencyType: String? = null,
    var accountType: String? = null,
    var foods: String? = null,
    var shopping: String? = null,
    var transpotaion: String? = null,
    var housing: String? = null,
    var communication: String? = null,
    var finance: String? = null,
    var life: String? = null,
    var other: String? = null
)