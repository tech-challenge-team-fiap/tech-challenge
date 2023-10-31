package br.com.fiap.techchallenge.common.exception.order;

import br.com.fiap.techchallenge.common.exception.InvalidProcessException;

public abstract class InvalidOrderProcessException extends InvalidProcessException {

    public InvalidOrderProcessException(String tittle, String message) {
        super(tittle, message);
    }
}
