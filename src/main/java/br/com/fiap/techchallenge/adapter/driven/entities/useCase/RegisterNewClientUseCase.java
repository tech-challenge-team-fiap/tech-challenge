package br.com.fiap.techchallenge.adapter.driven.entities.useCase;

import br.com.fiap.techchallenge.adapter.driven.entities.Client;
import br.com.fiap.techchallenge.adapter.driven.entities.form.ClientFormDto;
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

    @Autowired
    public RegisterNewClientUseCase(ClientGateway clientGateway, ClientRepository clientRepository) {
        this.clientGateway = clientGateway;
        this.clientRepository = clientRepository;
    }

    public ResponseEntity<Integer> register(ClientFormDto clientFormDto) {
        Optional<ClientRepositoryDb> optionalCliente = clientRepository.findByCpf(clientFormDto.getCpf());
        if (!optionalCliente.isPresent()) {
            Client client = new Client(clientFormDto);
            return clientGateway.register(client);
        }
        logger.info("Client already has a registration!");
        return ResponseEntity.badRequest().build();
    }
}
