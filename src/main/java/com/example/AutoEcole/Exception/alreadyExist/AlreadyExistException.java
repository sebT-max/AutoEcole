package com.example.AutoEcole.Exception.alreadyExist;

import com.example.AutoEcole.Exception.AutoEcoleException;

public class AlreadyExistException extends AutoEcoleException {

    public AlreadyExistException(String message){
        super(message, 409);
    }

    public AlreadyExistException(String message, int status) {
       super(message, status);
       //
    }

}
