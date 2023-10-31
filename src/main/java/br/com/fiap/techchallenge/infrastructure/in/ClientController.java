package br.com.fiap.techchallenge.infrastructure.in;

import static br.com.fiap.techchallenge.common.utils.ProblemAware.problemOf;

import br.com.fiap.techchallenge.adapter.driven.entities.form.ClientFormDto;
import br.com.fiap.techchallenge.adapter.driven.entities.useCase.client.EditClientUseCase;
import br.com.fiap.techchallenge.adapter.driven.entities.useCase.client.RegisterNewClientUseCase;
import br.com.fiap.techchallenge.adapter.driven.entities.useCase.client.RemoveClientUseCase;
import br.com.fiap.techchallenge.common.exception.InvalidProcessException;
import br.com.fiap.techchallenge.infrastructure.gateway.ClientGateway;
import jakarta.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.ThrowableProblem;

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

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody ClientFormDto clientFormDto) throws ThrowableProblem {
        try {
            return registerNewClientUseCase.register(clientFormDto);
        } catch (InvalidProcessException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }

    @PutMapping
    public ResponseEntity editClient(@RequestBody ClientFormDto clientFormDto) {
        try {
            clientFormDto.setCpf(clientFormDto.getCpf());
            return editClientUseCase.edit(clientFormDto);
        } catch (InvalidProcessException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity removeClient(@PathVariable String cpf) {
        try {
            return removeClientUseCase.remove(cpf);
        } catch (InvalidProcessException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }


    @GetMapping
    @Transactional
    public ResponseEntity findAll() {
        return gateway.findAll();
    }

    @GetMapping("/{cpf}")
    @Transactional
    public ResponseEntity findByCpf(@PathVariable String cpf) {
        try {
            return gateway.findByCpf(cpf);
        } catch (InvalidProcessException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }

}
