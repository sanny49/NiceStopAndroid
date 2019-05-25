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

public class MenuTabTest {

    @Rule
    public ActivityTestRule<menuTab> testRule = new ActivityTestRule<menuTab>(menuTab.class);
    private String plateNo = "GTI100";
    private String ln = "2002032200030696";
    private menuTab menuTab = null;
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(Save.class.getName(),null,false);
    @Before
    public void setUp() throws Exception {
        menuTab = testRule.getActivity();
    }
    @Test
    public void testUserInputScenario(){
        getInstrumentation().waitForMonitorWithTimeout(monitor,1000);
        Espresso.onView(withId(R.id.checkBoxDO)).perform(click());
        getInstrumentation().waitForMonitorWithTimeout(monitor,1000);
        Espresso.onView(withId(R.id.buttonScan1)).perform(click());
        getInstrumentation().waitForMonitorWithTimeout(monitor,10000);
        //Espresso.onView(withId(R.id.editTextLicenseNo)).perform(typeText(ln));
        //Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.buttonOk)).perform(click());
        Espresso.onView(withId(R.id.editTextPlateNo)).perform(typeText(plateNo));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.buttonEnter)).perform(click());
        getInstrumentation().waitForMonitorWithTimeout(monitor,4000);
        Espresso.onView(withId(R.id.checkBox3)).perform(click());
        getInstrumentation().waitForMonitorWithTimeout(monitor,1000);
        Espresso.onView(withId(R.id.checkBox2)).perform(click());
        getInstrumentation().waitForMonitorWithTimeout(monitor,1000);
        Espresso.onView(withId(R.id.buttonLocation)).perform(click());
        getInstrumentation().waitForMonitorWithTimeout(monitor,2000);
        Espresso.onView(withId(R.id.buttonSave)).perform(click());
    }
    @After
    public void tearDown() throws Exception {
        menuTab = null;
    }
}