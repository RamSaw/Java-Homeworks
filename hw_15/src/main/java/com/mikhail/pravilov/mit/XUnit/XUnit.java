package com.mikhail.pravilov.mit.XUnit;

import com.mikhail.pravilov.mit.XUnit.annotations.Test;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * XUnit class that can run tests marked with annotations.
 */
class XUnit {
    /**
     * Class to test.
     */
    @NotNull
    private Class<?> testClass;
    /**
     * Processor of test class that parses test class.
     */
    @NotNull
    private TestClassProcessor testClassProcessor;

    /**
     * Constructor with test class.
     * @param testClass class to test.
     */
    XUnit(@NotNull Class<?> testClass) {
        this.testClass = testClass;
        testClassProcessor = new TestClassProcessor(testClass);
    }

    /**
     * Runs all tests found in given test class in constuctor.
     * @return list of test results.
     */
    @NotNull
    List<TestResult> runTests() {
        testClassProcessor.getBeforeClassMethod().ifPresent(method -> {
            try {
                method.invoke(null);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException("Before Class method failed to execute", e);
            }
        });

        List<TestResult> testResults = new LinkedList<>();
        for (Method testMethod : testClassProcessor.getTestMethods()) {
            testResults.add(runTest(testMethod));
        }

        testClassProcessor.getAfterClassMethod().ifPresent(method -> {
            try {
                method.invoke(null);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException("After Class method failed to execute", e);
            }
        });
        return testResults;
    }

    @NotNull
    private TestResult runTest(@NotNull Method testMethod) {
        TestResult testResult = new TestResult(testMethod);

        String ignoreMessage = testMethod.getAnnotation(Test.class).ignore();
        if (!ignoreMessage.equals("")) {
            testResult.setIgnored();
            testResult.setIgnoreReason(ignoreMessage);
            return testResult;
        }

        Object testClassInstance;
        try {
            testClassInstance = testClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Cannot create instance of test class", e);
        }

        testClassProcessor.getBeforeMethod().ifPresent(method -> {
            try {
                method.invoke(testClassInstance);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException("Before method failed to execute", e);
            }
        });

        long startTime = System.nanoTime();
        try {
            testMethod.invoke(testClassInstance);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Cannot access test method", e);
        } catch (InvocationTargetException e) {
            testResult.setException(e.getCause());
        }
        long passedTime = System.nanoTime() - startTime;
        testResult.setTime(passedTime);
        Class<? extends Exception> expectedException = testMethod.getAnnotation(Test.class).expected();
        if (testResult.getException() != null) {
            testResult.setPassed(expectedException.isInstance(testResult.getException()));
            if (!testResult.isPassed()) {
                testResult.setFailReason("Thrown exception is not that was expected");
            }
        }
        else {
            testResult.setPassed(expectedException.equals(Test.NoExceptionExpected.class));
            if (!testResult.isPassed()) {
                testResult.setFailReason("Exception expected but hasn't been thrown");
            }
        }

        testClassProcessor.getAfterMethod().ifPresent(method -> {
            try {
                method.invoke(testClassInstance);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException("Cannot invoke after method", e);
            }
        });
        return testResult;
    }
}
