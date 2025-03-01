package com.example.AutoEcole.bll.exception.ressourceNotFound;

import com.example.AutoEcole.bll.exception.AutoEcoleException;

public class RessourceNotFoundException extends AutoEcoleException {

    public RessourceNotFoundException(String message){
        super(message, 404);
        //
    }

    public RessourceNotFoundException(String message, int status){
        super(message, status);
        //
    }

}
