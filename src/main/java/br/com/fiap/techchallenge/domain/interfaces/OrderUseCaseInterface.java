package br.com.fiap.techchallenge.domain.interfaces;

import br.com.fiap.techchallenge.application.dto.order.OrderFormDto;
import br.com.fiap.techchallenge.application.dto.order.OrderListDto;
import br.com.fiap.techchallenge.application.dto.order.OrderResultFormDto;
import br.com.fiap.techchallenge.domain.exception.InvalidProcessException;
import br.com.fiap.techchallenge.domain.exception.order.InvalidOrderProcessException;
import br.com.fiap.techchallenge.domain.exception.order.OrderNotFoundException;
import br.com.fiap.techchallenge.external.infrastructure.entities.OrderDB;

import java.util.List;

public interface OrderUseCaseInterface {

    OrderResultFormDto register(final OrderFormDto orderFormDto) throws InvalidProcessException;

    OrderResultFormDto update(String numberOrder, String status) throws InvalidOrderProcessException;

    List<OrderListDto> findAll();

    List<OrderDB> findAll(String status);

    List<OrderListDto> getByStatusPayments(boolean isPayments);

    OrderResultFormDto checkStatusPayments(String numberOrder) throws OrderNotFoundException;
}
