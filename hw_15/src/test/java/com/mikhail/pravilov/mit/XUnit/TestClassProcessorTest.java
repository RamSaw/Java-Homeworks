package com.mikhail.pravilov.mit.XUnit;

import com.mikhail.pravilov.mit.XUnit.exceptions.MultipleAnnotationsException;
import com.mikhail.pravilov.mit.XUnit.exceptions.MultiplePreparationMethodsException;
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

    @Test(expected = MultiplePreparationMethodsException.class)
    public void twoBeforeClassMethodsFails() {
        new TestClassProcessor(TwoBeforeClass.class);
    }

    @Test(expected = MultiplePreparationMethodsException.class)
    public void twoBeforeMethodsFails() {
        new TestClassProcessor(TwoBefore.class);
    }

    @Test(expected = MultiplePreparationMethodsException.class)
    public void twoAfterMethodsFails() {
        new TestClassProcessor(TwoAfter.class);
    }

    @Test(expected = MultiplePreparationMethodsException.class)
    public void twoAfterClassMethodsFails() {
        new TestClassProcessor(TwoAfterClass.class);
    }

    @Test(expected = MultipleAnnotationsException.class)
    public void multipleAnnotationsFails() {
        new TestClassProcessor(MultipleAnnotations.class);
    }
}