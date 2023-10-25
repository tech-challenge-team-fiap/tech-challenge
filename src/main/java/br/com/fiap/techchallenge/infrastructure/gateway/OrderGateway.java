package br.com.fiap.techchallenge.infrastructure.gateway;

import br.com.fiap.techchallenge.adapter.driven.entities.form.OrderFormDto;
import br.com.fiap.techchallenge.adapter.driven.entities.form.OrderResultFormDto;
import br.com.fiap.techchallenge.adapter.driven.entities.form.ProductOrderFormDto;
import br.com.fiap.techchallenge.common.enums.PaymentsType;
import br.com.fiap.techchallenge.common.enums.StatusOrder;
import br.com.fiap.techchallenge.common.exception.BaseException;
import br.com.fiap.techchallenge.common.utils.NumberOrderGenerator;
import br.com.fiap.techchallenge.infrastructure.out.OrderRepository;
import br.com.fiap.techchallenge.infrastructure.out.ProductRepository;
import br.com.fiap.techchallenge.infrastructure.repository.ClientRepositoryDb;
import br.com.fiap.techchallenge.infrastructure.repository.OrderQueueRepositoryDB;
import br.com.fiap.techchallenge.infrastructure.repository.OrderRepositoryDb;
import br.com.fiap.techchallenge.infrastructure.repository.ProductRepositoryDb;
import java.time.LocalDateTime;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
            List<UUID> productsIds = order.getProducts().stream().map(ProductOrderFormDto::getId).collect(Collectors.toList());


            AtomicReference<BigDecimal> total = new AtomicReference<>(BigDecimal.ZERO);
            List<ProductRepositoryDb> products = (List<ProductRepositoryDb>) productRepository.findAllById(productsIds);

            // Calculated value total of list products
            products.forEach(prod->{
                total.updateAndGet(v -> v.add(prod.getPrice()));

                if(prod.getQuantity().intValue() > 0) {
                    prod.setQuantity(prod.getQuantity() - 1);

                    logger.info("Update quantity productId:" + prod.getId());
                    productRepository.save(prod);
                }
            });

            if(total.get().intValue() == 0){
                new BaseException("Error calculating total price orders");
            }

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
                    LocalDateTime.now(),
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

    public ResponseEntity<List<OrderRepositoryDb>> findAll(String status) {
        try {
            StatusOrder statusOrder = StatusOrder.valueOf(status.toUpperCase());

            List<OrderRepositoryDb> itens = orderRepository
                    .findAllByStatusOrder(Sort.by(Sort.Direction.ASC, "date"), statusOrder )
                    .stream()
                    .toList();

            return ResponseEntity.ok(itens);
        } catch (Exception e) {
            logger.error("Error registering new product", e);
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity findAll() {
        try {
            List<OrderRepositoryDb> itens = (List<OrderRepositoryDb>) orderRepository.findAll();
            return ResponseEntity.ok(itens);
        } catch (Exception e) {
            logger.error("Error registering new product", e);
            return ResponseEntity.badRequest().build();
        }
    }
}