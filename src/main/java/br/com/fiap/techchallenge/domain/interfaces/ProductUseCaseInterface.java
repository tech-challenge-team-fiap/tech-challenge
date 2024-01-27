package br.com.fiap.techchallenge.domain.interfaces;

import br.com.fiap.techchallenge.application.dto.product.ProductDto;
import br.com.fiap.techchallenge.application.dto.product.ProductEditFormDto;
import br.com.fiap.techchallenge.application.dto.product.ProductFormDto;
import br.com.fiap.techchallenge.domain.exception.InvalidProcessException;
import br.com.fiap.techchallenge.domain.exception.products.InvalidProductsProcessException;

import java.util.List;
import java.util.UUID;

public interface ProductUseCaseInterface {

    ProductDto edit(final ProductEditFormDto productFormEditDto) throws InvalidProductsProcessException;

    List<ProductDto> findByProductType(String productType) throws InvalidProcessException;

    List<ProductDto> findAll();

    ProductDto register(final ProductFormDto productFormDto) throws InvalidProductsProcessException;

    ProductDto remove(final UUID id) throws InvalidProductsProcessException;
}
