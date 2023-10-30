package br.com.fiap.techchallenge.common.exception;

import lombok.Getter;

@Getter
public abstract class InvalidProcessException extends Exception {

    private final String tittle;
    private final String message;

    public InvalidProcessException (String tittle, String message) {
        this.tittle = tittle;
        this.message = message;
    }
}
