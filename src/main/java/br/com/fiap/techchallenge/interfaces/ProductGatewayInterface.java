package br.com.fiap.techchallenge.interfaces;

import br.com.fiap.techchallenge.db.dto.product.ProductDto;
import br.com.fiap.techchallenge.common.exception.products.InvalidProductsProcessException;
import br.com.fiap.techchallenge.entities.Product;
import br.com.fiap.techchallenge.infrastructure.repository.ProductRepositoryDb;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ProductGatewayInterface {

    ProductDto register(Product product) throws Exception;

    List<ProductRepositoryDb> findByProductType(String type) throws InvalidProductsProcessException;

    ProductDto edit(ProductRepositoryDb product);

    ProductDto remove(ProductRepositoryDb product);

    List<ProductRepositoryDb> findAll();

    ProductRepositoryDb findById(UUID id);
}