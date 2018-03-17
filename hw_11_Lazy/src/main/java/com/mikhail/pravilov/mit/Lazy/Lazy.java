package com.mikhail.pravilov.mit.Lazy;

/**
 * Interface of lazy computation.
 *
 * @param <T> type of calculated value.
 */
public interface Lazy<T> {
    /**
     * Lazy getter of value. Runs computation from supplier first time it asked, then always return calculated value.
     *
     * @return calculated value.
     */
    T get();
}