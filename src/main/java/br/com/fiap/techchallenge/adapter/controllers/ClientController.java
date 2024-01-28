package br.com.fiap.techchallenge.adapter.controllers;

import static br.com.fiap.techchallenge.domain.utils.ProblemAware.problemOf;

import br.com.fiap.techchallenge.domain.exception.client.ClientNotFoundException;
import br.com.fiap.techchallenge.application.dto.client.ClientDto;
import br.com.fiap.techchallenge.domain.interfaces.ClientUseCaseInterface;
import br.com.fiap.techchallenge.domain.exception.InvalidProcessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.ThrowableProblem;

@Controller
public class ClientController {
    private final ClientUseCaseInterface clientUseCase;

    @Autowired
    public ClientController(ClientUseCaseInterface clientUseCase) {
        this.clientUseCase = clientUseCase;
    }

    public ResponseEntity<?> register(@RequestBody ClientDto clientDto) throws ThrowableProblem {
        try {
            return ResponseEntity.ok(clientUseCase.register(clientDto));
        } catch (InvalidProcessException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }

    public ResponseEntity<?> edit(@RequestBody ClientDto clientDto) {
        try {
            clientDto.setCpf(clientDto.getCpf());
            return ResponseEntity.ok(clientUseCase.edit(clientDto));
        } catch (InvalidProcessException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }

    public ResponseEntity<?> remove(@PathVariable String cpf) {
        try {
            return ResponseEntity.ok(clientUseCase.remove(cpf));
        } catch (InvalidProcessException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }

    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(clientUseCase.findAll());
    }

    public ResponseEntity<?> findByDocument(@PathVariable String cpf) {
        try {
            return ResponseEntity.ok(clientUseCase.findByDocument(cpf));
        } catch (ClientNotFoundException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }
}