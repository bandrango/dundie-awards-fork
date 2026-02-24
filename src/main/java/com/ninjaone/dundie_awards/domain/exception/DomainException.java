package com.ninjaone.dundie_awards.domain.exception;

/**
 * Base exception for domain-level errors.
 * Independent of any framework.
 */
public class DomainException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
