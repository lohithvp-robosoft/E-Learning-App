package com.robosoft.elearning.dto.response;

public class ResponseDTO<T> {

    private int status;
    private int code;
    private String message;
    private T data;

    public ResponseDTO(int status, int code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }
    ResponseDTO(){}

    public ResponseDTO(String lessonLikedSuccessfully) {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
