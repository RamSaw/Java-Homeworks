package com.mikhail.pravilov.mit.XUnit;

import com.mikhail.pravilov.mit.XUnit.annotations.*;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

class TestClassProcessor {
    private Class testClass;
    private Method beforeClassMethod;
    private Method beforeMethod;
    private Method afterMethod;
    private Method afterClassMethod;
    private List<Method> testMethods = new LinkedList<>();

    TestClassProcessor(Class testClass) {
        this.testClass = testClass;
        beforeClassMethod = findPreparationMethod(this::isBeforeClassMethod);
        beforeMethod = findPreparationMethod(this::isBeforeMethod);
        afterMethod = findPreparationMethod(this::isAfterMethod);
        afterClassMethod = findPreparationMethod(this::isAfterClassMethod);
        testMethods = findTestMethods();
    }

    private Method findPreparationMethod(Predicate<Method> isTargetMethod) {
        Method targetMethod = null;
        for (Method method : testClass.getMethods()) {
            if (isTargetMethod.test(method)) {
                if (targetMethod != null) {
                    throw new IllegalStateException("Multiple BeforeClass methods");
                }
                targetMethod = method;
            }
        }
        return targetMethod;
    }

    private List<Method> findTestMethods() {
        List<Method> testMethods = new LinkedList<>();
        for (Method method : testClass.getMethods()) {
            if (isTestMethod(method)) {
                testMethods.add(method);
            }
        }
        return testMethods;
    }

    private boolean isBeforeClassMethod(Method method) {
        return method.getAnnotation(BeforeClass.class) != null;
    }

    private boolean isAfterMethod(Method method) {
        return method.getAnnotation(After.class) != null;
    }

    private boolean isAfterClassMethod(Method method) {
        return method.getAnnotation(AfterClass.class) != null;
    }

    private boolean isBeforeMethod(Method method) {
        return method.getAnnotation(Before.class) != null;
    }

    private boolean isTestMethod(Method method) {
        return method.getAnnotation(Test.class) != null;
    }

    Optional<Method> getBeforeClassMethod() {
        return Optional.ofNullable(beforeClassMethod);
    }

    Optional<Method> getBeforeMethod() {
        return Optional.ofNullable(beforeMethod);
    }

    Optional<Method> getAfterMethod() {
        return Optional.ofNullable(afterMethod);
    }

    Optional<Method> getAfterClassMethod() {
        return Optional.ofNullable(afterClassMethod);
    }

    List<Method> getTestMethods() {
        return testMethods;
    }
}
