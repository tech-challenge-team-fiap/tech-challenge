package br.com.fiap.techchallenge.interfaces;

import br.com.fiap.techchallenge.common.exception.InvalidProcessException;
import br.com.fiap.techchallenge.common.exception.client.ClientAlreadyExistsException;
import br.com.fiap.techchallenge.common.exception.client.ClientNotFoundException;
import br.com.fiap.techchallenge.common.exception.client.InvalidClientProcessException;
import br.com.fiap.techchallenge.db.dto.client.ClientDto;
import br.com.fiap.techchallenge.entities.Client;
import br.com.fiap.techchallenge.infrastructure.repository.ClientRepositoryDb;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ClientGatewayInterface {

    ClientDto register(Client client) throws InvalidClientProcessException;

    ClientDto update(ClientDto clientDto) throws InvalidClientProcessException;

    ClientDto delete(String cpf) throws ClientNotFoundException;

    List<ClientDto> listFindAll();

    ClientDto findByCpf(String cpf) throws InvalidClientProcessException;

    ClientDto findByDocument(String cpf) throws ClientNotFoundException;
}