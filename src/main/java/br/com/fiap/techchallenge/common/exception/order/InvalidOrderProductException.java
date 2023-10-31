package br.com.fiap.techchallenge.common.exception.order;

public class InvalidOrderProductException extends InvalidOrderProcessException {
    private static final String tittle = "Invalid order product";
    private static final String message = "No product specified to place the order!";

    public InvalidOrderProductException() {
        super(tittle, message);
    }
}
