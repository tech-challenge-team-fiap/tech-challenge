package br.com.fiap.techchallenge.common.exception.client;

import br.com.fiap.techchallenge.common.exception.InvalidProcessException;

public abstract class InvalidClientProcessException extends InvalidProcessException {

    public InvalidClientProcessException(String tittle, String message) {
        super(tittle, message);
    }
}
