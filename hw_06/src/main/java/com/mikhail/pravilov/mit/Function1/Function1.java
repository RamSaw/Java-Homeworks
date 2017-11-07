package com.mikhail.pravilov.mit.Function1;

/**
 * Interface of function from one argument.
 *
 * @param <T> argument type.
 * @param <R> return type.
 */
public interface Function1<T, R> {
    /**
     * Method to calculate function from given argument.
     *
     * @param t argument.
     * @return calculated value.
     */
    R apply(T t);

    /**
     * Method to create composition: after(this(x)).
     *
     * @param after function to apply after.
     * @param <V>   return type of after.
     * @return composition of functions.
     */
    default <V> Function1<T, V> compose(Function1<? super R, ? extends V> after) {
        return (t) -> after.apply(this.apply(t));
    }
}
