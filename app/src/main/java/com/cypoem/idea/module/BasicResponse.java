package com.cypoem.idea.module;

/**
 *
 */
public class BasicResponse<T> {

    private int code;
    private String message;
    private String errMsg;

    private boolean error;

    private T content;

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

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
