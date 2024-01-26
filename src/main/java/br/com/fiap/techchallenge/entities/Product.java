package br.com.fiap.techchallenge.entities;

import br.com.fiap.techchallenge.db.dto.product.ProductFormDto;
import br.com.fiap.techchallenge.common.enums.TypeProduct;
import br.com.fiap.techchallenge.common.enums.TypeStatus;
import br.com.fiap.techchallenge.infrastructure.repository.ProductRepositoryDb;
import java.time.LocalDateTime;
import br.com.fiap.techchallenge.infrastructure.repository.OrderRepositoryDb;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

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

    private LocalDateTime dateRegister;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private OrderRepositoryDb order;

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

    public ProductRepositoryDb build() {
        return ProductRepositoryDb.builder()
                                  .name(getName())
                                  .description(getDescription())
                                  .quantity(getQuantity())
                                  .typeProduct(getTypeProduct())
                                  .price(getPrice())
                                  .typeStatus(getTypeStatus())
                                  .dateRegister(getDateRegister())
                                  .build();
    }
}
