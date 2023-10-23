package br.com.fiap.techchallenge.infrastructure.repository;

import br.com.fiap.techchallenge.adapter.driven.entities.form.ProductEditFormDto;
import br.com.fiap.techchallenge.common.enums.TypeProduct;
import br.com.fiap.techchallenge.common.enums.TypeStatus;
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

import java.math.BigDecimal;
import java.util.Date;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "PRODUCT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class ProductRepositoryDb {

    @Id
    @Builder.Default
    @Type(NumericRepresentationUUIDType.class)
    @Column(name = "ID")
    @NotNull
    @EqualsAndHashCode.Include
    private UUID id = nextId();

    @Column(name = "NAME")
    @EqualsAndHashCode.Include
    private String name;

    @Column(name = "DESCRIPTION")
    @EqualsAndHashCode.Exclude
    private String description;

    @Column(name = "QUANTITY")
    @EqualsAndHashCode.Exclude
    private Integer quantity;

    @Column(name = "TYPE_PRODUCT")
    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Exclude
    private TypeProduct typeProduct;

    @Column(name = "PRICE")
    @EqualsAndHashCode.Exclude
    private BigDecimal price;

    @Column(name = "TYPE_STATUS")
    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Exclude
    private TypeStatus typeStatus;

    @Column(name = "DATE_REGISTER")
    @EqualsAndHashCode.Exclude
    private LocalDateTime dateRegister;

    @NotNull
    private static UUID nextId() {
        return UlidCreator.getMonotonicUlid().toUuid();
    }

    public ProductRepositoryDb(String name, String description, Integer quantity, TypeProduct typeProduct,  BigDecimal price, TypeStatus typeStatus, LocalDateTime dateRegister) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.typeProduct = typeProduct;
        this.price = price;
        this.typeStatus = typeStatus;
        this.dateRegister = dateRegister;
    }

    public void updateFrom(ProductEditFormDto productFormEditDto) {
        this.name = productFormEditDto.getName();
        this.description = productFormEditDto.getDescription();
        this.quantity = productFormEditDto.getQuantity();
        this.typeProduct = productFormEditDto.getTypeProduct();
        this.price = productFormEditDto.getPrice();
        this.typeStatus = productFormEditDto.getTypeStatus();
    }
}