package br.com.fiap.techchallenge.adapter.driven.entities.useCase.product;

import br.com.fiap.techchallenge.adapter.driven.entities.Product;
import br.com.fiap.techchallenge.adapter.driven.entities.form.ProductFormDto;
import br.com.fiap.techchallenge.common.enums.TypeStatus;
import br.com.fiap.techchallenge.common.exception.BaseException;
import br.com.fiap.techchallenge.infrastructure.gateway.ProductGateway;
import br.com.fiap.techchallenge.infrastructure.out.ProductRepository;
import br.com.fiap.techchallenge.infrastructure.repository.ProductRepositoryDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RegisterNewProductUseCase {

    private static final Logger logger = LoggerFactory.getLogger(RegisterNewProductUseCase.class);

    private final ProductGateway productGateway;

    private final ProductRepository productRepository;

    @Autowired
    public RegisterNewProductUseCase(ProductGateway productGateway, ProductRepository productRepository) {
        this.productGateway = productGateway;
        this.productRepository = productRepository;
    }

    public ResponseEntity<ProductRepositoryDb> register(final ProductFormDto productFormDto){
        validateQuantity(productFormDto.getQuantity());

        productFormDto.setTypeStatus(TypeStatus.ACTIVE);
        productFormDto.setDateRegister(new Date());

        return productGateway.register(new Product(productFormDto));
    }

    private void validateQuantity(java.lang.Integer quantity){
        if(quantity == 0){
            throw new BaseException("Quantity invalid save to product");
        }
    }
}
