package br.com.fiap.techchallenge.common.exception.client;

public class ClientNotFoundException extends InvalidClientProcessException{
    private static final String tittle = "Client not found";
    private static final String message = "The client with CPF %s not found";

    public ClientNotFoundException(String cpf) {
        super(tittle, String.format(message, cpf));
    }
}
