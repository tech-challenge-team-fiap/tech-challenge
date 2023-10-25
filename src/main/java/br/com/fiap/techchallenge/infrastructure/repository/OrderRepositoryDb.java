package br.com.fiap.techchallenge.infrastructure.repository;

import br.com.fiap.techchallenge.common.enums.PaymentsType;
import br.com.fiap.techchallenge.common.enums.StatusOrder;
import br.com.fiap.techchallenge.common.enums.TypeProduct;
import br.com.fiap.techchallenge.common.type.NumericRepresentationUUIDType;
import com.github.f4b6a3.ulid.UlidCreator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "ORDER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class OrderRepositoryDb {

    @Id
    @Builder.Default
    @Type(NumericRepresentationUUIDType.class)
    @Column(name = "ID")
    @NotNull
    @EqualsAndHashCode.Include
    private UUID id = nextId();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = ClientRepositoryDb.class)
    @JoinColumn(name = "CLIENT_ID")
    private ClientRepositoryDb client;

    @Column(name = "ORDER_NUMBER")
    private String numberOrder;

    @Column(name = "DATE")
    private Date date;

    @Column(name = "ORDER_STATUS")
    @Enumerated(EnumType.STRING)
    private StatusOrder statusOrder;

    @Column(name = "TOTAL")
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAYMENT_TYPE")
    private PaymentsType paymentsType;

    @Column(name = "PAYMENT_ID")
    private UUID paymentId;

    @Column(name = "DATE_DELIVERED")
    private LocalDateTime dateDelivered;

    @CreatedDate
    @Column(name = "UPDATED_AT")
    private LocalDateTime dateLastUpdate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ORDER_PRODUCT",
            joinColumns = @JoinColumn(name = "ORDER_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID")
    )
    private List<ProductRepositoryDb> products;

    @NotNull
    private static UUID nextId() {
        return UlidCreator.getMonotonicUlid().toUuid();
    }

    public OrderRepositoryDb(ClientRepositoryDb client, String numberOrder, Date date, StatusOrder statusOrder, BigDecimal total, PaymentsType paymentsType, LocalDateTime dateDelivered, LocalDateTime dateLastUpdate, List<ProductRepositoryDb> products) {

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