package ru.dzhiblavi.sd.rxshop.exception;

public class HttpException extends Exception {
    private final int code;

    public HttpException(final int code, final String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
