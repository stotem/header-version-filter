package com.stotem.lib;

/**
 * Created by JianjunWu on 16/4/12.
 */
public class RequestVersionException extends Exception {
    private int code;

    private String backupURI;

    public RequestVersionException() {
        super();
    }

    public RequestVersionException(int code, String message) {
        super(message);
        this.setCode(code);
    }

    public RequestVersionException(int code, String message, String backupURI) {
        this(code, message);
        this.setBackupURI(backupURI);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getBackupURI() {
        return backupURI;
    }

    public void setBackupURI(String backupURI) {
        this.backupURI = !Tools.isEmpty(backupURI) ? backupURI.trim():null;
    }


}
