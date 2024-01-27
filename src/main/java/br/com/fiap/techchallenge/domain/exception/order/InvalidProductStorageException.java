package br.com.fiap.techchallenge.domain.exception.order;

import java.util.UUID;

public class InvalidProductStorageException extends InvalidOrderProcessException {
    private static final String tittle = "Invalid product storage";
    private static final String message = "The product %s does not have storage";

    public InvalidProductStorageException(UUID id) {
        super(tittle, String.format(message, id));
    }
}
