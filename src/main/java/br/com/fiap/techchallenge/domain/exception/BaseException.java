package br.com.fiap.techchallenge.domain.exception;

public class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }
}