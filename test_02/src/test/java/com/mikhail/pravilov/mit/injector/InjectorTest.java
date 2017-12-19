package com.mikhail.pravilov.mit.injector;

import com.mikhail.pravilov.mit.injector.expetions.AmbiguousImplementationException;
import com.mikhail.pravilov.mit.injector.expetions.ImplementationNotFoundException;
import com.mikhail.pravilov.mit.injector.expetions.InjectionCycleException;
import com.mikhail.pravilov.mit.injector.testClasses.ClassWithOneClassDependency;
import com.mikhail.pravilov.mit.injector.testClasses.ClassWithOneInterfaceDependency;
import com.mikhail.pravilov.mit.injector.testClasses.ClassWithoutDependencies;
import com.mikhail.pravilov.mit.injector.testClasses.InterfaceImpl;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class InjectorTest {
    @Test
    public void injectorShouldInitializeClassWithoutDependencies()
            throws Exception {
        Object object = Injector.initialize("com.mikhail.pravilov.mit.injector.testClasses.ClassWithoutDependencies", Collections.emptyList());
        assertTrue(object instanceof ClassWithoutDependencies);
    }

    @Test
    public void injectorShouldInitializeClassWithOneClassDependency()
            throws Exception {
        Object object = Injector.initialize(
                "com.mikhail.pravilov.mit.injector.testClasses.ClassWithOneClassDependency",
                Collections.singletonList("com.mikhail.pravilov.mit.injector.testClasses.ClassWithoutDependencies")
        );
        assertTrue(object instanceof ClassWithOneClassDependency);
        ClassWithOneClassDependency instance = (ClassWithOneClassDependency) object;
        assertTrue(instance.dependency != null);
    }

    @Test
    public void injectorShouldInitializeClassWithOneInterfaceDependency()
            throws Exception {
        Object object = Injector.initialize(
                "com.mikhail.pravilov.mit.injector.testClasses.ClassWithOneInterfaceDependency",
                Collections.singletonList("com.mikhail.pravilov.mit.injector.testClasses.InterfaceImpl")
        );
        assertTrue(object instanceof ClassWithOneInterfaceDependency);
        ClassWithOneInterfaceDependency instance = (ClassWithOneInterfaceDependency) object;
        assertTrue(instance.dependency instanceof InterfaceImpl);
    }

    @Test(expected = InjectionCycleException.class)
    public void throwsInjectionCycleException() throws Exception {
        Injector.initialize("com.mikhail.pravilov.mit.injector.testClasses.Class1Cycle",
                Arrays.asList("com.mikhail.pravilov.mit.injector.testClasses.Class2Cycle",
                              "com.mikhail.pravilov.mit.injector.testClasses.Class3Cycle"));
    }

    @Test(expected = AmbiguousImplementationException.class)
    public void throwsAmbiguousImplementationException() throws Exception {
        Injector.initialize("com.mikhail.pravilov.mit.injector.testClasses.ClassAmbiguous",
                Arrays.asList("com.mikhail.pravilov.mit.injector.testClasses.InterfaceImpl",
                        "com.mikhail.pravilov.mit.injector.testClasses.InterfaceImpl2"));
    }

    @Test(expected = ImplementationNotFoundException.class)
    public void throwsImplementationNotFoundException() throws Exception {
        Injector.initialize("com.mikhail.pravilov.mit.injector.testClasses.ClassAmbiguous",
                Collections.emptyList());
    }
}