package br.com.fiap.techchallenge.useCase.product;

import br.com.fiap.techchallenge.common.exception.products.InvalidPriceException;
import br.com.fiap.techchallenge.common.exception.products.InvalidQuantityException;
import java.math.BigDecimal;

public abstract class AbstractProductUserCase {
    protected void validateQuantity(Integer quantity) throws InvalidQuantityException {
        if (quantity <= 0) {
            throw new InvalidQuantityException(quantity);
        }
    }

    protected void validatePrice(BigDecimal price) throws InvalidPriceException {
        if (price.signum() <= 0) {
            throw new InvalidPriceException(price);
        }
    }

}
