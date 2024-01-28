package br.com.fiap.techchallenge.external.infrastructure.gateway;

import br.com.fiap.techchallenge.adapter.gateways.OrderGatewayInterface;
import br.com.fiap.techchallenge.domain.exception.InvalidProcessException;
import br.com.fiap.techchallenge.domain.model.Order;
import br.com.fiap.techchallenge.application.dto.order.OrderFormDto;
import br.com.fiap.techchallenge.application.dto.order.OrderListDto;
import br.com.fiap.techchallenge.application.dto.order.OrderResultFormDto;
import br.com.fiap.techchallenge.application.dto.product.ProductOrderFormDto;
import br.com.fiap.techchallenge.domain.enums.PaymentsType;
import br.com.fiap.techchallenge.domain.enums.StatusOrder;
import br.com.fiap.techchallenge.domain.exception.BaseException;
import br.com.fiap.techchallenge.domain.exception.order.InvalidOrderProcessException;
import br.com.fiap.techchallenge.domain.exception.order.InvalidProductStorageException;
import br.com.fiap.techchallenge.domain.exception.order.OrderNotFoundException;
import br.com.fiap.techchallenge.domain.utils.NumberOrderGenerator;
import br.com.fiap.techchallenge.external.infrastructure.entities.*;
import br.com.fiap.techchallenge.external.infrastructure.repositories.NotificationRepository;
import br.com.fiap.techchallenge.external.infrastructure.repositories.OrderQueueRepository;
import br.com.fiap.techchallenge.external.infrastructure.repositories.OrderRepository;
import br.com.fiap.techchallenge.external.infrastructure.repositories.ProductRepository;

import java.time.LocalDateTime;
import java.util.*;

import br.com.fiap.techchallenge.external.infrastructure.webhook.Payments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class OrderGatewayImpl implements OrderGatewayInterface {

    private static final Logger logger = LoggerFactory.getLogger(OrderGatewayImpl.class);

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final OrderQueueRepository orderQueueRepository;

    private final NotificationRepository notificationRepository;

    private final Payments payments;

    @Autowired
    public OrderGatewayImpl(OrderRepository orderRepository, ProductRepository productRepository, OrderQueueRepository orderQueueRepository, NotificationRepository notificationRepository, Payments payments){
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderQueueRepository = orderQueueRepository;
        this.notificationRepository = notificationRepository;
        this.payments = payments;
    }

    @Override
    public OrderResultFormDto register(OrderFormDto orderFormDto, ClientDB client) throws InvalidProcessException {
        List<UUID> productsIds = orderFormDto.getProducts().stream().map(ProductOrderFormDto::getId).collect(Collectors.toList());

        AtomicReference<BigDecimal> total = new AtomicReference<>(BigDecimal.ZERO);
        List<ProductDB> products = (List<ProductDB>) productRepository.findAllById(productsIds);

        // Calculated value total of list products
        for (ProductDB prod : products) {
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

    public OrderResultFormDto doRegister(OrderDB orderDB) {
        OrderDB orderSaveDB = orderRepository.save(orderDB);
        return new OrderResultFormDto(orderSaveDB);
    }

    @Override
    public OrderResultFormDto update(OrderDB order) throws InvalidOrderProcessException {
        OrderDB orderSaveDB = orderRepository.save(order);
        return new OrderResultFormDto(orderSaveDB);
    }

    public List<OrderDB> findAll(String status) {
        StatusOrder statusOrder = StatusOrder.valueOf(status.toUpperCase());
        List<OrderDB> itens = orderRepository
                .findAllByStatusOrder(Sort.by(Sort.Direction.ASC, "date"), statusOrder )
                .stream()
                .toList();

        return itens;
    }

    public List<OrderListDto> findAll() {
        List<OrderListDto> allOrders = new ArrayList<>();

        //READY
        List<OrderQueueDB> orderWithStatusRead = orderQueueRepository
                .findAllByStatusOrder(Sort.by(Sort.Direction.DESC, "dateRegister"), StatusOrder.READY);

        orderWithStatusRead.forEach(order -> allOrders.add(new OrderListDto(order)));

        //IN_PREPARATION
        List<OrderQueueDB> orderWithStatusPreparation = orderQueueRepository
                .findAllByStatusOrder(Sort.by(Sort.Direction.DESC, "dateRegister"), StatusOrder.IN_PREPARATION);

        orderWithStatusPreparation.forEach(order -> allOrders.add(new OrderListDto(order)));

        //RECEIVED
        List<OrderDB> orderWithStatusReceived = orderRepository
                .findAllByStatusOrder(Sort.by(Sort.Direction.DESC, "date"), StatusOrder.WAITING_PAYMENTS);

        orderWithStatusReceived.forEach(order -> allOrders.add(new OrderListDto(order)));

        return allOrders;
    }


    @Override
    public OrderResultFormDto checkStatusPayments(String numberOrder) throws OrderNotFoundException {
        Optional<OrderDB> order = orderRepository.findByNumberOrder(numberOrder);
        if(order.isPresent()) {
            boolean isPay = order.get().getStatusOrder().equals(StatusOrder.PAYMENTS_RECEIVED);

            if(isPay){
                return new OrderResultFormDto(order.get());
            }

            return new OrderResultFormDto(StatusOrder.WAITING_PAYMENTS);
        } else {
            throw new OrderNotFoundException(numberOrder);
        }
    }
    public List<OrderListDto> getByStatusPayments(boolean isPayments) {
        List<OrderListDto> allOrders = new ArrayList<>();
        StatusOrder status = isPayments ? StatusOrder.PAYMENTS_RECEIVED : StatusOrder.WAITING_PAYMENTS;

        List<OrderDB> orders = orderRepository
                .findAllByStatusOrder(Sort.by(Sort.Direction.DESC, "date"), status );

        orders.forEach(order -> allOrders.add(new OrderListDto(order)));

        return allOrders;
    }

    @Override
    public void saveOrder(OrderQueueDB queue) {
        logger.info("[NOTIFICATION] Save order with successfully");
        orderQueueRepository.save(queue);
    }

    @Override
    public void sendNotification(NotificationDB notification) {
        logger.info("[NOTIFICATION] Send notification successfully");
        notificationRepository.save(notification);
    }
}