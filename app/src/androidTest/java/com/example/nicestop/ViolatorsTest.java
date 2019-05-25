package com.example.nicestop;

import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class ViolatorsTest {
    @Rule
    public ActivityTestRule<Violators> testRule = new ActivityTestRule<Violators>(Violators.class);
    private Violators violators = null;
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(menuTab.class.getName(),null,false);
    @Before
    public void setUp() throws Exception {
        violators = testRule.getActivity();
    }
    @Test
    public void testUserInputScenario(){
        getInstrumentation().waitForMonitorWithTimeout(monitor,15000);

    }
    @After
    public void tearDown() throws Exception {
        violators = null;
    }
}