package com.mikhail.pravilov.mit.injector;

import com.mikhail.pravilov.mit.injector.expetions.AmbiguousImplementationException;
import com.mikhail.pravilov.mit.injector.expetions.ImplementationNotFoundException;
import com.mikhail.pravilov.mit.injector.expetions.InjectionCycleException;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;

public class Injector {
    /**
     * Creates instance of rootClassName using given dependencies.
     * @param rootClassName name of class instance of that we need to create.
     * @param availableClassNames names of classes that we need as dependency. If availableClasses contains rootClassName then it throws AmbiguousImplementationException.
     * @return instance of rootClassName with Object type.
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     * @throws InjectionCycleException if dependencies creates a cycle.
     * @throws AmbiguousImplementationException if there are several dependencies that can be used.
     * @throws ImplementationNotFoundException if no implementation of needed dependency.
     */
    @NotNull
    public static Object initialize(@NotNull String rootClassName, @NotNull Collection<String> availableClassNames)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, InjectionCycleException, AmbiguousImplementationException, ImplementationNotFoundException {
        ArrayList<Class<?>> availableClasses = new ArrayList<>();

        for (String availableClassName : availableClassNames)
            availableClasses.add(Class.forName(availableClassName));

        return initialize(rootClassName, availableClasses, new HashMap<>(), new ArrayList<>());
    }

    @NotNull
    private static Object initialize(@NotNull String rootClassName, @NotNull ArrayList<Class<?>> availableClasses,
                                     Map<String, Object> initializedClasses, ArrayList<String> calledClasses)
            throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException, InjectionCycleException, AmbiguousImplementationException, ImplementationNotFoundException {
        if (calledClasses.contains(rootClassName) && !initializedClasses.containsKey(rootClassName)) {
            throw new InjectionCycleException();
        }

        if (calledClasses.contains(rootClassName) && initializedClasses.containsKey(rootClassName)) {
            return initializedClasses.get(rootClassName);
        }

        calledClasses.add(rootClassName);

        ArrayList<Class<?>> dependencies = getDependencies(Class.forName(rootClassName), availableClasses);

        ArrayList<Object> instances = new ArrayList<>();
        for (Class<?> dependency : dependencies) {
            instances.add(initialize(dependency.getName(),
                    availableClasses, initializedClasses, calledClasses));
        }

        Class<?> rootClass = Class.forName(rootClassName);
        initializedClasses.put(rootClassName, rootClass);
        return rootClass.getConstructors()[0].newInstance(instances.toArray());
    }

    @NotNull
    private static ArrayList<Class<?>> getDependencies(@NotNull Class<?> someClass, @NotNull ArrayList<Class<?>> availableClasses)
            throws AmbiguousImplementationException, ImplementationNotFoundException {
        Class<?>[] parameters = someClass.getConstructors()[0].getParameterTypes();
        ArrayList<Class<?>> dependencies = new ArrayList<>();
        for (Class<?> parameter : parameters) {
            dependencies.add(findChildForClass(parameter, availableClasses));
        }

        return dependencies;
    }

    @NotNull
    private static Class<?> findChildForClass(@NotNull Class<?> someClass, @NotNull ArrayList<Class<?>> availableClasses)
            throws AmbiguousImplementationException, ImplementationNotFoundException {
        Class<?> found = null;

        for (Class<?> availableClass : availableClasses) {
            if (someClass.isAssignableFrom(availableClass) && !availableClass.isInterface()
                    && !Modifier.isAbstract(availableClass.getModifiers()))
                if (found != null) {
                    throw new AmbiguousImplementationException();
                }
                found = availableClass;
        }

        if (found == null) {
            throw new ImplementationNotFoundException();
        }

        return found;
    }
}
