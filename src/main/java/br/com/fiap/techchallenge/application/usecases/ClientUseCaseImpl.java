package br.com.fiap.techchallenge.application.usecases;

import br.com.fiap.techchallenge.application.dto.client.ClientDto;
import br.com.fiap.techchallenge.domain.exception.InvalidProcessException;
import br.com.fiap.techchallenge.domain.exception.client.ClientNotFoundException;
import br.com.fiap.techchallenge.domain.exception.client.InvalidClientProcessException;
import br.com.fiap.techchallenge.domain.interfaces.ClientUseCaseInterface;
import br.com.fiap.techchallenge.domain.model.Client;
import br.com.fiap.techchallenge.domain.utils.ValidCPF;
import br.com.fiap.techchallenge.external.infrastructure.gateway.ClientGatewayImpl;
import br.com.fiap.techchallenge.domain.interfaces.abstracts.AbstractClientUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientUseCaseImpl extends AbstractClientUseCase implements ClientUseCaseInterface  {

    private static final Logger logger = LoggerFactory.getLogger(ClientUseCaseImpl.class);
    private final ClientGatewayImpl clientGatewayImpl;

    @Autowired
    public ClientUseCaseImpl(ClientGatewayImpl clientGatewayImpl) {
        super(logger);
        this.clientGatewayImpl = clientGatewayImpl;
    }

    @Override
    public List<ClientDto> findAll() {
        return clientGatewayImpl.listFindAll();
    }

    @Override
    public ClientDto findByCpf(String cpf) throws InvalidProcessException {
        return clientGatewayImpl.findByCpf(cpf);
    }

    @Override
    public ClientDto findByDocument(String cpf) throws ClientNotFoundException {
        return clientGatewayImpl.findByDocument(cpf);
    }

    @Override
    public ClientDto edit(ClientDto clientDto) throws InvalidClientProcessException {
        validateEmail(clientDto.getEmail());
        validatePhoneNumber(clientDto.getPhone());

        return clientGatewayImpl.update(clientDto);
    }

    @Override
    public ClientDto register(ClientDto clientDto) throws InvalidClientProcessException {
        validateCpf(clientDto.getCpf());
        validateEmail(clientDto.getEmail());
        validatePhoneNumber(clientDto.getPhone());
        checkIfClientAlreadyExists(clientDto.getCpf());

        return clientGatewayImpl.register(new Client(clientDto));
    }

    @Override
    public ClientDto remove(String cpf) throws InvalidClientProcessException {
        ValidCPF.validateCpf(cpf);
        return clientGatewayImpl.delete(cpf);
    }

    private void checkIfClientAlreadyExists(String cpf) throws InvalidClientProcessException {
        this.clientGatewayImpl.findByCpf(cpf);
    }
}
