package com.mikhail.pravilov.mit.XUnit.annotations;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for test method of test class.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {
    /**
     * Expected exception parameter.
     * @return Class of an exception that is expected to be thrown.
     */
    @NotNull
    Class<? extends Exception> expected() default NoExceptionExpected.class;

    /**
     * Reason why this test must be ignored.
     * @return reason in string.
     */
    @NotNull
    String ignore() default "";

    /**
     * Exception that describes that no exception is expected.
     */
    class NoExceptionExpected extends Exception {
    }
}

