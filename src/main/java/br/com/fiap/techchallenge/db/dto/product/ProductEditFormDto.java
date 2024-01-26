package br.com.fiap.techchallenge.db.dto.product;

import br.com.fiap.techchallenge.common.enums.TypeProduct;
import br.com.fiap.techchallenge.common.enums.TypeStatus;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ProductEditFormDto {

    private UUID id;
    private String name;

    private String description;

    private Integer quantity;

    private TypeProduct typeProduct;

    private BigDecimal price;

    private TypeStatus typeStatus;

    private Date dateRegister;

    public ProductEditFormDto(UUID id , String name, String description, Integer quantity, TypeProduct typeProduct, BigDecimal price, TypeStatus typeStatus, Date dateRegister) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.typeProduct = typeProduct;
        this.price = price;
        this.typeStatus = typeStatus;
        this.dateRegister = dateRegister;
    }
}