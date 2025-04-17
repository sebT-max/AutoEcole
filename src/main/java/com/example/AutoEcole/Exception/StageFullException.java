package com.example.AutoEcole.Exception;

public class StageFullException extends RuntimeException {
    public StageFullException() {
        super("Le stage est déjà complet.");
    }
}
