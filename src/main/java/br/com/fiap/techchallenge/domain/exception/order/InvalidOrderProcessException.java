package br.com.fiap.techchallenge.domain.exception.order;

import br.com.fiap.techchallenge.domain.exception.InvalidProcessException;

public abstract class InvalidOrderProcessException extends InvalidProcessException {

    public InvalidOrderProcessException(String tittle, String message) {
        super(tittle, message);
    }
}
