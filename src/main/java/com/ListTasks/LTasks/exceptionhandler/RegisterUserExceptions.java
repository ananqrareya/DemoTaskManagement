package com.ListTasks.LTasks.exceptionhandler;

public class RegisterUserExceptions extends RuntimeException{
    public RegisterUserExceptions() {
    }

    public RegisterUserExceptions(String message) {
        super(message);
    }

    public RegisterUserExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
