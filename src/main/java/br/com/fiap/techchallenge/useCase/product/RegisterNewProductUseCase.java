package br.com.fiap.techchallenge.useCase.product;

import br.com.fiap.techchallenge.db.dto.product.ProductDto;
import br.com.fiap.techchallenge.entities.Product;
import br.com.fiap.techchallenge.db.dto.product.ProductFormDto;
import br.com.fiap.techchallenge.common.enums.TypeStatus;
import br.com.fiap.techchallenge.common.exception.products.InvalidProductsProcessException;
import br.com.fiap.techchallenge.gateway.ProductGateway;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegisterNewProductUseCase extends AbstractProductUserCase {

    private static final Logger logger = LoggerFactory.getLogger(RegisterNewProductUseCase.class);

    private final ProductGateway productGateway;

    @Autowired
    public RegisterNewProductUseCase(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    public ProductDto register(final ProductFormDto productFormDto) throws InvalidProductsProcessException {
        validateQuantity(productFormDto.getQuantity());
        validatePrice(productFormDto.getPrice());

        productFormDto.setTypeStatus(TypeStatus.ACTIVE);
        productFormDto.setDateRegister(LocalDateTime.now());

        return productGateway.register(new Product(productFormDto));
    }
}
