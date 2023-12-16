package com.ucjc;

import static org.junit.Assert.fail;

import org.junit.Test;

public class AppTest {

    @Test
    public void testAppMainMethod() {
        try {
            // Call the main method of App.java
            App.main(new String[]{});
        } catch (Exception e) {
            // If an exception occurs, fail the test
            e.printStackTrace();
            fail("The main method threw an exception: " + e.getMessage());
        }
    }
}
