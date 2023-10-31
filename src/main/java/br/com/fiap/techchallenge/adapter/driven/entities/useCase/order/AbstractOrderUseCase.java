package br.com.fiap.techchallenge.adapter.driven.entities.useCase.order;

import br.com.fiap.techchallenge.adapter.driven.entities.form.ProductOrderFormDto;
import br.com.fiap.techchallenge.common.enums.StatusOrder;
import br.com.fiap.techchallenge.common.exception.InvalidProcessException;
import br.com.fiap.techchallenge.common.exception.order.InvalidOrderProcessException;
import br.com.fiap.techchallenge.common.exception.order.InvalidOrderProductException;
import br.com.fiap.techchallenge.common.exception.order.InvalidOrderStatusException;
import br.com.fiap.techchallenge.common.exception.order.OrderNotFoundException;
import br.com.fiap.techchallenge.common.exception.products.ProductNotFoundException;
import br.com.fiap.techchallenge.infrastructure.out.OrderRepository;
import br.com.fiap.techchallenge.infrastructure.out.ProductRepository;
import br.com.fiap.techchallenge.infrastructure.repository.OrderRepositoryDb;
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

    public OrderRepositoryDb findOrder(String numberOrder) throws InvalidOrderProcessException {
        Optional<OrderRepositoryDb> order = orderRepository.findByNumberOrder(numberOrder);
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
