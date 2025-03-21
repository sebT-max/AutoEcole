package com.example.AutoEcole.bll.exception;

public class AutoEcoleException extends RuntimeException{
    private int status;
    private Object message;

    public AutoEcoleException(String message){
        super(message);
        this.message = message;
        this.status = 500;
    }

    public AutoEcoleException(String message, int status){
        super(message);
        this.message = message;
        this.status = status;
    }

    public String getMessage(){
        return message.toString();
    }

    public int getSatus() {
        return status;
    }

    @Override
    public String toString(){
        StackTraceElement elem = Thread.currentThread().getStackTrace()[0];
        return String.format("%s throw in %s at %s: %d with message : %s",
                this.getClass().getSimpleName(),
                elem.getMethodName(),
                elem.getFileName(),
                elem.getLineNumber(),
                this.getMessage()
        );
    }

}
