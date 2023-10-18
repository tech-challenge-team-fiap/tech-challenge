package br.com.fiap.techchallenge.infrastructure.in;

import br.com.fiap.techchallenge.adapter.driven.entities.form.ClientFormDto;
import br.com.fiap.techchallenge.adapter.driven.entities.useCase.EditClientUseCase;
import br.com.fiap.techchallenge.adapter.driven.entities.useCase.RegisterNewClientUseCase;
import br.com.fiap.techchallenge.adapter.driven.entities.useCase.RemoveClientUseCase;
import br.com.fiap.techchallenge.infrastructure.gateway.ClientGateway;
import jakarta.transaction.Transactional;
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

    @PostMapping(path = "register")
    @Transactional
    public ResponseEntity<Integer> create(@RequestBody ClientFormDto clientFormDto) {
        return registerNewClientUseCase.register(clientFormDto);
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Integer> editClient(@PathVariable String cpf, @RequestBody ClientFormDto clientFormDto) {
        clientFormDto.setCpf(cpf);
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
