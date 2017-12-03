package com.mikhail.pravilov.mit.Collections;

import com.mikhail.pravilov.mit.Function1.Function1;
import com.mikhail.pravilov.mit.Function2.Function2;
import com.mikhail.pravilov.mit.Predicate.Predicate;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Class that implements basic functions on collections
 */
public class Collections {
    /**
     * Applies given function to each element in collection.
     *
     * @param function   to apply to elements.
     * @param collection elements.
     * @param <T>        base type of elements.
     * @param <R>        type of elements in result list.
     * @return List of elements applied to function.
     */
    @NotNull
    static public <T, R> List<R> map(@NotNull Function1<? super T, ? extends R> function, @NotNull Iterable<? extends T> collection) {
        ArrayList<R> mapped = new ArrayList<>();
        for (T element : collection) {
            mapped.add(function.apply(element));
        }

        return mapped;
    }

    /**
     * Filters collection: will be left only elements that suit the predicate.
     *
     * @param predicate  condition on elements.
     * @param collection elements.
     * @param <T>        basic type of elements.
     * @return List of elements that are true on given predicate.
     */
    @NotNull
    static public <T> List<T> filter(@NotNull Predicate<? super T> predicate, @NotNull Iterable<? extends T> collection) {
        final ArrayList<T> mapped = new ArrayList<>();
        for (T element : collection) {
            if (predicate.apply(element))
                mapped.add(element);
        }

        return mapped;
    }

    /**
     * Method to take all elements while condition is true.
     *
     * @param predicate  condition.
     * @param collection elements.
     * @param <T>        basic type of elements.
     * @return List of first elements that are true on given predicate.
     */
    @NotNull
    static public <T> List<T> takeWhile(@NotNull Predicate<? super T> predicate, @NotNull Iterable<? extends T> collection) {
        final ArrayList<T> mapped = new ArrayList<>();
        for (T element : collection) {
            if (!predicate.apply(element))
                break;
            mapped.add(element);
        }

        return mapped;
    }

    /**
     * Method to take all elements while condition is false.
     *
     * @param predicate  condition.
     * @param collection elements.
     * @param <T>        basic type of elements.
     * @return List of first elements that are false on given predicate.
     */
    @NotNull
    static public <T> List<T> takeUnless(@NotNull Predicate<? super T> predicate, @NotNull Iterable<? extends T> collection) {
        final ArrayList<T> mapped = new ArrayList<>();
        for (T element : collection) {
            if (predicate.apply(element))
                break;
            mapped.add(element);
        }

        return mapped;
    }

    /**
     * Applies accumulate function to each element from begin starting with given initial value.
     *
     * @param function     to apply.
     * @param initialValue starting value.
     * @param collection   elements.
     * @param <T>          type of initial value and type of return value
     * @return accumulated value
     */
    static public <T> T foldr(@NotNull Function2<? super T, ? super T, ? extends T> function,
                              T initialValue, @NotNull Collection<? extends T> collection) {
        for (T element : collection) {
            initialValue = function.apply(initialValue, element);
        }

        return initialValue;
    }

    /**
     * Applies accumulate function to each element from end starting with given initial value.
     *
     * @param function     to apply.
     * @param initialValue starting value.
     * @param collection   elements.
     * @param <T>          type of initial value and type of return value
     * @return accumulated value
     */
    static public <T> T foldl(@NotNull Function2<? super T, ? super T, ? extends T> function,
                              T initialValue, @NotNull Collection<? extends T> collection) {
        return foldl(function, initialValue, collection.iterator());
    }

    static private <T> T foldl(@NotNull Function2<? super T, ? super T, ? extends T> function,
                               T initialValue, @NotNull Iterator<? extends T> collectionIterator) {
        if (!collectionIterator.hasNext())
            return initialValue;

        final T value = collectionIterator.next();
        return function.apply(value, foldl(function, initialValue, collectionIterator));
    }
}
