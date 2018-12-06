package barrylui.randomizertoolkotlin


import android.app.PendingIntent.getActivity
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers

import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    val myActivity = activityRule.activity
    val testString1: String = "Pizza"
    val testString2: String = "Sandwhich Shop"
    val testString3 = "Chinese Food"
    val testString4 = "Suishi"
    val testString5 = "Halal food"
    val testString6 = "Salad bar"

    val testName1 = "Sam"
    val testName2 = "Michael"
    val testName3 = "Adam"

    //butt 1 = postive
    //butt 2 = negative
    @Before
    fun setUp() {
    }


    //Test Random food scenario
    @Test
    fun testUserInputFoodScenario(){

        //Add food items to list
        addtestInput(testString1)
        addtestInput(testString2)
        addtestInput(testString3)
        addtestInput(testString4)
        addtestInput(testString5)
        addtestInput(testString6)

        //Click randomly select one button
        onView(withId(R.id.randomlySelectOneButton))
                .perform(click())
        //Checks if the dialog is displayed
        onView(withText(R.string.randomlyselected)).check(matches(isDisplayed()))
        //Retry
        onView(withId(android.R.id.button1)).perform(click())
        //Check if retry worked and dialog appears
        onView(withText(R.string.randomlyselected)).check(matches(isDisplayed()))
        //Cancel the dialog
        onView(withId(android.R.id.button2)).perform(click())
        //delete the list
        onView(withId(R.id.deleteListImageButton)).perform(click())
    }

    //Test random order for bathroom scenario
    @Test
    fun testUserInputBathroomOrderScenario(){
        //Add names to list
        addtestInput(testName1)
        addtestInput(testName2)
        addtestInput(testName3)

        //Click shuffle button
        onView(withId(R.id.shuffleButton))
                .perform(click())


        //Check if toast appeared
        //onView(withText(R.string.listshufflemsg)).inRoot(withDecorView( not(`is`(myActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));

        //clear list
        onView(withId(R.id.deleteListImageButton)).perform(click())
    }
    fun addtestInput(item: String){
        //Click floating action button
        onView(withId(R.id.fabaddtolistbutton))
                .perform(click())

        //Check if dialog shows up
        onView(withText(R.string.inputitem)).check(matches(isDisplayed()))


        //Type string into eddittext
        onView(withId(R.id.editTextDialogUserInput))
                .perform(typeText(item)).perform()
        //Hit submit
        onView(withId(android.R.id.button1)).perform(click())
    }


    @After
    fun tearDown() {
    }
}