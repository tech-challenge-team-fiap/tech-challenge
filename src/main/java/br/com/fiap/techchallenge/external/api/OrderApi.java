package br.com.fiap.techchallenge.external.api;

import br.com.fiap.techchallenge.adapter.controllers.OrderController;
import br.com.fiap.techchallenge.application.dto.order.OrderFormDto;
import br.com.fiap.techchallenge.application.dto.order.OrderResultFormDto;
import br.com.fiap.techchallenge.domain.exception.order.OrderNotFoundException;
import br.com.fiap.techchallenge.external.infrastructure.entities.OrderDB;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderApi {

    private final OrderController orderController;

    @Autowired
    public OrderApi(OrderController orderController){
        this.orderController = orderController;
    }

    @PostMapping("/{checkout}")
    @Transactional
    public ResponseEntity<?> register(@RequestBody OrderFormDto orderFormDto) {
        return orderController.register(orderFormDto);
    }

    @PutMapping
    public ResponseEntity update(@PathParam("numberOrder") String numberOrder, @PathParam("status") String status) {
        return orderController.update(numberOrder, status);
    }

    @GetMapping("/{status}")
    @Transactional
    @JsonIgnore
    public ResponseEntity<?> findAll(@PathVariable("status") String status) {
        return orderController.findAll(status);
    }

    @GetMapping("/status-payments/{numberOrder}")
    @Transactional
    @JsonIgnore
    public ResponseEntity<?> checkStatusPayments(@PathVariable("numberOrder") String numberOrder) throws OrderNotFoundException {
        return orderController.checkStatusPayments(numberOrder);
    }

    @GetMapping("/payments-received/{payment}")
    @Transactional
    @JsonIgnore
    public ResponseEntity<?> getByStatusPayments(@PathVariable("payment") boolean isPayments){
        return orderController.getByStatusPayments(isPayments);
    }

    @GetMapping
    @Transactional
    @JsonIgnore
    public ResponseEntity<?> findAll() {
        return orderController.findAll();
    }
}