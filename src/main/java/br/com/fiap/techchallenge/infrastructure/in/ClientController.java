package br.com.fiap.techchallenge.infrastructure.in;

import br.com.fiap.techchallenge.adapter.driven.entities.form.ClientFormDto;
import br.com.fiap.techchallenge.adapter.driven.entities.useCase.RegisterNewClientUseCase;
import br.com.fiap.techchallenge.infrastructure.gateway.ClientGateway;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {


    private final RegisterNewClientUseCase registerNewClientUseCase;
    private final ClientGateway gateway;

    @Autowired
    public ClientController(RegisterNewClientUseCase registerNewClientUseCase,
                             ClientGateway clientGateway) {
        this.registerNewClientUseCase = registerNewClientUseCase;
        this.gateway = clientGateway;
    }

    @PostMapping(path = "/client/registr")
    @Transactional
    public ResponseEntity<Integer> create(@RequestBody ClientFormDto clientFormDto) {
        return registerNewClientUseCase.register(clientFormDto);
    }

    @GetMapping(path = "/client")
    @Transactional
    public ResponseEntity findAll() {
        return gateway.findAll();
    }

}
