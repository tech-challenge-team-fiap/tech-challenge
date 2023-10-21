package br.com.fiap.techchallenge.infrastructure.gateway;

import br.com.fiap.techchallenge.adapter.driven.entities.form.OrderFormDto;
import br.com.fiap.techchallenge.adapter.driven.entities.form.OrderResultFormDto;
import br.com.fiap.techchallenge.adapter.driven.entities.form.ProductOrderFormDto;
import br.com.fiap.techchallenge.common.enums.PaymentsType;
import br.com.fiap.techchallenge.common.enums.StatusOrder;
import br.com.fiap.techchallenge.common.utils.NumberOrderGenerator;
import br.com.fiap.techchallenge.infrastructure.out.OrderRepository;
import br.com.fiap.techchallenge.infrastructure.out.ProductRepository;
import br.com.fiap.techchallenge.infrastructure.repository.ClientRepositoryDb;
import br.com.fiap.techchallenge.infrastructure.repository.OrderRepositoryDb;
import br.com.fiap.techchallenge.infrastructure.repository.ProductRepositoryDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Controller
public class OrderGateway {

    private static final Logger logger = LoggerFactory.getLogger(OrderGateway.class);

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    @Autowired
    public OrderGateway(OrderRepository orderRepository, ProductRepository productRepository){
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public ResponseEntity<OrderResultFormDto> register(OrderFormDto order, ClientRepositoryDb client) {
        try {
            List<Integer> productsIds = order.getProducts().stream().map(ProductOrderFormDto::getId).collect(Collectors.toList());

            AtomicReference<BigDecimal> total = new AtomicReference<>(BigDecimal.ZERO);
            List<ProductRepositoryDb> products = (List<ProductRepositoryDb>) productRepository.findAllById(productsIds);

            // Calculated value total of list products
            products.forEach(prod->{
                total.updateAndGet(v -> v.add(prod.getPrice()));
            });

            //Generated number order
            String numberOrder = NumberOrderGenerator.generateNumberOrder();

            OrderRepositoryDb orderDB = new OrderRepositoryDb(
                    client,
                    numberOrder,
                    new Date(),
                    StatusOrder.WAITING_PAYMENTS,
                    total.get(),
                    PaymentsType.QR_CODE,
                    null,
                    new Date(),
                    products
            );

            OrderRepositoryDb orderSaveDB = orderRepository.save(orderDB);

            return ResponseEntity.ok(new OrderResultFormDto(orderSaveDB));
        } catch (Exception e) {
            logger.error("Error registering new product", e);
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<OrderResultFormDto> update(OrderRepositoryDb order) {
        try{
            OrderRepositoryDb orderSaveDB = orderRepository.save(order);
            return ResponseEntity.ok(new OrderResultFormDto(orderSaveDB));
        } catch (Exception e) {
            logger.error("Error registering new product", e);
            return ResponseEntity.badRequest().build();
        }
    }
}