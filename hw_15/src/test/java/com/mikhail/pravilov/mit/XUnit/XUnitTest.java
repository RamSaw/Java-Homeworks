package com.mikhail.pravilov.mit.XUnit;

import org.junit.Test;
import testClasses.ComplexCaseTestClass;

import java.util.List;

import static org.junit.Assert.*;

public class XUnitTest {

    @Test
    public void runTests() throws NoSuchMethodException {
        XUnit xUnit = new XUnit(ComplexCaseTestClass.class);
        List<TestResult> testResults = xUnit.runTests();
        for (TestResult testResult : testResults) {
            if (testResult.getTestMethod().equals(ComplexCaseTestClass.class.getMethod("testBefore"))) {
                assertTrue(testResult.isPassed());
            }
            else if (testResult.getTestMethod().equals(ComplexCaseTestClass.class.getMethod("testBeforeRunsEachTime"))) {
                assertTrue(testResult.isPassed());
            }
            else if (testResult.getTestMethod().equals(ComplexCaseTestClass.class.getMethod("failTest"))) {
                assertFalse(testResult.isPassed());
                assertTrue(testResult.getException() instanceof AssertionError);
            }
            else if (testResult.getTestMethod().equals(ComplexCaseTestClass.class.getMethod("successTest"))) {
                assertTrue(testResult.isPassed());
            }
            else if (testResult.getTestMethod().equals(ComplexCaseTestClass.class.getMethod("ignoredTest"))) {
                assertTrue(testResult.isIgnored());
                assertEquals("This method is ignored", testResult.getIgnoreReason());
            }
            else if (testResult.getTestMethod().equals(ComplexCaseTestClass.class.getMethod("exceptionIsThrown"))) {
                assertTrue(testResult.isPassed());
                assertTrue(testResult.getException() instanceof Exception);
            }
            else if (testResult.getTestMethod().equals(ComplexCaseTestClass.class.getMethod("exceptionExpectedButNotThrown"))) {
                assertFalse(testResult.isPassed());
                assertNull(testResult.getException());
            }
            else {
                throw new IllegalStateException("No such method but test result of it exists");
            }
        }
    }
}