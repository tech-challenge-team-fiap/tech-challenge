package br.com.fiap.techchallenge.adapter.driven.entities.useCase;

import br.com.fiap.techchallenge.adapter.driven.entities.Client;
import br.com.fiap.techchallenge.adapter.driven.entities.form.ClientFormDto;
import br.com.fiap.techchallenge.common.validation.CpfValidator;
import br.com.fiap.techchallenge.common.exception.InvalidInputException;
import br.com.fiap.techchallenge.common.validation.EmailValidator;
import br.com.fiap.techchallenge.infrastructure.gateway.ClientGateway;
import br.com.fiap.techchallenge.infrastructure.out.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegisterNewClientUseCase {

    private static final Logger logger = LoggerFactory.getLogger(RegisterNewClientUseCase.class);
    private final ClientGateway clientGateway;
    private final ClientRepository clientRepository;

    @Autowired
    public RegisterNewClientUseCase(ClientGateway clientGateway, ClientRepository clientRepository) {
        this.clientGateway = clientGateway;
        this.clientRepository = clientRepository;
    }

    public ResponseEntity<Integer> register(final ClientFormDto clientFormDto) {

        validateCpf(clientFormDto.getCpf());
        validateEmail(clientFormDto.getEmail());
        checkIfClientAlreadyExists(clientFormDto.getCpf());

        Client client = new Client(clientFormDto);
        return clientGateway.register(client);
    }

    private void validateCpf(String cpf) {
        if (!CpfValidator.isValidCpf(cpf)) {
            String message = String.format("Invalid CPF: %s", cpf);
            logger.info(message);
            throw new InvalidInputException(message);
        }
    }

    private void validateEmail(String email) {
        if (!EmailValidator.isValidEmail(email)) {
            String message = String.format("Invalid Email: %s", email);
            logger.info(message);
            throw new InvalidInputException(message);
        }
    }

    private void checkIfClientAlreadyExists(String cpf) {
        clientRepository.findByCpf(cpf).ifPresent(client -> {
            String message = String.format("Client with CPF %s already has a registration!", cpf);
            logger.info(message);
            throw new InvalidInputException(message);
        });
    }
}
