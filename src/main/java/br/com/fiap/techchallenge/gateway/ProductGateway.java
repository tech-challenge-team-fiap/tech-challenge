package br.com.fiap.techchallenge.gateway;

import br.com.fiap.techchallenge.db.dto.product.ProductDto;
import br.com.fiap.techchallenge.entities.Product;
import br.com.fiap.techchallenge.common.enums.TypeProduct;
import br.com.fiap.techchallenge.common.enums.TypeStatus;
import br.com.fiap.techchallenge.common.exception.products.InvalidProductTypeException;
import br.com.fiap.techchallenge.common.exception.products.InvalidProductsProcessException;
import br.com.fiap.techchallenge.infrastructure.out.ProductRepository;
import br.com.fiap.techchallenge.infrastructure.repository.ProductRepositoryDb;

import br.com.fiap.techchallenge.interfaces.ProductGatewayInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class ProductGateway implements ProductGatewayInterface {

    private static final Logger logger = LoggerFactory.getLogger(ProductGateway.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductGateway(ProductRepository productRepository){
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
    public List<ProductRepositoryDb> findByProductType(String type) throws InvalidProductsProcessException {
        try {
            return productRepository.findByTypeProductAndTypeStatus(typeProduct(type), TypeStatus.ACTIVE);
        } catch (RuntimeException ex) {
            throw new InvalidProductTypeException(type);
        }
    }

    @Override
    public ProductDto edit(ProductRepositoryDb product) {
        return new ProductDto(productRepository.save(product).getId());
    }

    @Override
    public ProductDto remove(ProductRepositoryDb product){
        product.setTypeStatus(TypeStatus.INACTIVE);
        return new ProductDto(productRepository.save(product).getId());
    }
    @Override
    public List<ProductRepositoryDb> findAll() {
        return productRepository.findAll();
    }

    @Override
    public ProductRepositoryDb findById(UUID id) {
        return productRepository.findAllById(id);
    }
}