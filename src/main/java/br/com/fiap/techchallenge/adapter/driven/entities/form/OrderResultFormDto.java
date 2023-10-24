package br.com.fiap.techchallenge.adapter.driven.entities.form;

import br.com.fiap.techchallenge.infrastructure.repository.OrderRepositoryDb;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResultFormDto {

    private String numberOrder;
    private Date date;
    private String statusOrder;
    private BigDecimal total;
    private String paymentsType;

    public OrderResultFormDto(OrderRepositoryDb order) {
        this.numberOrder = order.getNumberOrder();
        this.date = order.getDate();
        this.statusOrder = order.getStatusOrder().name();
        this.total = order.getTotal();
        this.paymentsType = order.getPaymentsType().name();
    }
}