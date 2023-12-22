package com.cac.repository;

public class RepositorioException extends RuntimeException {

    public RepositorioException(String message) {
        super(message);
    }

    public RepositorioException(String message, Throwable cause) {
        super(message, cause);
    }
}
