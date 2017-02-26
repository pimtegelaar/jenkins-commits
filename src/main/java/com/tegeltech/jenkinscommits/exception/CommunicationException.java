package com.tegeltech.jenkinscommits.exception;

/**
 * Indicates that somthing went wrong during an HTTP request.
 */
public class CommunicationException extends RuntimeException {

    public CommunicationException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
