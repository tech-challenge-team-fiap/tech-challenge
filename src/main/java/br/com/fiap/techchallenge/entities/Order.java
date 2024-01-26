package br.com.fiap.techchallenge.entities;

import br.com.fiap.techchallenge.common.enums.PaymentsType;
import br.com.fiap.techchallenge.common.enums.StatusOrder;
import br.com.fiap.techchallenge.common.enums.TypeProduct;
import br.com.fiap.techchallenge.infrastructure.repository.ClientRepositoryDb;
import br.com.fiap.techchallenge.infrastructure.repository.OrderRepositoryDb;
import br.com.fiap.techchallenge.infrastructure.repository.ProductRepositoryDb;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Order {

    private ClientRepositoryDb client;

    private String numberOrder;

    private Date date;

    private StatusOrder statusOrder;

    private BigDecimal total;

    private PaymentsType paymentsType;

    private TypeProduct typeProduct;

    private Integer paymentId;

    private LocalDateTime dateDelivered;

    private LocalDateTime dateLastUpdate;

    private List<ProductRepositoryDb> products;

    public Order(ClientRepositoryDb client, String numberOrder, Date date, StatusOrder statusOrder, BigDecimal total, PaymentsType paymentsType, Integer paymentId, LocalDateTime dateDelivered, LocalDateTime dateLastUpdate, List<ProductRepositoryDb> products) {
        this.client = client;
        this.numberOrder = numberOrder;
        this.date = date;
        this.statusOrder = statusOrder;
        this.total = total;
        this.paymentsType = paymentsType;
        this.paymentId = paymentId;
        this.dateDelivered = dateDelivered;
        this.dateLastUpdate = dateLastUpdate;
        this.products = products;
    }

    public OrderRepositoryDb build() {
        return OrderRepositoryDb.builder()
                                .client(getClient())
                                .numberOrder(getNumberOrder())
                                .date(new Date())
                                .statusOrder(getStatusOrder())
                                .total(getTotal())
                                .paymentsType(getPaymentsType())
                                .dateDelivered(null)
                                .dateLastUpdate(LocalDateTime.now())
                                .products(getProducts())
                                .build();
    }
}