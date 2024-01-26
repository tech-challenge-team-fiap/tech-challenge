package br.com.fiap.techchallenge.useCase.order;

import br.com.fiap.techchallenge.db.dto.order.OrderResultFormDto;
import br.com.fiap.techchallenge.common.enums.StatusOrder;
import br.com.fiap.techchallenge.common.exception.order.InvalidOrderProcessException;
import br.com.fiap.techchallenge.gateway.OrderGateway;
import br.com.fiap.techchallenge.infrastructure.out.NotificationRepository;
import br.com.fiap.techchallenge.infrastructure.out.OrderQueueRepository;
import br.com.fiap.techchallenge.infrastructure.out.OrderRepository;
import br.com.fiap.techchallenge.infrastructure.out.ProductRepository;
import br.com.fiap.techchallenge.infrastructure.repository.NotificationRepositoryDB;
import br.com.fiap.techchallenge.infrastructure.repository.OrderQueueRepositoryDB;
import br.com.fiap.techchallenge.infrastructure.repository.OrderRepositoryDb;
import java.time.LocalDateTime;

import br.com.fiap.techchallenge.infrastructure.webhook.Payments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UpdateOrderUseCase extends AbstractOrderUseCase {

    private static final Logger logger = LoggerFactory.getLogger(UpdateOrderUseCase.class);
    private final OrderGateway gateway;

    private final NotificationRepository notificationRepository;

    private final OrderQueueRepository orderQueueRepository;

    private Payments payments;

    public UpdateOrderUseCase(OrderGateway gateway, OrderRepository orderRepository, NotificationRepository notificationRepository, OrderQueueRepository orderQueueRepository, ProductRepository productRepository, Payments payments) {
        super(productRepository, orderRepository);
        this.gateway = gateway;
        this.notificationRepository = notificationRepository;
        this.orderQueueRepository = orderQueueRepository;
        this.payments = payments;
    }

    public ResponseEntity<OrderResultFormDto> update(String numberOrder, String status) throws InvalidOrderProcessException {

        OrderRepositoryDb order = findOrder(numberOrder);

        StatusOrder statusOrder = validateOrderStatus(status);

        order.markStatusAs(statusOrder);

        doUpdate(numberOrder, order, statusOrder);

        return gateway.update(order);
    }

    private void doUpdate(String numberOrder, OrderRepositoryDb order, StatusOrder statusOrder) {
        paymentReceived(numberOrder, statusOrder);

        clientOrderReady(numberOrder, order, statusOrder);

        clientOrderDelivered(numberOrder, order, statusOrder);
    }

    private void clientOrderDelivered(String numberOrder, OrderRepositoryDb order, StatusOrder statusOrder) {
        if (StatusOrder.DELIVERED.equals(statusOrder)) {

            order.setDateLastUpdate(LocalDateTime.now());
            order.setDateDelivered(LocalDateTime.now());
            sendNotificationToClient(numberOrder, StatusOrder.DELIVERED);
        }
    }

    private void clientOrderReady(String numberOrder, OrderRepositoryDb order, StatusOrder statusOrder) {
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
            OrderQueueRepositoryDB queue = OrderQueueRepositoryDB.builder()
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
        NotificationRepositoryDB notification = NotificationRepositoryDB.builder()
            .numberOrder(numberOrder)
            .message(msg)
            .statusOrder(status)
            .dateRegister(LocalDateTime.now())
            .build();
        notificationRepository.save(notification);
    }
}
