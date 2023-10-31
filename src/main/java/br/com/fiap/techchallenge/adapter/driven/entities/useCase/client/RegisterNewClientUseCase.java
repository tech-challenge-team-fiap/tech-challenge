package br.com.fiap.techchallenge.adapter.driven.entities.useCase.client;

import br.com.fiap.techchallenge.adapter.driven.entities.Client;
import br.com.fiap.techchallenge.adapter.driven.entities.form.ClientFormDto;
import br.com.fiap.techchallenge.common.exception.client.ClientAlreadyExistsException;
import br.com.fiap.techchallenge.common.exception.client.InvalidClientProcessException;
import br.com.fiap.techchallenge.common.exception.client.InvalidCpfException;
import br.com.fiap.techchallenge.common.exception.client.InvalidEmailException;
import br.com.fiap.techchallenge.common.exception.client.InvalidPhoneNumberException;
import br.com.fiap.techchallenge.common.validation.CpfValidator;
import br.com.fiap.techchallenge.common.exception.BaseException;
import br.com.fiap.techchallenge.common.validation.EmailValidator;
import br.com.fiap.techchallenge.common.validation.PhoneNumberValidator;
import br.com.fiap.techchallenge.infrastructure.gateway.ClientGateway;
import br.com.fiap.techchallenge.infrastructure.out.ClientRepository;
import br.com.fiap.techchallenge.infrastructure.repository.ClientRepositoryDb;
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
    public RegisterNewClientUseCase(ClientGateway clientGateway, ClientRepository clientRepository) {
        super(logger, clientRepository);
        this.clientGateway = clientGateway;
    }

    public ResponseEntity<UUID> register(final ClientFormDto clientFormDto) throws InvalidClientProcessException {

        validateCpf(clientFormDto.getCpf());
        validateEmail(clientFormDto.getEmail());
        checkIfClientAlreadyExists(clientFormDto.getCpf());
        validatePhoneNumber(clientFormDto.getPhone());


        Client client = new Client(clientFormDto);
        return clientGateway.register(client);
    }
}
