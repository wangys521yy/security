package com.waysli.tools.security.demo.converter;

public class CommonException extends RuntimeException {

    /**
     * @param message
     */
    public CommonException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public CommonException(Throwable cause) {
        super(cause);
    }
}
