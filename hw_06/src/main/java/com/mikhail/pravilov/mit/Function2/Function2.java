package com.mikhail.pravilov.mit.Function2;

import com.mikhail.pravilov.mit.Function1.Function1;

/**
 * Interface of function from 2 arguments.
 *
 * @param <T> type of first argument.
 * @param <U> type of second argument.
 * @param <R> type of return value.
 */
public interface Function2<T, U, R> {
    R apply(T t, U u);

    /**
     * Method to create composition: after(this(x, y)).
     *
     * @param after function to apply after.
     * @param <V>   return type of after.
     * @return composition of functions.
     */
    default <V> Function2<T, U, V> compose(Function1<? super R, ? extends V> after) {
        return (t, u) -> after.apply(this.apply(t, u));
    }

    /**
     * Method to bind 1st parameter of function.
     *
     * @param firstParameter value of 1st argument to bind.
     * @return function from 1 arguments that waits for the second argument.
     */
    default Function1<U, R> bind1(T firstParameter) {
        return (u) -> apply(firstParameter, u);
    }

    /**
     * Method to bind 2nd parameter of function.
     *
     * @param secondParameter value of 2nd argument to bind.
     * @return function from 1 arguments that waits for the first argument.
     */
    default Function1<T, R> bind2(U secondParameter) {
        return (t) -> apply(t, secondParameter);
    }

    /**
     * Method to curry function, second parameter will be bind.
     *
     * @param parameter parameter to set.
     * @return function from 1 argument(1st argument of Function2).
     */
    default Function1<T, R> curry(U parameter) {
        return bind2(parameter);
    }
}
