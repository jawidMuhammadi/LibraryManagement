package com.example.library.book_register

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.rules.activityScenarioRule
import com.example.library.R
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test

class BookRegisterActivityTest {

    @get:Rule
    val activityScenarioRule: ActivityScenarioRule<BookRegisterActivity> = activityScenarioRule()

    @Test
    fun onSpinnerClick_Jawid_displayJawidOnTextView() {

        //when click on spinner
        onView(withId(R.id.sp_students_name)).perform(click())

        //when click on Jawid item from spinner
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Jawid"))).perform(click())

        //Then check clicked item from spinner has been shown in text
        onView(withId(R.id.tv_selected_borrower_name)).check(matches(withText("Jawid")))
    }
}