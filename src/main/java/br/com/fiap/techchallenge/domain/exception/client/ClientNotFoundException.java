package br.com.fiap.techchallenge.domain.exception.client;

public class ClientNotFoundException extends InvalidClientProcessException{
    private static final String tittle = "Client not found";
    private static final String message = "The client %s not found";

    public ClientNotFoundException(String cpf) {
        super(tittle, String.format(message, cpf));
    }
}
