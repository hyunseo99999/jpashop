package com.jpa.jpashop.exception;

public class NotEnorghStockExcpetion extends RuntimeException {

    public NotEnorghStockExcpetion() {
        super();
    }

    public NotEnorghStockExcpetion(String message) {
        super(message);
    }

    public NotEnorghStockExcpetion(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnorghStockExcpetion(Throwable cause) {
        super(cause);
    }

    protected NotEnorghStockExcpetion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
