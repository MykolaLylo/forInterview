package com.demo_books_shop.exception;

import lombok.experimental.StandardException;

@StandardException
public class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
