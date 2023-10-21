package br.com.fiap.techchallenge.infrastructure.in;

import br.com.fiap.techchallenge.adapter.driven.entities.form.OrderFormDto;
import br.com.fiap.techchallenge.adapter.driven.entities.form.OrderResultFormDto;
import br.com.fiap.techchallenge.adapter.driven.entities.useCase.order.RegisterNewOrderUseCase;
import br.com.fiap.techchallenge.adapter.driven.entities.useCase.order.UpdateOrderUseCase;
import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {


    private final RegisterNewOrderUseCase registerNewOrderUseCase;

    private final UpdateOrderUseCase updateOrderUseCase;

    @Autowired
    public OrderController(RegisterNewOrderUseCase registerNewOrderUseCase, UpdateOrderUseCase updateOrderUseCase){
        this.registerNewOrderUseCase = registerNewOrderUseCase;
        this.updateOrderUseCase = updateOrderUseCase;
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
//
//    @DeleteMapping
//    public ResponseEntity<Integer> remove(@PathParam("id") Integer id) {
//        return removeProductUseCase.remove(id);
//    }
//
//
//    @GetMapping
//    @Transactional
//    public ResponseEntity findAll(@PathParam("type") String type) {
//        return gateway.findAll(TypeProduct.valueOf(type));
//    }

}
