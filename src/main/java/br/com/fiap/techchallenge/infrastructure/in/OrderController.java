package br.com.fiap.techchallenge.infrastructure.in;

import static br.com.fiap.techchallenge.common.utils.ProblemAware.problemOf;

import br.com.fiap.techchallenge.adapter.driven.entities.form.OrderFormDto;
import br.com.fiap.techchallenge.adapter.driven.entities.useCase.order.RegisterNewOrderUseCase;
import br.com.fiap.techchallenge.adapter.driven.entities.useCase.order.UpdateOrderUseCase;
import br.com.fiap.techchallenge.common.exception.InvalidProcessException;
import br.com.fiap.techchallenge.common.utils.ProblemAware;
import br.com.fiap.techchallenge.infrastructure.gateway.OrderGateway;
import br.com.fiap.techchallenge.infrastructure.repository.OrderRepositoryDb;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {


    private final RegisterNewOrderUseCase registerNewOrderUseCase;

    private final UpdateOrderUseCase updateOrderUseCase;

    private final OrderGateway gateway;

    @Autowired
    public OrderController(RegisterNewOrderUseCase registerNewOrderUseCase, UpdateOrderUseCase updateOrderUseCase, OrderGateway gateway){
        this.registerNewOrderUseCase = registerNewOrderUseCase;
        this.updateOrderUseCase = updateOrderUseCase;
        this.gateway = gateway;
    }

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody OrderFormDto orderFormDto) {
        try {
            return registerNewOrderUseCase.register(orderFormDto);
        } catch (InvalidProcessException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }

    @PutMapping
    public ResponseEntity update(@PathParam("numberOrder") String numberOrder, @PathParam("status") String status) {
        try {
            return updateOrderUseCase.update(numberOrder, status);
        } catch (InvalidProcessException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }

    @GetMapping("/{status}")
    @Transactional
    @JsonIgnore
    public ResponseEntity<List<OrderRepositoryDb>> findAll(@PathVariable("status") String status) {
        return gateway.findAll(status);
    }

    @GetMapping
    @Transactional
    @JsonIgnore
    public ResponseEntity findAll() {
        return gateway.findAll();
    }

}
