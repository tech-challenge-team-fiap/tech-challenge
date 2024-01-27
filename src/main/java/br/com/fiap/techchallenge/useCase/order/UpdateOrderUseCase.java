package br.com.fiap.techchallenge.useCase.order;

import br.com.fiap.techchallenge.application.dto.order.OrderResultFormDto;
import br.com.fiap.techchallenge.domain.enums.StatusOrder;
import br.com.fiap.techchallenge.domain.exception.order.InvalidOrderProcessException;
import br.com.fiap.techchallenge.domain.interfaces.abstracts.AbstractOrderUseCase;
import br.com.fiap.techchallenge.external.infrastructure.gateway.OrderGatewayImpl;
import br.com.fiap.techchallenge.external.infrastructure.repositories.NotificationRepository;
import br.com.fiap.techchallenge.external.infrastructure.repositories.OrderQueueRepository;
import br.com.fiap.techchallenge.external.infrastructure.repositories.OrderRepository;
import br.com.fiap.techchallenge.external.infrastructure.repositories.ProductRepository;
import br.com.fiap.techchallenge.external.infrastructure.entities.NotificationDB;
import br.com.fiap.techchallenge.external.infrastructure.entities.OrderQueueDB;
import br.com.fiap.techchallenge.external.infrastructure.entities.OrderDB;
import java.time.LocalDateTime;

import br.com.fiap.techchallenge.external.infrastructure.webhook.Payments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UpdateOrderUseCase extends AbstractOrderUseCase {

    private static final Logger logger = LoggerFactory.getLogger(UpdateOrderUseCase.class);
    private final OrderGatewayImpl gateway;

    private final NotificationRepository notificationRepository;

    private final OrderQueueRepository orderQueueRepository;

    private Payments payments;

    public UpdateOrderUseCase(OrderGatewayImpl gateway, OrderRepository orderRepository, NotificationRepository notificationRepository, OrderQueueRepository orderQueueRepository, ProductRepository productRepository, Payments payments) {
        super(productRepository, orderRepository);
        this.gateway = gateway;
        this.notificationRepository = notificationRepository;
        this.orderQueueRepository = orderQueueRepository;
        this.payments = payments;
    }

    public OrderResultFormDto update(String numberOrder, String status) throws InvalidOrderProcessException {

        OrderDB order = findOrder(numberOrder);

        StatusOrder statusOrder = validateOrderStatus(status);

        order.markStatusAs(statusOrder);

        doUpdate(numberOrder, order, statusOrder);

        return gateway.update(order);
    }

    private void doUpdate(String numberOrder, OrderDB order, StatusOrder statusOrder) {
        paymentReceived(numberOrder, statusOrder);

        clientOrderReady(numberOrder, order, statusOrder);

        clientOrderDelivered(numberOrder, order, statusOrder);
    }

    private void clientOrderDelivered(String numberOrder, OrderDB order, StatusOrder statusOrder) {
        if (StatusOrder.DELIVERED.equals(statusOrder)) {

            order.setDateLastUpdate(LocalDateTime.now());
            order.setDateDelivered(LocalDateTime.now());
            sendNotificationToClient(numberOrder, StatusOrder.DELIVERED);
        }
    }

    private void clientOrderReady(String numberOrder, OrderDB order, StatusOrder statusOrder) {
        if (StatusOrder.READY.equals(statusOrder)) {

            order.setDateLastUpdate(LocalDateTime.now());
            sendNotificationToClient(numberOrder, StatusOrder.READY);
        }
    }

    private void paymentReceived(String numberOrder, StatusOrder statusOrder) {

        boolean isPayment = payments.checkStatusPayments(numberOrder);

        if(isPayment){
            logger.info("[ORDER] Payment checked and status is true ");
        }

        if (StatusOrder.PAYMENTS_RECEIVED.equals(statusOrder) && isPayment) {
            sendNotificationToClient(numberOrder, StatusOrder.RECEIVED);

            // Send order to queue IN_PREPARATION
            logger.info("[QUEUE] Sending the client order to preparation with order number: " + numberOrder);
            OrderQueueDB queue = OrderQueueDB.builder()
                .numberOrder(numberOrder)
                .statusOrder(StatusOrder.IN_PREPARATION)
                .dateRegister(LocalDateTime.now())
                .build();

            sendNotificationToClient(numberOrder, StatusOrder.IN_PREPARATION);

            orderQueueRepository.save(queue);
        }
    }

    private void sendNotificationToClient(String numberOrder, StatusOrder status) {
        String msg = "[Notification] The client order with number: " + numberOrder + " - Status: " + status + ".";
        logger.info(msg);
        NotificationDB notification = NotificationDB.builder()
            .numberOrder(numberOrder)
            .message(msg)
            .statusOrder(status)
            .dateRegister(LocalDateTime.now())
            .build();
        notificationRepository.save(notification);
    }
}
