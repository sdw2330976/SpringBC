package com.sdw.soft.exception;

public class BCException extends RuntimeException {

    private int code;

    public BCException(String message, int code) {
        super(message);
        this.code = code;
    }

    public BCException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public BCException(String message) {
        super(message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
