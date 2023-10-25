package br.com.fiap.techchallenge.common.component;

import br.com.fiap.techchallenge.adapter.driven.entities.useCase.order.RegisterNewOrderUseCase;
import br.com.fiap.techchallenge.common.enums.StatusOrder;
import br.com.fiap.techchallenge.infrastructure.out.NotificationRepository;
import br.com.fiap.techchallenge.infrastructure.out.OrderQueueRepository;
import br.com.fiap.techchallenge.infrastructure.out.OrderRepository;
import br.com.fiap.techchallenge.infrastructure.repository.NotificationRepositoryDB;
import br.com.fiap.techchallenge.infrastructure.repository.OrderQueueRepositoryDB;
import br.com.fiap.techchallenge.infrastructure.repository.OrderRepositoryDb;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
public class CronJob {

    private static final Logger logger = LoggerFactory.getLogger(CronJob.class);
    private OrderQueueRepository orderQueueRepository;
    private NotificationRepository notificationRepository;
    private OrderRepository orderRepository;

    @Autowired
    public CronJob(OrderQueueRepository orderQueueRepository, NotificationRepository notificationRepository, OrderRepository orderRepository){
        this.orderQueueRepository = orderQueueRepository;
        this.notificationRepository = notificationRepository;
        this.orderRepository = orderRepository;
    }

    @Scheduled(cron = "0 */1 * ? * *")
    public void finishedOrder() {
        System.out.println("=========== [COZINHA] FINALIZANDO PEDIDOS ========== ");

        OrderQueueRepositoryDB item = orderQueueRepository
                .findAllByStatusOrder(Sort.by(Sort.Direction.ASC, "dateRegister"), StatusOrder.IN_PREPARATION)
                .stream()
                .findFirst()
                .get();

        if(item != null && StatusOrder.IN_PREPARATION.name().equals(item.getStatusOrder().name().toUpperCase())) {
            logger.info("[FILA] Pedido Id:" +item.getId() + "/ Numero Pedido: " + item.getNumberOrder() + "/ Status Anterior:" + item.getStatusOrder());
            item.setStatusOrder(StatusOrder.READY);

            orderQueueRepository.save(item);


            NotificationRepositoryDB notification = new NotificationRepositoryDB( item.getNumberOrder(), "Finalizado o pedido: "+ item.getNumberOrder(),  StatusOrder.READY, LocalDateTime.now() );

            logger.info("[Notificacao] Notificação enviada ao cliente que o pedido esta PRONTO");
            notificationRepository.save(notification);

            OrderRepositoryDb order = orderRepository.findByNumberOrder(item.getNumberOrder()).get();
            order.setStatusOrder(StatusOrder.READY);
            order.setDateLastUpdate(LocalDateTime.now());

            logger.info("[FILA] Pedido Id:" +order.getId() + "/ Numero Pedido: " + order.getNumberOrder() + "/ Status Atual:" + order.getStatusOrder());
            orderRepository.save(order);
        }
    }
}