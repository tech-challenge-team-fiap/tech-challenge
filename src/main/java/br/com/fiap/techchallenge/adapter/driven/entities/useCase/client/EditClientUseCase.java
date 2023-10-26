package br.com.fiap.techchallenge.adapter.driven.entities.useCase.client;

import br.com.fiap.techchallenge.adapter.driven.entities.form.ClientFormDto;
import br.com.fiap.techchallenge.common.exception.BaseException;
import br.com.fiap.techchallenge.infrastructure.gateway.ClientGateway;
import br.com.fiap.techchallenge.infrastructure.out.ClientRepository;
import br.com.fiap.techchallenge.infrastructure.repository.ClientRepositoryDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EditClientUseCase {

    private static final Logger logger = LoggerFactory.getLogger(EditClientUseCase.class);
    private final ClientGateway clientGateway;
    private final ClientRepository clientRepository;

    @Autowired
    public EditClientUseCase(ClientGateway clientGateway, ClientRepository clientRepository) {
        this.clientGateway = clientGateway;
        this.clientRepository = clientRepository;
    }

    public ResponseEntity<Integer> edit(final ClientFormDto clientFormDto) {
        ClientRepositoryDb existingClient = clientRepository.findByCpf(clientFormDto.getCpf())
                .orElseThrow(() -> new BaseException("Client with CPF " + clientFormDto.getCpf() + " does not exist!"));

        existingClient.updateFromDto(clientFormDto);

        return clientGateway.update(existingClient);
    }
}