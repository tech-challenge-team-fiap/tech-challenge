package br.com.fiap.techchallenge.adapter.driven.entities.useCase.client;

import br.com.fiap.techchallenge.adapter.driven.entities.form.ClientFormDto;
import br.com.fiap.techchallenge.common.exception.BaseException;
import br.com.fiap.techchallenge.common.exception.client.ClientNotFoundException;
import br.com.fiap.techchallenge.common.exception.client.InvalidClientProcessException;
import br.com.fiap.techchallenge.infrastructure.gateway.ClientGateway;
import br.com.fiap.techchallenge.infrastructure.out.ClientRepository;
import br.com.fiap.techchallenge.infrastructure.repository.ClientRepositoryDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EditClientUseCase extends AbstractClientUseCase {

    private static final Logger logger = LoggerFactory.getLogger(EditClientUseCase.class);
    private final ClientGateway clientGateway;
    private final ClientRepository clientRepository;

    @Autowired
    public EditClientUseCase(ClientGateway clientGateway, ClientRepository clientRepository) {
        super(logger, clientRepository);
        this.clientGateway = clientGateway;
        this.clientRepository = clientRepository;
    }

    public ResponseEntity<Integer> edit(final ClientFormDto clientFormDto) throws InvalidClientProcessException {
        ClientRepositoryDb existingClient = clientRepository.findByCpf(clientFormDto.getCpf())
                .orElseThrow(() -> new ClientNotFoundException("Client with CPF " + clientFormDto.getCpf() + " does not exist!"));

        validateEmail(clientFormDto.getEmail());
        validatePhoneNumber(clientFormDto.getPhone());

        existingClient.updateFromDto(clientFormDto);

        return clientGateway.update(existingClient);
    }
}