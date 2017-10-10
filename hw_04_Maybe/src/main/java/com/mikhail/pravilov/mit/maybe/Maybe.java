package com.mikhail.pravilov.mit.maybe;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Optional<T>-like class
 * @param <T> type of stored value
 */
public class Maybe<T> {
    private T storedValue;

    private Maybe(T storedValue) {
        this.storedValue = storedValue;
    }

    /**
     * Method creates new instance of Maybe with not null stored value.
     * @param t value to store in new instance.
     * @param <T> type of stored value.
     * @return new instance of Maybe that stores given value.
     */
    @NotNull
    public static <T> Maybe<T> just(@NotNull T t) {
        return new Maybe<>(t);
    }

    /**
     * Method creates new instance of Maybe with null stored value.
     * @param <T> type of stored value.
     * @return new instance of Maybe that stores null.
     */
    @NotNull
    public static <T> Maybe<T> nothing() {
        return new Maybe<>(null);
    }

    /**
     * Method to get stored value.
     * @return stored value if not null.
     * @throws NullStoredValueException if stored value is null.
     */
    @NotNull
    public T get() throws NullStoredValueException {
        if (storedValue == null)
            throw new NullStoredValueException("null stored value");
        return storedValue;
    }

    /**
     * Checks if stored value is not null.
     * @return true if stored value is not null otherwise false.
     */
    @NotNull
    public boolean isPresent() {
        return storedValue != null;
    }

    /**
     * Applies given function to stored value if not null.
     * @param mapper function to apply.
     * @param <U> type of new stored value, return type of mapper function.
     * @return new instance of Maybe with new stored value if stored value wasn't null otherwise Maybe that stores null.
     */
    @NotNull
    public <U> Maybe<U> map(@NotNull Function<T, U> mapper) {
        if (isPresent())
            return new Maybe<>(mapper.apply(storedValue));
        else
            return new Maybe<>(null);
    }

}
