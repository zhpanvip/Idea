package com.cypoem.idea.module;

/**
 *
 */
public class BasicResponse {

    private int code;
    private String message;
    private String errMsg;

    public String getErrMsg() {
        return errMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
