package br.com.fiap.techchallenge.useCase.product;

import br.com.fiap.techchallenge.common.enums.TypeProduct;
import br.com.fiap.techchallenge.common.exception.products.ProductTypeNotFoundException;
import br.com.fiap.techchallenge.db.dto.product.ProductDto;
import br.com.fiap.techchallenge.common.exception.InvalidProcessException;
import br.com.fiap.techchallenge.infrastructure.repository.ProductRepositoryDb;
import br.com.fiap.techchallenge.interfaces.ProductGatewayInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ListProductUseCase {

    private final ProductGatewayInterface productGateway;

    public ListProductUseCase(ProductGatewayInterface productGatewayInterface) {
        this.productGateway = productGatewayInterface;
    }

    public List<ProductDto> findByProductType(String productType) throws InvalidProcessException {
        return Optional.ofNullable(this.productGateway.findByProductType(productType))
                .map(products -> products.stream()
                        .map(ProductDto::toDto)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new ProductTypeNotFoundException(TypeProduct.valueOf(productType)));
    }

    public List<ProductDto> findAll() {
        return this.productGateway
                .findAll()
                .stream()
                .map(ProductDto::toDto)
                .collect(Collectors.toList());
    }
}