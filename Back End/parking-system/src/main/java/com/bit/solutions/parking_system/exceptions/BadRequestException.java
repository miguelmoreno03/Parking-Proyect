package com.bit.solutions.parking_system.exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) { super(message);}
}