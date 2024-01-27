package br.com.fiap.techchallenge.adapter.gateways;

import br.com.fiap.techchallenge.application.dto.product.ProductDto;
import br.com.fiap.techchallenge.domain.exception.products.InvalidProductsProcessException;
import br.com.fiap.techchallenge.domain.model.Product;
import br.com.fiap.techchallenge.external.infrastructure.entities.ProductDB;

import java.util.List;
import java.util.UUID;

public interface ProductGatewayInterface {

    ProductDto register(Product product) throws Exception;

    List<ProductDB> findByProductType(String type) throws InvalidProductsProcessException;

    ProductDto edit(ProductDB product);

    ProductDto remove(ProductDB product);

    List<ProductDB> findAll();

    ProductDB findById(UUID id);
}