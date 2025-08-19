import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.selfemployeesmanagement.R
import com.example.selfemployeesmanagement.UpdateBudgetActivity

@RunWith(AndroidJUnit4::class)
class UpdateBudgetActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(UpdateBudgetActivity::class.java)

    @Test
    fun updateBudget() {
        // Find and interact with the UI elements
        onView(withId(R.id.customername)).perform(typeText("John"))
        onView(withId(R.id.period)).perform(click())
        onView(withText("Monthly")).inRoot(isDialog()).perform(click())
        onView(withId(R.id.amount)).perform(typeText("500"))
        onView(withId(R.id.currencyType)).perform(click())
        onView(withText("USD")).inRoot(isDialog()).perform(click())
        onView(withId(R.id.accountType)).perform(click())
        onView(withText("Checking")).inRoot(isDialog()).perform(click())
        onView(withId(R.id.foods)).perform(typeText("100"))
        onView(withId(R.id.shopping)).perform(typeText("50"))
        onView(withId(R.id.transpotation)).perform(typeText("50"))
        onView(withId(R.id.housing)).perform(typeText("200"))
        onView(withId(R.id.communication)).perform(typeText("50"))
        onView(withId(R.id.finance)).perform(typeText("25"))
        onView(withId(R.id.life)).perform(typeText("25"))
        onView(withId(R.id.other)).perform(typeText("0"))
        onView(withId(R.id.btnSaveData)).perform(click())

        // Verify that the toast message is displayed
        Thread.sleep(1000) // Add a delay of 1 second
        onView(withText("Data updated successfully")).inRoot(ToastMatcher()).check(matches(isDisplayed()))

//        onView(withText("Data updated successfully")).inRoot(ToastMatcher()).check(matches(isDisplayed()))

    }

}
