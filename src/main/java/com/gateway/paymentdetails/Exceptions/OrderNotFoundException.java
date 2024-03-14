package com.gateway.paymentdetails.Exceptions;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String message)
    {
        super(message);
    }
}
