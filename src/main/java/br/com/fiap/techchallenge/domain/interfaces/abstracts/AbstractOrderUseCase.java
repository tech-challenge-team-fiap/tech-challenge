package br.com.fiap.techchallenge.domain.interfaces.abstracts;

import br.com.fiap.techchallenge.application.dto.product.ProductOrderFormDto;
import br.com.fiap.techchallenge.domain.enums.StatusOrder;
import br.com.fiap.techchallenge.domain.exception.InvalidProcessException;
import br.com.fiap.techchallenge.domain.exception.order.InvalidOrderProcessException;
import br.com.fiap.techchallenge.domain.exception.order.InvalidOrderProductException;
import br.com.fiap.techchallenge.domain.exception.order.InvalidOrderStatusException;
import br.com.fiap.techchallenge.domain.exception.order.OrderNotFoundException;
import br.com.fiap.techchallenge.domain.exception.products.ProductNotFoundException;
import br.com.fiap.techchallenge.external.infrastructure.repositories.OrderRepository;
import br.com.fiap.techchallenge.external.infrastructure.repositories.ProductRepository;
import br.com.fiap.techchallenge.external.infrastructure.entities.OrderDB;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractOrderUseCase {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    protected void validateProduct(List<ProductOrderFormDto> products) throws InvalidProcessException {
        if (products.isEmpty()) {
            throw new InvalidOrderProductException();
        } else {
            doValidateProduct(products);
        }
    }

    private void doValidateProduct(List<ProductOrderFormDto> products) throws ProductNotFoundException {
        for (ProductOrderFormDto product : products) {
            if (productRepository.findById(product.getId()).isEmpty()) {
                throw new ProductNotFoundException(product.getId());
            }
        }
    }

    public OrderDB findOrder(String numberOrder) throws InvalidOrderProcessException {
        Optional<OrderDB> order = orderRepository.findByNumberOrder(numberOrder);
        if(order.isPresent()) {
            return order.get();
        } else {
            throw new OrderNotFoundException(numberOrder);
        }
    }

    public StatusOrder validateOrderStatus(String status) throws InvalidOrderProcessException {
        try {
            return StatusOrder.valueOf(status);
        } catch (Exception ex) {
            throw new InvalidOrderStatusException(status);
        }

    }
}
