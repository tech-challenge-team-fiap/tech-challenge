package br.com.fiap.techchallenge.useCase.client;

import br.com.fiap.techchallenge.db.dto.client.ClientDto;
import br.com.fiap.techchallenge.common.exception.client.ClientNotFoundException;
import br.com.fiap.techchallenge.common.exception.client.InvalidClientProcessException;
import br.com.fiap.techchallenge.gateway.ClientGateway;
import br.com.fiap.techchallenge.infrastructure.repository.ClientRepositoryDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EditClientUseCase extends AbstractClientUseCase {
    private static final Logger logger = LoggerFactory.getLogger(EditClientUseCase.class);
    private final ClientGateway clientGateway;

    @Autowired
    public EditClientUseCase(ClientGateway clientGateway) {
        super(logger);
        this.clientGateway = clientGateway;
    }

    public ClientDto edit(final ClientDto clientDto) throws InvalidClientProcessException {
        validateEmail(clientDto.getEmail());
        validatePhoneNumber(clientDto.getPhone());

        return clientGateway.update(clientDto);
    }
}