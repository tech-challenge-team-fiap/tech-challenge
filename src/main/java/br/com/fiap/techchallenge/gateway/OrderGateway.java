package br.com.fiap.techchallenge.gateway;

import br.com.fiap.techchallenge.entities.Order;
import br.com.fiap.techchallenge.db.dto.order.OrderFormDto;
import br.com.fiap.techchallenge.db.dto.order.OrderListDto;
import br.com.fiap.techchallenge.db.dto.order.OrderResultFormDto;
import br.com.fiap.techchallenge.db.dto.product.ProductOrderFormDto;
import br.com.fiap.techchallenge.common.enums.PaymentsType;
import br.com.fiap.techchallenge.common.enums.StatusOrder;
import br.com.fiap.techchallenge.common.exception.BaseException;
import br.com.fiap.techchallenge.common.exception.order.InvalidOrderProcessException;
import br.com.fiap.techchallenge.common.exception.order.InvalidProductStorageException;
import br.com.fiap.techchallenge.common.exception.order.OrderNotFoundException;
import br.com.fiap.techchallenge.common.utils.NumberOrderGenerator;
import br.com.fiap.techchallenge.infrastructure.out.OrderQueueRepository;
import br.com.fiap.techchallenge.infrastructure.out.OrderRepository;
import br.com.fiap.techchallenge.infrastructure.out.ProductRepository;
import br.com.fiap.techchallenge.infrastructure.repository.ClientRepositoryDb;
import br.com.fiap.techchallenge.infrastructure.repository.OrderQueueRepositoryDB;
import br.com.fiap.techchallenge.infrastructure.repository.OrderRepositoryDb;
import br.com.fiap.techchallenge.infrastructure.repository.ProductRepositoryDb;
import java.time.LocalDateTime;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Controller
public class OrderGateway {

    private static final Logger logger = LoggerFactory.getLogger(OrderGateway.class);

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final OrderQueueRepository orderQueueRepository;

    @Autowired
    public OrderGateway(OrderRepository orderRepository, ProductRepository productRepository, OrderQueueRepository orderQueueRepository){
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderQueueRepository = orderQueueRepository;
    }

    public ResponseEntity<OrderResultFormDto> register(OrderFormDto orderFormDto, ClientRepositoryDb client) throws InvalidOrderProcessException {
        List<UUID> productsIds = orderFormDto.getProducts().stream().map(ProductOrderFormDto::getId).collect(Collectors.toList());

        AtomicReference<BigDecimal> total = new AtomicReference<>(BigDecimal.ZERO);
        List<ProductRepositoryDb> products = (List<ProductRepositoryDb>) productRepository.findAllById(productsIds);

        // Calculated value total of list products
        for (ProductRepositoryDb prod : products) {
            total.updateAndGet(v -> v.add(prod.getPrice()));

            if (prod.hasStorage()) {
                prod.mergeQuantity(1);
                productRepository.save(prod);
            } else {
                throw new InvalidProductStorageException(prod.getId());
            }
        }

        if (total.get().intValue() == 0) {
            new BaseException("Error calculating total price orders");
        }

        //Generated number order
        String numberOrder = NumberOrderGenerator.generateNumberOrder();

        Order productOrder = new Order(client, numberOrder, new Date(), StatusOrder.IN_PREPARATION, total.get(), PaymentsType.QR_CODE, null, null, LocalDateTime.now(), products);

        return doRegister(productOrder.build());
    }

    public ResponseEntity<OrderResultFormDto> doRegister(OrderRepositoryDb orderDB) {
        OrderRepositoryDb orderSaveDB = orderRepository.save(orderDB);
        return ResponseEntity.ok(new OrderResultFormDto(orderSaveDB));
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
            List<OrderListDto> allOrders = new ArrayList<>();

            //READY
            List<OrderQueueRepositoryDB> orderWithStatusRead = orderQueueRepository
                    .findAllByStatusOrder(Sort.by(Sort.Direction.DESC, "dateRegister"), StatusOrder.READY);

            orderWithStatusRead.forEach(order -> allOrders.add(new OrderListDto(order)));

            //IN_PREPARATION
            List<OrderQueueRepositoryDB> orderWithStatusPreparation = orderQueueRepository
                    .findAllByStatusOrder(Sort.by(Sort.Direction.DESC, "dateRegister"), StatusOrder.IN_PREPARATION);

            orderWithStatusPreparation.forEach(order -> allOrders.add(new OrderListDto(order)));

            //RECEIVED
            List<OrderRepositoryDb> orderWithStatusReceived = orderRepository
                    .findAllByStatusOrder(Sort.by(Sort.Direction.DESC, "date"), StatusOrder.WAITING_PAYMENTS);

            orderWithStatusReceived.forEach(order -> allOrders.add(new OrderListDto(order)));

            return ResponseEntity.ok(allOrders);
        } catch (Exception e) {
            logger.error("Error list all", e);
            return ResponseEntity.badRequest().build();
        }
    }


    public ResponseEntity checkPaymentStatus(String numberOrder) {
        try {
            Optional<OrderRepositoryDb> order = orderRepository.findByNumberOrder(numberOrder);
            if(order.isPresent()) {
                boolean isPay = order.get().getStatusOrder().equals(StatusOrder.PAYMENTS_RECEIVED);

                if(isPay){
                    return ResponseEntity.ok(new OrderResultFormDto(order.get()));
                }

                return ResponseEntity.ok(false);
            } else {
                throw new OrderNotFoundException(numberOrder);
            }
        } catch (Exception e) {
            logger.error("Error registering new product", e);
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity getByStatusPayments(boolean isPayments) {
        try{
            List<OrderListDto> allOrders = new ArrayList<>();
            StatusOrder status = isPayments ? StatusOrder.PAYMENTS_RECEIVED : StatusOrder.WAITING_PAYMENTS;

            List<OrderRepositoryDb> orders = orderRepository
                    .findAllByStatusOrder(Sort.by(Sort.Direction.DESC, "date"), status );

            orders.forEach(order -> allOrders.add(new OrderListDto(order)));

            return ResponseEntity.ok(allOrders);

        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}