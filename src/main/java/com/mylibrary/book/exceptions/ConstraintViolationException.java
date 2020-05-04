package com.mylibrary.book.exceptions;

/**
 * This exception should be thrown whenever integrity violation is happening.
 * Example:
 * DB Integrity Violation Exception
 * Hibernate Violation Exception
 * Business Logic Violation Exception
 *
 * @author Vadym Lotar
 * @see RuntimeException
 */
@SuppressWarnings("unused")
public class ConstraintViolationException extends RuntimeException {

    /**
     * Instantiates a new Constraint violation exception.
     *
     * @param message the message
     */
    public ConstraintViolationException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Constraint violation exception.
     *
     * @param message the message
     * @param cause   the cause {@link Throwable}
     */
    public ConstraintViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
