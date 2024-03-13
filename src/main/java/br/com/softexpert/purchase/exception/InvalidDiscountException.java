package br.com.softexpert.purchase.exception;

public class InvalidDiscountException extends RuntimeException{
    public InvalidDiscountException(String msg) {
        super(msg);
    }
}
