package com.ListTasks.LTasks.exceptionhandler;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDetails {
    private String status;
    private String message;
    private Date date;

    public ErrorDetails() {

    }

    public ErrorDetails(String status, String message, Date date) {
        this.status = status;
        this.message = message;
        this.date = date;
    }
}
