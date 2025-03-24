package com.example.AutoEcole.Exception.ressourceNotFound;

import com.example.AutoEcole.Exception.AutoEcoleException;

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
