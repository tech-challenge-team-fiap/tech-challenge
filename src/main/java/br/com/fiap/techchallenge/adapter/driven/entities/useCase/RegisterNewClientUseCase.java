package br.com.fiap.techchallenge.adapter.driven.entities.useCase;

import br.com.fiap.techchallenge.adapter.driven.entities.Client;
import br.com.fiap.techchallenge.adapter.driven.entities.form.ClientFormDto;
import br.com.fiap.techchallenge.common.CpfValidator;
import br.com.fiap.techchallenge.common.exception.ClientAlreadyExistsException;
import br.com.fiap.techchallenge.common.exception.InvalidCpfException;
import br.com.fiap.techchallenge.infrastructure.gateway.ClientGateway;
import br.com.fiap.techchallenge.infrastructure.out.ClientRepository;
import br.com.fiap.techchallenge.infrastructure.repository.ClientRepositoryDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterNewClientUseCase {

    private static final Logger logger = LoggerFactory.getLogger(RegisterNewClientUseCase.class);
    private final ClientGateway clientGateway;
    private final ClientRepository clientRepository;
    private final CpfValidator cpfValidator = new CpfValidator();

    @Autowired
    public RegisterNewClientUseCase(ClientGateway clientGateway, ClientRepository clientRepository) {
        this.clientGateway = clientGateway;
        this.clientRepository = clientRepository;
    }

    public ResponseEntity<Integer> register(final ClientFormDto clientFormDto) {

        final String cpf = clientFormDto.getCpf();

        if (!CpfValidator.isValidCpf(cpf)) {
            String message = String.format("Invalid CPF: %s", cpf);
            logger.info(message);
            throw new InvalidCpfException(message);
        }

        var optionalCliente = clientRepository.findByCpf(cpf);
        optionalCliente.ifPresent(client -> {
            String message = String.format("Client with CPF %s already has a registration!", cpf);
            logger.info(message);
            throw new ClientAlreadyExistsException(message);
        });

        Client client = new Client(clientFormDto);
        return clientGateway.register(client);
    }
}
