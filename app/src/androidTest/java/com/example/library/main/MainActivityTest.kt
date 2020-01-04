package com.example.library.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.example.library.R
import org.junit.Rule
import org.junit.Test

class MainActivityTest {


    @get:Rule
    val activityTestRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java)

//    @get:Rule
//    val activityScenarioRule: ActivityScenarioRule<MainActivity> = activityScenarioRule()


    @Test
    fun onRegisterMenuClick_registerActivityDisplayed() {
        onView(withId(R.id.menu_register)).perform(click())
        onView(withId(R.id.register_activity_layout)).check(matches(isDisplayed()))
    }

}