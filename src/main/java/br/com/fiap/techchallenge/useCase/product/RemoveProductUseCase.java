package br.com.fiap.techchallenge.useCase.product;

import br.com.fiap.techchallenge.common.exception.products.InvalidProductsProcessException;
import br.com.fiap.techchallenge.common.exception.products.ProductNotFoundException;
import br.com.fiap.techchallenge.db.dto.product.ProductDto;
import br.com.fiap.techchallenge.gateway.ProductGateway;
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

    @Autowired
    public RemoveProductUseCase(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    public ProductDto remove(final UUID id) throws InvalidProductsProcessException {
        ProductRepositoryDb productDB = productGateway.findById(id);

        if(productDB == null){
            throw new ProductNotFoundException(id);
        }

        logger.info("[Remove] - Product remove sucessfull.");
        return productGateway.remove(productDB);
    }
}
