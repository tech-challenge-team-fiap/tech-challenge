package br.com.fiap.techchallenge.adapter.driven.entities.useCase.client;

import br.com.fiap.techchallenge.common.exception.BaseException;
import br.com.fiap.techchallenge.common.exception.client.ClientNotFoundException;
import br.com.fiap.techchallenge.common.exception.client.InvalidClientProcessException;
import br.com.fiap.techchallenge.common.utils.ValidCPF;
import br.com.fiap.techchallenge.infrastructure.gateway.ClientGateway;
import br.com.fiap.techchallenge.infrastructure.out.ClientRepository;
import br.com.fiap.techchallenge.infrastructure.repository.ClientRepositoryDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RemoveClientUseCase {

    private static final Logger logger = LoggerFactory.getLogger(RemoveClientUseCase.class);
    private final ClientGateway clientGateway;
    private final ClientRepository clientRepository;

    @Autowired
    public RemoveClientUseCase(ClientGateway clientGateway, ClientRepository clientRepository) {
        this.clientGateway = clientGateway;
        this.clientRepository = clientRepository;
    }

    public ResponseEntity<Integer> remove(final String cpf) throws InvalidClientProcessException {
        ValidCPF.validateCpf(cpf);

        ClientRepositoryDb existingClient = clientRepository.findByCpf(cpf)
                .orElseThrow(() -> new ClientNotFoundException(cpf));

        return clientGateway.delete(existingClient);
    }
}
