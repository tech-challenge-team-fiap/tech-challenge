package br.com.fiap.techchallenge.controllers;

import static br.com.fiap.techchallenge.common.utils.ProblemAware.problemOf;

import br.com.fiap.techchallenge.common.exception.client.ClientNotFoundException;
import br.com.fiap.techchallenge.db.dto.client.ClientDto;
import br.com.fiap.techchallenge.db.dto.client.ClientFormDto;
import br.com.fiap.techchallenge.useCase.client.EditClientUseCase;
import br.com.fiap.techchallenge.useCase.client.ClientUseCase;
import br.com.fiap.techchallenge.useCase.client.RegisterNewClientUseCase;
import br.com.fiap.techchallenge.useCase.client.RemoveClientUseCase;
import br.com.fiap.techchallenge.common.exception.InvalidProcessException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.ThrowableProblem;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final EditClientUseCase editClientUseCase;
    private final RemoveClientUseCase removeClientUseCase;
    private final RegisterNewClientUseCase registerNewClientUseCase;
    private final ClientUseCase clientUseCase;

    @Autowired
    public ClientController(RegisterNewClientUseCase registerNewClientUseCase, ClientUseCase clientUseCase, EditClientUseCase editClientUseCase, RemoveClientUseCase removeClientUseCase) {
        this.editClientUseCase = editClientUseCase;
        this.removeClientUseCase = removeClientUseCase;
        this.registerNewClientUseCase = registerNewClientUseCase;
        this.clientUseCase = clientUseCase;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody ClientDto clientDto) throws ThrowableProblem {
        try {
            return ResponseEntity.ok(registerNewClientUseCase.register(clientDto));
        } catch (InvalidProcessException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }

    @PutMapping
    public ResponseEntity<?> edit(@RequestBody ClientDto clientDto) {
        try {
            clientDto.setCpf(clientDto.getCpf());
            return ResponseEntity.ok(editClientUseCase.edit(clientDto));
        } catch (InvalidProcessException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<?> remove(@PathVariable String cpf) {
        try {
            return ResponseEntity.ok(removeClientUseCase.remove(cpf));
        } catch (InvalidProcessException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }

    @GetMapping
    @Transactional
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(clientUseCase.findAll());
    }

    @GetMapping("/{cpf}")
    @Transactional
    public ResponseEntity<?> findByCpf(@PathVariable String cpf) {
        try {
            return ResponseEntity.ok(clientUseCase.findByDocument(cpf));
        } catch (ClientNotFoundException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }
}