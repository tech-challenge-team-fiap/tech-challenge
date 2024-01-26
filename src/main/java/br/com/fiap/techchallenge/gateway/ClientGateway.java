package br.com.fiap.techchallenge.gateway;

import br.com.fiap.techchallenge.common.exception.client.ClientAlreadyExistsException;
import br.com.fiap.techchallenge.common.exception.client.ClientNotFoundException;
import br.com.fiap.techchallenge.db.dto.client.ClientDto;
import br.com.fiap.techchallenge.entities.Client;
import br.com.fiap.techchallenge.common.exception.client.InvalidClientProcessException;
import br.com.fiap.techchallenge.common.utils.ValidCPF;
import br.com.fiap.techchallenge.infrastructure.out.ClientRepository;
import br.com.fiap.techchallenge.infrastructure.repository.ClientRepositoryDb;

import java.util.List;

import br.com.fiap.techchallenge.interfaces.ClientGatewayInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ClientGateway implements ClientGatewayInterface {

    private static final Logger logger = LoggerFactory.getLogger(ClientGateway.class);

    private final ClientRepository clientRepository;

    @Autowired
    public ClientGateway(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public ClientDto register(Client client) throws InvalidClientProcessException {
        try {
            return Optional.ofNullable(client.getCpf())
                    .filter(cpf -> !cpf.isEmpty())
                    .flatMap(clientRepository::findByCpf)
                    .map(existingClient -> {
                        logger.info("Order linked to existing customer with CPF: " + client.getCpf());
                        return new ClientDto(existingClient.getId().toString());
                    })
                    .orElseGet(() -> {
                        ClientRepositoryDb clientRepositoryDb = client.build();
                        clientRepository.save(clientRepositoryDb);
                        logger.info("Customer registered successfully");
                        return new ClientDto(clientRepositoryDb.getId().toString());
                    });
        } catch (Exception e) {
            logger.error("Error registering new customer", e);
            throw new RuntimeException(e);
        }
    }

    public ClientDto update(ClientDto clientDto) throws InvalidClientProcessException {
        Optional<ClientRepositoryDb> clientUpdateDB = clientRepository.findByCpf(clientDto.getCpf());

        if (!clientUpdateDB.isPresent()) {
            throw new ClientNotFoundException(clientDto.getCpf());
        }

        ClientRepositoryDb clientUp = clientRepository.save(
                new ClientRepositoryDb(clientDto, clientUpdateDB.get().getId()
        ));
        logger.info("[CLIENT] Update client with successfully");

        return new ClientDto(clientUp.getId().toString());
    }

    public ClientDto delete(String cpf) throws ClientNotFoundException {
        Optional<ClientRepositoryDb> client = clientRepository.findByCpf(cpf);

        if (!client.isPresent()) {
            throw new ClientNotFoundException(cpf);
        }

        clientRepository.delete(client.get());
        logger.info("[CLIENT] Remove client with successfully");

        return new ClientDto(client.get().getId().toString());
    }

    public List<ClientDto> listFindAll() {
        return this.clientRepository
                .findAll()
                .stream()
                .map(ClientDto::toDto)
                .collect(Collectors.toList());
    }

    public ClientDto findByCpf(String cpf) throws InvalidClientProcessException {
        ValidCPF.validateCpf(cpf);

        Optional<ClientRepositoryDb> clientDB = clientRepository.findByCpf(cpf);

        if (clientDB.isPresent()) {
            String message = String.format("Client with CPF %s already has a registration!", cpf);
            logger.info(message);
            throw new ClientAlreadyExistsException(cpf);
        } else {
            logger.info(String.format("Client with cpf %s not exists so creating it.", cpf));
            return new ClientDto();
        }
    }

    public ClientDto findByDocument(String cpf) throws ClientNotFoundException{
        Optional<ClientRepositoryDb> clientDB = clientRepository.findByCpf(cpf);

        if (!clientDB.isPresent()) {
            throw new ClientNotFoundException(cpf);
        }

        clientRepository.delete(clientDB.get());

        return new ClientDto(clientDB.get().getName(), clientDB.get().getCpf(), clientDB.get().getEmail(), clientDB.get().getPhone(), clientDB.get().getDateRegister());
    }
}