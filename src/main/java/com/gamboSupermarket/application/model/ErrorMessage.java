package com.gamboSupermarket.application.model;

public class ErrorMessage {
    String message;
    String details;

    public ErrorMessage(){}

    public ErrorMessage(String message, String details) {
        super();
        this.message=message;
        this.details=details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
