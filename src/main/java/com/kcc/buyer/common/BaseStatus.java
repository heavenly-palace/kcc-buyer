package com.kcc.buyer.common;

public enum BaseStatus {

    SUCCESS(200, "success"),
    ISNULL(400,"json does not match");

    private Integer code;
    private String message;

    BaseStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
