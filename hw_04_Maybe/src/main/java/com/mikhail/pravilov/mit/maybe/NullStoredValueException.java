package com.mikhail.pravilov.mit.maybe;

/**
 * Class for exception occurred during Maybe.get method because Maybe.storedValue is null.
 * Class and all methods are package-private.
 */
class NullStoredValueException extends Exception {
    NullStoredValueException(String message) {
        super(message);
    }

    NullStoredValueException(String message, Throwable cause) {
        super(message, cause);
    }

    NullStoredValueException(Throwable cause) {
        super(cause);
    }

    NullStoredValueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    NullStoredValueException() {
    }
}
