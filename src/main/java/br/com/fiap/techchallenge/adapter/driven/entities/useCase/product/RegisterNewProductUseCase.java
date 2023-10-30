package br.com.fiap.techchallenge.adapter.driven.entities.useCase.product;

import br.com.fiap.techchallenge.adapter.driven.entities.Product;
import br.com.fiap.techchallenge.adapter.driven.entities.form.ProductFormDto;
import br.com.fiap.techchallenge.common.enums.TypeStatus;
import br.com.fiap.techchallenge.common.exception.BaseException;
import br.com.fiap.techchallenge.common.exception.products.InvalidProductsProcessException;
import br.com.fiap.techchallenge.common.exception.products.InvalidQuantityException;
import br.com.fiap.techchallenge.infrastructure.gateway.ProductGateway;
import br.com.fiap.techchallenge.infrastructure.out.ProductRepository;
import br.com.fiap.techchallenge.infrastructure.repository.ProductRepositoryDb;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RegisterNewProductUseCase extends AbstractProductUserCase {

    private static final Logger logger = LoggerFactory.getLogger(RegisterNewProductUseCase.class);

    private final ProductGateway productGateway;

    private final ProductRepository productRepository;

    @Autowired
    public RegisterNewProductUseCase(ProductGateway productGateway, ProductRepository productRepository) {
        this.productGateway = productGateway;
        this.productRepository = productRepository;
    }

    public ResponseEntity<ProductRepositoryDb> register(final ProductFormDto productFormDto) throws InvalidProductsProcessException {
        validateQuantity(productFormDto.getQuantity());

        productFormDto.setTypeStatus(TypeStatus.ACTIVE);
        productFormDto.setDateRegister(LocalDateTime.now());

        return productGateway.register(new Product(productFormDto));
    }
}
