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
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class SaveTest {

    @Rule
    public ActivityTestRule<Save> testRule = new ActivityTestRule<Save>(Save.class);

    private Save save = null;
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(Violators.class.getName(),null,false);
    @Before
    public void setUp() throws Exception {
        save = testRule.getActivity();
    }
    @Test
    public void testUserInputScenario(){

        getInstrumentation().waitForMonitorWithTimeout(monitor,2000);
        Espresso.onView(withId(R.id.buttonConnect)).perform(click());
        getInstrumentation().waitForMonitorWithTimeout(monitor,2000);
        Espresso.onView(withId(R.id.buttonPrint)).perform(click());
        getInstrumentation().waitForMonitorWithTimeout(monitor,2000);
        Espresso.onView(withId(R.id.buttonDisconnect)).perform(click());
        getInstrumentation().waitForMonitorWithTimeout(monitor,2000);
        Espresso.onView(withId(R.id.buttonSend)).perform(click());
        getInstrumentation().waitForMonitorWithTimeout(monitor,0);
    }
    @After
    public void tearDown() throws Exception {
        save = null;
    }
}