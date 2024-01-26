package br.com.fiap.techchallenge.useCase.client;

import br.com.fiap.techchallenge.common.exception.InvalidProcessException;
import br.com.fiap.techchallenge.common.exception.client.ClientNotFoundException;
import br.com.fiap.techchallenge.db.dto.client.ClientDto;
import br.com.fiap.techchallenge.gateway.ClientGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientUseCase extends AbstractClientUseCase {

    private static final Logger logger = LoggerFactory.getLogger(ClientUseCase.class);
    private final ClientGateway clientGateway;

    @Autowired
    public ClientUseCase(ClientGateway clientGateway) {
        super(logger);
        this.clientGateway = clientGateway;
    }

    public List<ClientDto> findAll() {
        return clientGateway.listFindAll();
    }

    public ClientDto findByCpf(String cpf) throws InvalidProcessException {
        return clientGateway.findByCpf(cpf);
    }

    public ClientDto findByDocument(String cpf) throws ClientNotFoundException {
        return clientGateway.findByDocument(cpf);
    }
}