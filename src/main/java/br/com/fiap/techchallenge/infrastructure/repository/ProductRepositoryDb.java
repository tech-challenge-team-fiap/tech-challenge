package br.com.fiap.techchallenge.infrastructure.repository;

import br.com.fiap.techchallenge.adapter.driven.entities.form.ProductEditFormDto;
import br.com.fiap.techchallenge.common.enums.TypeProduct;
import br.com.fiap.techchallenge.common.enums.TypeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "PRODUCT")
@Getter
@Setter
public class ProductRepositoryDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private TypeProduct typeProduct;

    private BigDecimal price;

    @Column(name="typeStatus", length=250)
    @Enumerated(EnumType.STRING)
    private TypeStatus typeStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegister;

    public ProductRepositoryDb(){

    }

    public ProductRepositoryDb(String name, String description, Integer quantity, TypeProduct typeProduct,  BigDecimal price, TypeStatus typeStatus, Date dateRegister) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.typeProduct = typeProduct;
        this.price = price;
        this.typeStatus = typeStatus;
        this.dateRegister = dateRegister;
    }

    public ProductRepositoryDb(Integer id, String name, String description, Integer quantity, TypeProduct typeProduct, BigDecimal price, TypeStatus typeStatus, Date dateRegister) {
        this.id = id;
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