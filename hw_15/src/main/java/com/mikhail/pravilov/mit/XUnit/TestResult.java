package com.mikhail.pravilov.mit.XUnit;

public class TestResult {
    private boolean isPassed;
    private Throwable exception;
    private String methodName;
    private String failReason;
    private boolean isIgnored;
    private String ignoreReason;
    private long time;

    String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    TestResult(String methodName) {
        this.methodName = methodName;
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
