package br.com.fiap.techchallenge.application.dto.product;

import br.com.fiap.techchallenge.domain.enums.TypeProduct;
import br.com.fiap.techchallenge.domain.enums.TypeStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Valid
public class ProductFormDto {

    private String name;

    private String description;

    private Integer quantity;

    private TypeProduct typeProduct;

    @Positive
    private BigDecimal price;

    private TypeStatus typeStatus;

    private LocalDateTime dateRegister;

    public ProductFormDto(String name, String description, Integer quantity, TypeProduct typeProduct, BigDecimal price, TypeStatus typeStatus, LocalDateTime dateRegister) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.typeProduct = typeProduct;
        this.price = price;
        this.typeStatus = typeStatus;
        this.dateRegister = dateRegister;
    }
}