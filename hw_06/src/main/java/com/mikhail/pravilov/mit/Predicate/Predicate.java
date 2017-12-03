package com.mikhail.pravilov.mit.Predicate;

import com.mikhail.pravilov.mit.Function1.Function1;

/**
 * Interface of predicate: argument -> bool.
 * @param <T> type of argument.
 */
public interface Predicate <T> extends Function1<T, Boolean> {
    Predicate ALWAYS_TRUE = (t) -> true;
    Predicate ALWAYS_FALSE = (t) -> false;

    /**
     * Method to get or of two predicates.
     * @param other predicate.
     * @return or predicate.
     */
    default Predicate<T> or(Predicate<? super T> other) {
        return (t) -> this.apply(t) || other.apply(t);
    }

    /**
     * Method to get and of two predicates.
     * @param other predicate.
     * @return and predicate.
     */
    default Predicate<T> and(Predicate<? super T> other) {
        return (t) -> this.apply(t) && other.apply(t);
    }

    /**
     * Method to get not of predicate.
     * @return not predicate.
     */
    default Predicate<T> not() {
        return (t) -> !this.apply(t);
    }
}
