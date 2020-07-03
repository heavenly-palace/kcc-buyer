package com.kcc.buyer.common;

public class ResponseEntity {

    private int statusCode;

    private String message;

    private Object data;

    public ResponseEntity() {
    }

    public ResponseEntity(int statusCode, String message) {
        super();
        this.statusCode = statusCode;
        this.message = message;
    }

    public ResponseEntity(int statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public ResponseEntity(int statusCode, Object data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public Object getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public ResponseEntity addMessage(String message){
        this.message = message;
        return this;
    }

    public static ResponseEntity ok() {
        return new ResponseEntity(
                BaseStatus.SUCCESS.getCode(),
                BaseStatus.SUCCESS.getMessage());
    }

    public static ResponseEntity ok(Object data) {
        return new ResponseEntity(
                BaseStatus.SUCCESS.getCode(),
                BaseStatus.SUCCESS.getMessage(),
                data);
    }

    public static ResponseEntity ok(String message) {
        return new ResponseEntity(
                BaseStatus.SUCCESS.getCode(),
                message);
    }

    public static ResponseEntity jsonError() {
        return new ResponseEntity(
                BaseStatus.ISNULL.getCode(),
                BaseStatus.ISNULL.getMessage());
    }

    public static ResponseEntity jsonError(String message) {
        return new ResponseEntity(
                BaseStatus.ISNULL.getCode(),
                message);
    }

//	public ResponseEntity putData(String key, Object value) {
//		data.put(key, value);
//		return this;
//	}

}
