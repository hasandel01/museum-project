package com.bakelor.museum.exceptions;


public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(String message) {
        super(message);
    }


}