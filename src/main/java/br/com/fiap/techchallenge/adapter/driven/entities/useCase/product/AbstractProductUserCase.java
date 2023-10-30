package br.com.fiap.techchallenge.adapter.driven.entities.useCase.product;

import br.com.fiap.techchallenge.common.exception.products.InvalidQuantityException;

public abstract class AbstractProductUserCase {
    protected void validateQuantity(Integer quantity) throws InvalidQuantityException {
        if (quantity == 0) {
            throw new InvalidQuantityException(quantity);
        }
    }

}
