package com.kimslog.exception;

public abstract class KimslogException extends RuntimeException{

    public KimslogException(String message) {
        super(message);
    }

    public KimslogException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();
}
