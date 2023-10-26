package br.com.fiap.techchallenge.infrastructure.in;

import br.com.fiap.techchallenge.adapter.driven.entities.form.ClientFormDto;
import br.com.fiap.techchallenge.adapter.driven.entities.useCase.client.EditClientUseCase;
import br.com.fiap.techchallenge.adapter.driven.entities.useCase.client.RegisterNewClientUseCase;
import br.com.fiap.techchallenge.adapter.driven.entities.useCase.client.RemoveClientUseCase;
import br.com.fiap.techchallenge.infrastructure.gateway.ClientGateway;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final EditClientUseCase editClientUseCase;
    private final RemoveClientUseCase removeClientUseCase;
    private final RegisterNewClientUseCase registerNewClientUseCase;
    private final ClientGateway gateway;

    @Autowired
    public ClientController(RegisterNewClientUseCase registerNewClientUseCase,
                             ClientGateway clientGateway, EditClientUseCase editClientUseCase, RemoveClientUseCase removeClientUseCase) {
        this.editClientUseCase = editClientUseCase;
        this.removeClientUseCase = removeClientUseCase;
        this.registerNewClientUseCase = registerNewClientUseCase;
        this.gateway = clientGateway;
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<UUID> create(@RequestBody ClientFormDto clientFormDto) {
        return registerNewClientUseCase.register(clientFormDto);
    }

    @PutMapping()
    public ResponseEntity<Integer> editClient(@RequestBody ClientFormDto clientFormDto) {
        clientFormDto.setCpf(clientFormDto.getCpf());
        return editClientUseCase.edit(clientFormDto);
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Integer> removeClient(@PathVariable String cpf) {
        return removeClientUseCase.remove(cpf);
    }


    @GetMapping
    @Transactional
    public ResponseEntity findAll() {
        return gateway.findAll();
    }

}
