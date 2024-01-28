package br.com.fiap.techchallenge.adapter.gateways;

import br.com.fiap.techchallenge.application.dto.order.OrderFormDto;
import br.com.fiap.techchallenge.application.dto.order.OrderListDto;
import br.com.fiap.techchallenge.application.dto.order.OrderResultFormDto;
import br.com.fiap.techchallenge.domain.exception.InvalidProcessException;
import br.com.fiap.techchallenge.domain.exception.order.InvalidOrderProcessException;
import br.com.fiap.techchallenge.domain.exception.order.OrderNotFoundException;
import br.com.fiap.techchallenge.external.infrastructure.entities.ClientDB;
import br.com.fiap.techchallenge.external.infrastructure.entities.NotificationDB;
import br.com.fiap.techchallenge.external.infrastructure.entities.OrderDB;
import br.com.fiap.techchallenge.external.infrastructure.entities.OrderQueueDB;

import java.util.List;

public interface OrderGatewayInterface {

    OrderResultFormDto register(final OrderFormDto orderFormDto, ClientDB client) throws InvalidProcessException;

    OrderResultFormDto update(OrderDB order) throws InvalidOrderProcessException;

    List<OrderListDto> findAll();

    List<OrderDB> findAll(String status);

    List<OrderListDto> getByStatusPayments(boolean isPayments);

    void saveOrder(OrderQueueDB queue);

    void sendNotification(NotificationDB notification);

    OrderResultFormDto checkStatusPayments(String numberOrder) throws OrderNotFoundException;
}