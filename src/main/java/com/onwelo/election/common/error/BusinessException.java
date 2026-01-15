package com.onwelo.election.common.error;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}