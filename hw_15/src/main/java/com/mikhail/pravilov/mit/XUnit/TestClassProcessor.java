package com.mikhail.pravilov.mit.XUnit;

import com.mikhail.pravilov.mit.XUnit.annotations.*;
import com.mikhail.pravilov.mit.XUnit.exceptions.MultipleAnnotationsException;
import com.mikhail.pravilov.mit.XUnit.exceptions.MultiplePreparationMethodsException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class TestClassProcessor {
    /**
     * Test class to process.
     */
    @NotNull
    private Class testClass;
    @Nullable
    private Method beforeClassMethod;
    @Nullable
    private Method beforeMethod;
    @Nullable
    private Method afterMethod;
    @Nullable
    private Method afterClassMethod;
    @NotNull
    private List<Method> testMethods;

    /**
     * Default constructor. Takes test class and immediately processes it.
     * @param testClass test class to process.
     */
    TestClassProcessor(@NotNull Class testClass) {
        this.testClass = testClass;
        throwIfExistsMethodWithSeveralAnnotations();
        beforeClassMethod = findPreparationMethod(this::isBeforeClassMethod);
        beforeMethod = findPreparationMethod(this::isBeforeMethod);
        afterMethod = findPreparationMethod(this::isAfterMethod);
        afterClassMethod = findPreparationMethod(this::isAfterClassMethod);
        testMethods = findTestMethods();
    }

    /**
     * Finds preparation method and checks it to be single in class.
     * @param isTargetMethod predicate that says if given method is target preparation method.
     * @throws MultiplePreparationMethodsException if there are several preparation methods in class.
     * @return found method or null;
     */
    @Nullable
    private Method findPreparationMethod(@NotNull Predicate<Method> isTargetMethod) {
        Method targetMethod = null;
        for (Method method : testClass.getMethods()) {
            if (isTargetMethod.test(method)) {
                if (targetMethod != null) {
                    throw new MultiplePreparationMethodsException("Multiple preparation methods: " +
                            method.getName() + " and " + targetMethod.getName());
                }
                targetMethod = method;
            }
        }
        return targetMethod;
    }

    /**
     * Finds all test methods in test class.
     * @return list of test methods.
     */
    @NotNull
    private List<Method> findTestMethods() {
        List<Method> testMethods = new LinkedList<>();
        for (Method method : testClass.getMethods()) {
            if (isTestMethod(method)) {
                testMethods.add(method);
            }
        }
        return testMethods;
    }

    /**
     * Checks that all methods have no more than 1 annotation.
     * @throws MultipleAnnotationsException if method has more than 1 annotation.
     */
    private void throwIfExistsMethodWithSeveralAnnotations() {
        for (Method method : testClass.getMethods()) {
            if (methodHasMultipleAnnotations(method)) {
                throw new MultipleAnnotationsException("Method " + method.getName() + " has multiple annotations");
            }
        }
    }

    /**
     * Checks if given methods has several annotations.
     * @param method to check.
     * @return true if has multiple annotations otherwise false.
     */
    private boolean methodHasMultipleAnnotations(Method method) {
        List<Predicate<Method>> availableAnnotatedMethods = Arrays.asList(this::isBeforeClassMethod,
                this::isBeforeMethod, this::isAfterMethod, this::isAfterClassMethod, this::isTestMethod);
        return availableAnnotatedMethods.stream().
                filter(methodPredicate -> methodPredicate.test(method)).collect(Collectors.toList()).size() > 1;
    }

    /**
     * Predicate that checks for being a before class method.
     * Could be more complex and check for example for being public but I decide to let the programmer check for it.
     * @param method to check.
     * @return true if method is before class otherwise false.
     */
    private boolean isBeforeClassMethod(@NotNull Method method) {
        return method.getAnnotation(BeforeClass.class) != null;
    }

    /**
     * Predicate that checks for being an after method.
     * Could be more complex and check for example for being public but I decide to let the programmer check for it.
     * @param method to check.
     * @return true if method is after otherwise false.
     */
    private boolean isAfterMethod(@NotNull Method method) {
        return method.getAnnotation(After.class) != null;
    }

    /**
     * Predicate that checks for being an after class method.
     * Could be more complex and check for example for being public but I decide to let the programmer check for it.
     * @param method to check.
     * @return true if method is after class otherwise false.
     */
    private boolean isAfterClassMethod(@NotNull Method method) {
        return method.getAnnotation(AfterClass.class) != null;
    }

    /**
     * Predicate that checks for being a before method.
     * Could be more complex and check for example for being public but I decide to let the programmer check for it.
     * @param method to check.
     * @return true if method is before otherwise false.
     */
    private boolean isBeforeMethod(@NotNull Method method) {
        return method.getAnnotation(Before.class) != null;
    }

    /**
     * Predicate that checks for being a test method.
     * Could be more complex and check for example for being public but I decide to let the programmer check for it.
     * @param method to check.
     * @return true if method is test otherwise false.
     */
    private boolean isTestMethod(@NotNull Method method) {
        return method.getAnnotation(Test.class) != null;
    }

    /**
     * Getter for before class method. Packed into optional because is null if there is no before class method.
     * @return optional of before class method.
     */
    @NotNull
    Optional<Method> getBeforeClassMethod() {
        return Optional.ofNullable(beforeClassMethod);
    }

    /**
     * Getter for before method. Packed into optional because is null if there is no before method.
     * @return optional of before method.
     */
    @NotNull
    Optional<Method> getBeforeMethod() {
        return Optional.ofNullable(beforeMethod);
    }

    /**
     * Getter for after method. Packed into optional because is null if there is no after method.
     * @return optional of after method.
     */
    @NotNull
    Optional<Method> getAfterMethod() {
        return Optional.ofNullable(afterMethod);
    }

    /**
     * Getter for after class method. Packed into optional because is null if there is no after class method.
     * @return optional of after class method.
     */
    @NotNull
    Optional<Method> getAfterClassMethod() {
        return Optional.ofNullable(afterClassMethod);
    }

    /**
     * Getter for test method. Packed into optional because is null if there is no test method.
     * @return optional of test method.
     */
    @NotNull
    List<Method> getTestMethods() {
        return testMethods;
    }
}
