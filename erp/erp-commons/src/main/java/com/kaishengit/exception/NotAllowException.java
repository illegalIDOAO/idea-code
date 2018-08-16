package com.kaishengit.exception;

import java.io.Serializable;

/**
 * @Author: chuzhaohui
 * @Date: Created in 15:40 2018/7/24
 */
public class NotAllowException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public NotAllowException() {
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public NotAllowException(String message) {
        super(message);
    }
}
