package com.testing.pack.Exception;

import com.testing.pack.Response.ErrorResponse;

import java.io.Serial;

public class ValidationErrorException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1;

    public ValidationErrorException(String message){
        super(message);
    }

    public ValidationErrorException(ErrorResponse errors){
        super(String.valueOf(errors));
    }
}
