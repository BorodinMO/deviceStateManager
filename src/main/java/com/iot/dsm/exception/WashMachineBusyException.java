package com.iot.dsm.exception;

public class WashMachineBusyException extends RuntimeException {

    private static final String MESSAGE = "Wash machine is still working.";

    public WashMachineBusyException() {
        super(MESSAGE);
    }

}
