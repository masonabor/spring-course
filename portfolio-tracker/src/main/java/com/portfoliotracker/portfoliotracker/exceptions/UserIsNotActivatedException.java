package com.portfoliotracker.portfoliotracker.exceptions;

public class UserIsNotActivatedException extends Exception {
    public UserIsNotActivatedException() {
    }
    public UserIsNotActivatedException(String message) {
        super(message);
    }
}
