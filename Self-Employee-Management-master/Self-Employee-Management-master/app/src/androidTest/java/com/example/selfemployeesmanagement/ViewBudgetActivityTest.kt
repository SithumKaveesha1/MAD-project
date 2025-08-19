package com.example.selfemployeesmanagement

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.any
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class ViewBudgetActivityTest {
    @get:Rule
    var rule = ActivityTestRule(ViewBudgetActivity::class.java)

    @Test
    fun testGetBudgetData() {
        // Wait for the data to load
        Thread.sleep(2000)

        // Check that the RecyclerView is visible and has at least one item
        onView(withId(R.id.rvBudget)).check(matches(isDisplayed()))
        onView(withId(R.id.rvBudget)).check(matches(hasMinimumChildCount(1)))
    }
}
