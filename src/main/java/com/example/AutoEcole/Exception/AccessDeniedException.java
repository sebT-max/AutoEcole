package com.example.AutoEcole.Exception;

public AccessDeniedException() {
    super("Accès refusé.");
}

public AccessDeniedException(String message) {
    super(message);
}

public AccessDeniedException(String message, Throwable cause) {
    super(message, cause);
}

public AccessDeniedException(Throwable cause) {
    super(cause);
}
}