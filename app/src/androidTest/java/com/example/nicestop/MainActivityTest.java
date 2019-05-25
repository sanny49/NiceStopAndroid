package com.example.nicestop;

import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private String code = "20191";
    private String password = "sanny";
    private MainActivity mainActivity = null;
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(menuTab.class.getName(),null,false);
    @Before
    public void setUp() throws Exception {
        mainActivity = testRule.getActivity();
    }

    @Test
    public void testUserInputScenario(){
        getInstrumentation().waitForMonitorWithTimeout(monitor,1000);
    //editText
        Espresso.onView(withId(R.id.editTextCode)).perform(typeText(code));
        getInstrumentation().waitForMonitorWithTimeout(monitor,1000);
    //close soft keyboard
        Espresso.closeSoftKeyboard();
    //editText
        Espresso.onView(withId(R.id.editTextPassword)).perform(typeText(password));
    //close soft keyboard
        Espresso.closeSoftKeyboard();
    //Button
        Espresso.onView(withId(R.id.buttonCode)).perform(click());

    }

    @After
    public void tearDown() throws Exception {
        mainActivity =null;
    }
}