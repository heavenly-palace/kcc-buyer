package com.kcc.buyer.common;

public class ResponseEntity<T> {

    private int statusCode;

    private String message;

    private T data;

    public ResponseEntity() {
    }

    public ResponseEntity(int statusCode, String message) {
        super();
        this.statusCode = statusCode;
        this.message = message;
    }

    public ResponseEntity(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public ResponseEntity(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public Object getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
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

    public static ResponseEntity jsonError() {
        return new ResponseEntity(
                BaseStatus.ISNULL.getCode(),
                BaseStatus.ISNULL.getMessage());
    }

//	public ResponseEntity putData(String key, Object value) {
//		data.put(key, value);
//		return this;
//	}

}
