package com.mikhail.pravilov.mit.lightFuture;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public interface LightFuture<R> {
    /**
     * Checks whether computation is finished.
     *
     * @return true if finished, otherwise false.
     */
    boolean isReady();

    /**
     * Getter of calculated value. If it is not calculated yet, method should wait for computation finish.
     *
     * @return calculated value.
     * @throws LightExecutionException if exception occurred during computation.
     */
    @Nullable
    R get() throws LightExecutionException;

    /**
     * Waits for calculating of computation representing by this instance and then adds new task to pool by valueOfNextSupplier function.
     *
     * @param valueOfNextSupplier function that describes how to get value for next supplier.
     * @return created task.
     */
    @NotNull
    LightFuture<R> thenApply(@NotNull Function<? super R, ? extends R> valueOfNextSupplier);
}
