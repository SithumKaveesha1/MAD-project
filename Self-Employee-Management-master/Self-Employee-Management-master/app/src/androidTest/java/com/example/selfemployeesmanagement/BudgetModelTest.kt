package com.example.selfemployeesmanagement

import org.junit.Assert.assertEquals
import org.junit.Test

class BudgetModelTest {

    @Test
    fun testBudgetModel() {
        val budgetId = "1"
        val name = "Test Budget"
        val period = "Weekly"
        val amount = "1000"
        val currencyType = "USD"
        val accountType = "Bank Account"
        val foods = "200"
        val shopping = "300"
        val transportation = "100"
        val housing = "200"
        val communication = "100"
        val finance = "0"
        val life = "100"
        val other = "0"

        val budgetModel = BudgetModel(
            budgetId, name, period, amount, currencyType, accountType,
            foods, shopping, transportation, housing, communication, finance, life, other
        )

        assertEquals(budgetId, budgetModel.budgetId)
        assertEquals(name, budgetModel.name)
        assertEquals(period, budgetModel.period)
        assertEquals(amount, budgetModel.amount)
        assertEquals(currencyType, budgetModel.currencyType)
        assertEquals(accountType, budgetModel.accountType)
        assertEquals(foods, budgetModel.foods)
        assertEquals(shopping, budgetModel.shopping)
        assertEquals(transportation, budgetModel.transpotaion)
        assertEquals(housing, budgetModel.housing)
        assertEquals(communication, budgetModel.communication)
        assertEquals(finance, budgetModel.finance)
        assertEquals(life, budgetModel.life)
        assertEquals(other, budgetModel.other)
    }

}
