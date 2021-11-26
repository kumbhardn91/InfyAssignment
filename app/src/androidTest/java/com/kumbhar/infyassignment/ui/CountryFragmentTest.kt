package com.kumbhar.infyassignment.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.kumbhar.infyassignment.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class CountryFragmentTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var fragmentScenario: FragmentScenario<CountryFragment>

    @Before
    fun setUp() {
        fragmentScenario = launchFragmentInContainer(themeResId = R.style.Theme_InfyAssignment)
        fragmentScenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun testViewOnFragment() {
        onView(withId(R.id.recyclerView)).perform(ViewActions.swipeDown())
    }

    @After
    fun tearDown() {
    }

}