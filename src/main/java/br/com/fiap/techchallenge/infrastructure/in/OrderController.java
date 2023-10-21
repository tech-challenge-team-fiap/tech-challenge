package br.com.fiap.techchallenge.infrastructure.in;

import br.com.fiap.techchallenge.adapter.driven.entities.form.OrderFormDto;
import br.com.fiap.techchallenge.adapter.driven.entities.form.OrderResultFormDto;
import br.com.fiap.techchallenge.adapter.driven.entities.useCase.order.RegisterNewOrderUseCase;
import br.com.fiap.techchallenge.adapter.driven.entities.useCase.order.UpdateOrderUseCase;
import br.com.fiap.techchallenge.infrastructure.gateway.OrderGateway;
import br.com.fiap.techchallenge.infrastructure.repository.OrderRepositoryDb;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;
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

    @PostMapping(path = "register")
    @Transactional
    public ResponseEntity<OrderResultFormDto> create(@RequestBody OrderFormDto orderFormDto) {
        return registerNewOrderUseCase.register(orderFormDto);
    }

    @PutMapping(path = "update")
    public ResponseEntity<OrderResultFormDto> update(@PathParam("numberOrder") String numberOrder, @PathParam("status") String status) {
        return updateOrderUseCase.update(numberOrder, status);
    }

    @GetMapping(path = "all")
    @Transactional
    @JsonIgnore
    public ResponseEntity<List<OrderRepositoryDb>> findAll(@PathParam("status") String status) {
        return gateway.findAll(status);
    }

    @GetMapping
    @Transactional
    @JsonIgnore
    public ResponseEntity findAll() {
        return gateway.findAll();
    }

}
