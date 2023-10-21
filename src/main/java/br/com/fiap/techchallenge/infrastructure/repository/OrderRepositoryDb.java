package br.com.fiap.techchallenge.infrastructure.repository;

import br.com.fiap.techchallenge.common.enums.PaymentsType;
import br.com.fiap.techchallenge.common.enums.StatusOrder;
import br.com.fiap.techchallenge.common.enums.TypeProduct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ORDER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRepositoryDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private ClientRepositoryDb client;

    private String numberOrder;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name="statusOrder", length=250)
    @Enumerated(EnumType.STRING)
    private StatusOrder statusOrder;

    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private PaymentsType paymentsType;

    private Integer paymentId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDelivered;

    @CreationTimestamp
    private Date dateLastUpdate;

    @ManyToMany
    @JoinTable(
            name = "ORDER_PRODUCT",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<ProductRepositoryDb> products;

    public OrderRepositoryDb(ClientRepositoryDb client, String numberOrder, Date date, StatusOrder statusOrder, BigDecimal total, PaymentsType paymentsType, Date dateDelivered, Date dateLastUpdate, List<ProductRepositoryDb> products) {
        this.client = client;
        this.numberOrder = numberOrder;
        this.date = date;
        this.statusOrder = statusOrder;
        this.total = total;
        this.paymentsType = paymentsType;
        this.dateDelivered = dateDelivered;
        this.dateLastUpdate = dateLastUpdate;
        this.products = products;
    }
}