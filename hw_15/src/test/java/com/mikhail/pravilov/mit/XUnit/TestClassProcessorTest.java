package com.mikhail.pravilov.mit.XUnit;

import org.junit.Test;
import testClasses.*;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class TestClassProcessorTest {
    @Test
    public void complexCaseTestClass() throws NoSuchMethodException {
        TestClassProcessor testClassProcessor = new TestClassProcessor(ComplexCaseTestClass.class);
        Method expected;
        Method real;
        expected = ComplexCaseTestClass.class.getMethod("beforeClassMethod");
        real =  testClassProcessor.getBeforeClassMethod().get();
        assertEquals(expected, real);

        expected = ComplexCaseTestClass.class.getMethod("beforeMethod");
        real =  testClassProcessor.getBeforeMethod().get();
        assertEquals(expected, real);

        expected = ComplexCaseTestClass.class.getMethod("afterTest");
        real =  testClassProcessor.getAfterMethod().get();
        assertEquals(expected, real);

        expected = ComplexCaseTestClass.class.getMethod("afterClassTest");
        real =  testClassProcessor.getAfterClassMethod().get();
        assertEquals(expected, real);
    }

    @Test(expected = IllegalStateException.class)
    public void twoBeforeClassMethodsFails() {
        new TestClassProcessor(TwoBeforeClass.class);
    }

    @Test(expected = IllegalStateException.class)
    public void twoBeforeMethodsFails() {
        new TestClassProcessor(TwoBefore.class);
    }

    @Test(expected = IllegalStateException.class)
    public void twoAfterMethodsFails() {
        new TestClassProcessor(TwoAfter.class);
    }

    @Test(expected = IllegalStateException.class)
    public void twoAfterClassMethodsFails() {
        new TestClassProcessor(TwoAfterClass.class);
    }
}