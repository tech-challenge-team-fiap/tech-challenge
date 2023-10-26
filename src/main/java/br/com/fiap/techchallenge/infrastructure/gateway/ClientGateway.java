package br.com.fiap.techchallenge.infrastructure.gateway;

import br.com.fiap.techchallenge.adapter.driven.entities.Client;
import br.com.fiap.techchallenge.infrastructure.out.ClientRepository;
import br.com.fiap.techchallenge.infrastructure.repository.ClientRepositoryDb;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class ClientGateway {

    private static final Logger logger = LoggerFactory.getLogger(ClientGateway.class);

    private final ClientRepository clientRepository;

    @Autowired
    public ClientGateway(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public ResponseEntity<UUID> register(Client client) {
        try {
            return Optional.ofNullable(client.getCpf())
                    .filter(cpf -> !cpf.isEmpty())
                    .flatMap(clientRepository::findByCpf)
                    .map(existingClient -> {
                        // TODO: proceed to place the ORDER
                        logger.info("Order linked to existing customer with CPF: " + client.getCpf());
                        return ResponseEntity.ok(existingClient.getId());
                    })
                    .orElseGet(() -> {
                        ClientRepositoryDb clientRepositoryDb = new ClientRepositoryDb(client);
                        clientRepository.save(clientRepositoryDb);
                        logger.info("Customer registered successfully");
                        return ResponseEntity.ok(clientRepositoryDb.getId());
                    });
        } catch (Exception e) {
            logger.error("Error registering new customer", e);
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Integer> update(ClientRepositoryDb client) {
        // Assuming ClientGateway has a method to update a client
        clientRepository.save(client);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Integer> delete(ClientRepositoryDb client) {
        // Assuming ClientGateway has a method to delete a client
        clientRepository.delete(client);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity findAll() {
        return new ResponseEntity<>(clientRepository.findAll(), HttpStatus.OK);
    }
}
