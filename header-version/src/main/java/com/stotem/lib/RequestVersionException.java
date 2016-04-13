package com.stotem.lib;

/**
 * Created by JianjunWu on 16/4/12.
 */
public class RequestVersionException extends Exception {
    private int code;

    public RequestVersionException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
