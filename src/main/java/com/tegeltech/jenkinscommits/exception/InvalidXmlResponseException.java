package com.tegeltech.jenkinscommits.exception;

public class InvalidXmlResponseException extends RuntimeException {

    public InvalidXmlResponseException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
