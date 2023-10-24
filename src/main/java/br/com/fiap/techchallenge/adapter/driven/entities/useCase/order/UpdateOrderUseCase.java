package br.com.fiap.techchallenge.adapter.driven.entities.useCase.order;

import br.com.fiap.techchallenge.adapter.driven.entities.form.OrderResultFormDto;
import br.com.fiap.techchallenge.common.enums.StatusOrder;
import br.com.fiap.techchallenge.common.exception.BaseException;
import br.com.fiap.techchallenge.infrastructure.gateway.OrderGateway;
import br.com.fiap.techchallenge.infrastructure.out.NotificationRepository;
import br.com.fiap.techchallenge.infrastructure.out.OrderQueueRepository;
import br.com.fiap.techchallenge.infrastructure.out.OrderRepository;
import br.com.fiap.techchallenge.infrastructure.repository.NotificationRepositoryDB;
import br.com.fiap.techchallenge.infrastructure.repository.OrderQueueRepositoryDB;
import br.com.fiap.techchallenge.infrastructure.repository.OrderRepositoryDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UpdateOrderUseCase {

    private static final Logger logger = LoggerFactory.getLogger(UpdateOrderUseCase.class);
    private final OrderGateway gateway;
    private final OrderRepository orderRepository;

    private final NotificationRepository notificationRepository;

    private final OrderQueueRepository orderQueueRepository;

    @Autowired
    public UpdateOrderUseCase(OrderGateway gateway, OrderRepository orderRepository, NotificationRepository notificationRepository, OrderQueueRepository orderQueueRepository) {
        this.gateway = gateway;
        this.orderRepository = orderRepository;
        this.notificationRepository = notificationRepository;
        this.orderQueueRepository = orderQueueRepository;
    }

    public ResponseEntity<OrderResultFormDto> update(String numberOrder, String status) {

        OrderRepositoryDb order = orderRepository.findByNumberOrder(numberOrder).orElseThrow(() -> new BaseException("Order: " + numberOrder + " does not exist!"));

        order.setStatusOrder(StatusOrder.valueOf(status.toUpperCase()));

        if(StatusOrder.PAYMENTS_RECEIVED.name().equals(status.toUpperCase())){

            sendNotificationToClient(numberOrder, StatusOrder.RECEIVED );

            // Send order to queue IN_PREPARATION
            logger.info("[OrderQueue] Enviado para a fila o pedido de número: " + numberOrder);
            OrderQueueRepositoryDB queue = new OrderQueueRepositoryDB(
                    numberOrder,
                    StatusOrder.IN_PREPARATION,
                    new Date()
            );

            sendNotificationToClient(numberOrder , StatusOrder.IN_PREPARATION);

            orderQueueRepository.save(queue);
        }

        if(status.toUpperCase().equals(StatusOrder.READY)){
            order.setDateLastUpdate(new Date());
            sendNotificationToClient(numberOrder , StatusOrder.READY);
        }

        if(status.toUpperCase().equals(StatusOrder.DELIVERED)){

            order.setDateLastUpdate(new Date());
            order.setDateDelivered(new Date());

            sendNotificationToClient(numberOrder , StatusOrder.DELIVERED);
        }

        return gateway.update(order);
    }

    private void sendNotificationToClient(String numberOrder, StatusOrder status) {
        String msg = "[Notification] O pedido de número: " + numberOrder +" - Status: "+ status + ".";
        logger.info(msg);

        NotificationRepositoryDB notification = new NotificationRepositoryDB( numberOrder, msg,  status, new Date() );
        notificationRepository.save(notification);
    }
}
