package br.com.fiap.techchallenge.useCase.client;

import br.com.fiap.techchallenge.common.exception.client.ClientAlreadyExistsException;
import br.com.fiap.techchallenge.common.exception.client.ClientNotFoundException;
import br.com.fiap.techchallenge.db.dto.client.ClientFormDto;
import br.com.fiap.techchallenge.entities.Client;
import br.com.fiap.techchallenge.db.dto.client.ClientDto;
import br.com.fiap.techchallenge.common.exception.client.InvalidClientProcessException;
import br.com.fiap.techchallenge.gateway.ClientGateway;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegisterNewClientUseCase extends AbstractClientUseCase {

    private static final Logger logger = LoggerFactory.getLogger(RegisterNewClientUseCase.class);
    private final ClientGateway clientGateway;

    @Autowired
    public RegisterNewClientUseCase(ClientGateway clientGateway) {
        super(logger);
        this.clientGateway = clientGateway;
    }

    public ClientDto register(final ClientDto clientDto) throws InvalidClientProcessException {
        validateCpf(clientDto.getCpf());
        validateEmail(clientDto.getEmail());
        validatePhoneNumber(clientDto.getPhone());
        checkIfClientAlreadyExists(clientDto.getCpf());

        return clientGateway.register(new Client(clientDto));
    }

    private void checkIfClientAlreadyExists(String cpf) throws InvalidClientProcessException {
        this.clientGateway.findByCpf(cpf);
    }
}
