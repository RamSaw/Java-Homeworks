package com.mikhail.pravilov.mit.lightFuture;

import java.util.concurrent.ExecutionException;

/**
 * Exception that occurs when {@link LightFuture#get()} runs and meet exception in supplier.
 */
public class LightExecutionException extends ExecutionException {
    /**
     * Default constructor from {@link Throwable}.
     *
     * @param cause of exception.
     */
    public LightExecutionException(Throwable cause) {
        super(cause);
    }
}
