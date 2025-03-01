package com.example.AutoEcole.bll.exception.alreadyExist;

import com.example.AutoEcole.bll.exception.AutoEcoleException;

public class AlreadyExistException extends AutoEcoleException {

    public AlreadyExistException(String message){
        super(message, 409);
    }

    public AlreadyExistException(String message, int status) {
       super(message, status);
       //
    }

}
