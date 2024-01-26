package br.com.fiap.techchallenge.useCase.product;

import br.com.fiap.techchallenge.db.dto.product.ProductDto;
import br.com.fiap.techchallenge.db.dto.product.ProductEditFormDto;
import br.com.fiap.techchallenge.common.exception.products.InvalidProductsProcessException;
import br.com.fiap.techchallenge.common.exception.products.ProductNotFoundException;
import br.com.fiap.techchallenge.gateway.ProductGateway;
import br.com.fiap.techchallenge.infrastructure.out.ProductRepository;
import br.com.fiap.techchallenge.infrastructure.repository.ProductRepositoryDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EditProductUseCase extends AbstractProductUserCase {

    private static final Logger logger = LoggerFactory.getLogger(EditProductUseCase.class);
    private final ProductGateway productGateway;

    @Autowired
    public EditProductUseCase(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    public ProductDto edit(final ProductEditFormDto productFormEditDto) throws InvalidProductsProcessException {
        validateQuantity(productFormEditDto.getQuantity());
        validatePrice(productFormEditDto.getPrice());

        ProductRepositoryDb productDB = Optional.ofNullable(productGateway.findById(productFormEditDto.getId()))
                .orElseThrow(() -> new ProductNotFoundException(productFormEditDto.getId()));

        productDB.updateFrom(productFormEditDto);

        logger.info("[Edit] - Product edit sucessfull.");
        return productGateway.edit(productDB);
    }
}