package br.com.fiap.techchallenge.adapter.driven.entities;

import br.com.fiap.techchallenge.adapter.driven.entities.form.ProductFormDto;
import br.com.fiap.techchallenge.common.enums.TypeProduct;
import br.com.fiap.techchallenge.common.enums.TypeStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Product {

    private String name;

    private String description;

    private Integer quantity;

    private TypeProduct typeProduct;

    private String category;

    private BigDecimal price;

    private TypeStatus typeStatus;

    private Date dateRegister;

    public Product() {}

    public Product(ProductFormDto productFormDto) {
        this.name = productFormDto.getName();
        this.description = productFormDto.getDescription();
        this.quantity = productFormDto.getQuantity();
        this.typeProduct = productFormDto.getTypeProduct();
        this.price = productFormDto.getPrice();
        this.typeStatus = productFormDto.getTypeStatus();
        this.dateRegister = productFormDto.getDateRegister();
    }
}
