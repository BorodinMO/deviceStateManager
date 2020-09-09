package com.iot.dsm.exception;

public class WashMachineNotFoundException extends RuntimeException{

    private static final String MESSAGE = "Wash machine was not found!";

    public WashMachineNotFoundException() {
        super(MESSAGE);
    }

}
