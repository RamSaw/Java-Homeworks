package com.mikhail.pravilov.mit.XUnit;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

/**
 * Class that describes test result.
 */
class TestResult {
    private boolean isPassed;
    @NotNull
    private Method testMethod;
    private Throwable exception;
    private String failReason;
    private boolean isIgnored;
    private String ignoreReason;
    private long time;

    String getMethodName() {
        return testMethod.getName();
    }

    @NotNull
    Method getTestMethod() {
        return testMethod;
    }

    TestResult(@NotNull Method testMethod) {
        this.testMethod = testMethod;
    }

    String getFailReason() {
        return failReason;
    }

    void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    boolean isIgnored() {
        return isIgnored;
    }

    void setIgnored() {
        isIgnored = true;
    }

    String getIgnoreReason() {
        return ignoreReason;
    }

    void setIgnoreReason(String ignoreReason) {
        this.ignoreReason = ignoreReason;
    }

    boolean isPassed() {
        return isPassed;
    }

    void setPassed(boolean passed) {
        isPassed = passed;
    }

    Throwable getException() {
        return exception;
    }

    void setException(Throwable exception) {
        this.exception = exception;
    }

    long getTime() {
        return time;
    }

    void setTime(long time) {
        this.time = time;
    }
}
