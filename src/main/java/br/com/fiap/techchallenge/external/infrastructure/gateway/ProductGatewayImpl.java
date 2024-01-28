package br.com.fiap.techchallenge.external.infrastructure.gateway;

import br.com.fiap.techchallenge.application.dto.product.ProductDto;
import br.com.fiap.techchallenge.domain.model.Product;
import br.com.fiap.techchallenge.domain.enums.TypeProduct;
import br.com.fiap.techchallenge.domain.enums.TypeStatus;
import br.com.fiap.techchallenge.domain.exception.products.InvalidProductTypeException;
import br.com.fiap.techchallenge.domain.exception.products.InvalidProductsProcessException;
import br.com.fiap.techchallenge.external.infrastructure.repositories.ProductRepository;
import br.com.fiap.techchallenge.external.infrastructure.entities.ProductDB;

import br.com.fiap.techchallenge.adapter.gateways.ProductGatewayInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Component
public class ProductGatewayImpl implements ProductGatewayInterface {

    private static final Logger logger = LoggerFactory.getLogger(ProductGatewayImpl.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductGatewayImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto register(Product product) {
        try {
            return new ProductDto(productRepository.save(product.build()).getId());
        } catch (Exception ex) {
            logger.error("Error registering new product", ex);
            throw new RuntimeException(ex);
        }
    }
    private TypeProduct typeProduct(String type) {
        try {
            return TypeProduct.valueOf(type);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    @Override
    public List<ProductDB> findByProductType(String type) throws InvalidProductsProcessException {
        try {
            return productRepository.findByTypeProductAndTypeStatus(typeProduct(type), TypeStatus.ACTIVE);
        } catch (RuntimeException ex) {
            throw new InvalidProductTypeException(type);
        }
    }

    @Override
    public ProductDto edit(ProductDB product) {
        return new ProductDto(productRepository.save(product).getId());
    }

    @Override
    public ProductDto remove(ProductDB product){
        product.setTypeStatus(TypeStatus.INACTIVE);
        return new ProductDto(productRepository.save(product).getId());
    }
    @Override
    public List<ProductDB> findAll() {
        return productRepository.findAll();
    }

    @Override
    public ProductDB findById(UUID id) {
        return productRepository.findAllById(id);
    }
}