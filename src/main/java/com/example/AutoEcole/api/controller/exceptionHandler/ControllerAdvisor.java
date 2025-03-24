package com.example.AutoEcole.api.controller.exceptionHandler;

import com.example.AutoEcole.Exception.AutoEcoleException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ControllerAdvisor {
    @ExceptionHandler(AutoEcoleException.class)
    public ResponseEntity<Object> handleAutoEcoleException(AutoEcoleException ex){
        return ResponseEntity.status(ex.getSatus()).body(ex.getMessage());
    }

}
