package br.com.fiap.techchallenge.common.exception.products;

import br.com.fiap.techchallenge.common.exception.InvalidProcessException;

public abstract class InvalidProductsProcessException extends InvalidProcessException {

    public InvalidProductsProcessException(String tittle, String message) {
        super(tittle, message);
    }
}
