package com.rhyno.jpasample.mattermost.exception;

public class MattermostApiException extends RuntimeException {
    public MattermostApiException(String errorMessage) {
        super(errorMessage);
    }
}
