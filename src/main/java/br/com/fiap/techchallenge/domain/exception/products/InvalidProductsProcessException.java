package br.com.fiap.techchallenge.domain.exception.products;

import br.com.fiap.techchallenge.domain.exception.InvalidProcessException;

public abstract class InvalidProductsProcessException extends InvalidProcessException {

    public InvalidProductsProcessException(String tittle, String message) {
        super(tittle, message);
    }
}
