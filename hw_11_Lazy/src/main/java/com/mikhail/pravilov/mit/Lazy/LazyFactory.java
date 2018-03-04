package com.mikhail.pravilov.mit.Lazy;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * Factory that can create multithreading and one-threading {@link Lazy} objects.
 * Lazy objects are suppliers, but calculate a value only when it is asked for and only once (returns old calculated value).
 */
public class LazyFactory {
    /**
     * Creates one-threading {@link Lazy} object.
     *
     * @param supplier where the value will be taken.
     * @param <T>      type of value.
     * @return {@link Lazy} object.
     */
    @NotNull
    public static <T> Lazy<T> createAsynchronizedLazy(@NotNull Supplier<T> supplier) {
        return new Lazy<T>() {
            @Nullable
            private T result;
            private boolean isCalculated = false;

            @Override
            @Nullable
            public T get() {
                if (!isCalculated) {
                    result = supplier.get();
                    isCalculated = true;
                }

                return result;
            }
        };
    }

    /**
     * Creates multithreading {@link Lazy} object.
     *
     * @param supplier where the value will be taken.
     * @param <T>      type of value.
     * @return {@link Lazy} object.
     */
    @NotNull
    public static <T> Lazy<T> createSynchronizedLazy(@NotNull Supplier<T> supplier) {
        return new Lazy<T>() {
            @Nullable
            private T result; // no need in volatile because after sync all is written in general memory
            private boolean isCalculated = false; // no need in volatile because after sync all is written in general memory

            @Override
            @Nullable
            public T get() {
                if (!isCalculated) {
                    synchronized (this) {
                        if (!isCalculated) {
                            result = supplier.get();
                            isCalculated = true;
                        } else {
                            return result;
                        }
                    }
                }

                return result;
            }
        };
    }
}
