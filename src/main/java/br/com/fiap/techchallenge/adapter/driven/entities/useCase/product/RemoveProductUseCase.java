package br.com.fiap.techchallenge.adapter.driven.entities.useCase.product;

import br.com.fiap.techchallenge.common.exception.BaseException;
import br.com.fiap.techchallenge.infrastructure.gateway.ProductGateway;
import br.com.fiap.techchallenge.infrastructure.out.ProductRepository;
import br.com.fiap.techchallenge.infrastructure.repository.ProductRepositoryDb;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RemoveProductUseCase {

    private static final Logger logger = LoggerFactory.getLogger(RemoveProductUseCase.class);
    private final ProductGateway productGateway;
    private final ProductRepository productRepository;

    @Autowired
    public RemoveProductUseCase(ProductGateway productGateway, ProductRepository productRepository) {
        this.productGateway = productGateway;
        this.productRepository = productRepository;
    }

    public ResponseEntity<Integer> remove(final UUID id) {
        ProductRepositoryDb productDB = productRepository.findById(id)
                .orElseThrow(() -> new BaseException("Product with ID " + id + " does not exist!"));

        return productGateway.remove(productDB);
    }
}
