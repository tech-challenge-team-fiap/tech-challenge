package br.com.fiap.techchallenge.db.dto.order;

import br.com.fiap.techchallenge.common.enums.PaymentsType;
import br.com.fiap.techchallenge.infrastructure.repository.OrderQueueRepositoryDB;
import br.com.fiap.techchallenge.infrastructure.repository.OrderRepositoryDb;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderListDto {

    private String numberOrder;

    private String date;

    private String statusOrder;

    private String paymentsType;


    public OrderListDto (OrderQueueRepositoryDB orderQueue) {
        this.setNumberOrder(orderQueue.getNumberOrder());
        this.setDate(String.valueOf(orderQueue.getDateRegister()));
        this.setStatusOrder(orderQueue.getStatusOrder().name());
        this.setPaymentsType(PaymentsType.QR_CODE.name());
    }

    public OrderListDto(OrderRepositoryDb order) {
        this.setNumberOrder(order.getNumberOrder());
        this.setDate(String.valueOf(order.getDate().getTime()));
        this.setStatusOrder(order.getStatusOrder().name());
        this.setPaymentsType(PaymentsType.QR_CODE.name());
    }
}