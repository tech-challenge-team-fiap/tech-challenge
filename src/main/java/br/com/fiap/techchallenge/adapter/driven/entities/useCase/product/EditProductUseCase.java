package br.com.fiap.techchallenge.adapter.driven.entities.useCase.product;

import br.com.fiap.techchallenge.adapter.driven.entities.form.ProductEditFormDto;
import br.com.fiap.techchallenge.common.exception.BaseException;
import br.com.fiap.techchallenge.infrastructure.gateway.ClientGateway;
import br.com.fiap.techchallenge.infrastructure.gateway.ProductGateway;
import br.com.fiap.techchallenge.infrastructure.out.ProductRepository;
import br.com.fiap.techchallenge.infrastructure.repository.ProductRepositoryDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EditProductUseCase {

    private static final Logger logger = LoggerFactory.getLogger(EditProductUseCase.class);
    private final ProductGateway productGateway;
    private final ProductRepository productRepository;

    @Autowired
    public EditProductUseCase(ProductGateway productGateway, ProductRepository productRepository) {
        this.productGateway = productGateway;
        this.productRepository = productRepository;
    }

    public ResponseEntity<Integer> edit(final ProductEditFormDto productFormEditDto) {
        ProductRepositoryDb productDB = productRepository
                .findById(productFormEditDto.getId())
                .orElseThrow(() -> new BaseException("Product with ID " + productFormEditDto.getId() + " does not exist!"));

        productDB.updateFrom(productFormEditDto);

        return productGateway.edit(productDB);
    }
}