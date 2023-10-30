package br.com.fiap.techchallenge.infrastructure.gateway;

import br.com.fiap.techchallenge.adapter.driven.entities.Product;
import br.com.fiap.techchallenge.common.enums.TypeProduct;
import br.com.fiap.techchallenge.common.enums.TypeStatus;
import br.com.fiap.techchallenge.common.exception.products.InvalidProductTypeException;
import br.com.fiap.techchallenge.common.exception.products.InvalidProductsProcessException;
import br.com.fiap.techchallenge.common.exception.products.ProductTypeNotFoundException;
import br.com.fiap.techchallenge.infrastructure.out.ProductRepository;
import br.com.fiap.techchallenge.infrastructure.repository.ProductRepositoryDb;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductGateway {

    private static final Logger logger = LoggerFactory.getLogger(ProductGateway.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductGateway(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public ResponseEntity<ProductRepositoryDb> register(Product product) {
        try {
            ProductRepositoryDb prod = product.build();

            return ResponseEntity.ok(productRepository.save(prod));
        } catch (Exception e) {
            logger.error("Error registering new product", e);
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity findByProductType() {
        List<ProductRepositoryDb> list = new ArrayList<>();
        productRepository.findAll()
            .forEach(it -> {
                list.add(new ProductRepositoryDb( it.getId(), it.getName(), it.getDescription(), it.getQuantity(), it.getTypeProduct(), it.getPrice(), it.getTypeStatus(), it.getDateRegister() ));
            });

        return new ResponseEntity(list , HttpStatus.OK);
    }

    private TypeProduct typeProduct(String type) {
        try {
            return TypeProduct.valueOf(type);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public ResponseEntity findByProductType(String type) throws InvalidProductsProcessException {
        try {
            TypeProduct typeProduct = typeProduct(type);
            List<ProductRepositoryDb> list = new ArrayList<>();
            Optional<List<ProductRepositoryDb>> products = productRepository.findByTypeProductAndTypeStatus(typeProduct, TypeStatus.ACTIVE);

            if (products.isPresent()) {
                products.get()
                    .forEach(it -> {
                        list.add(new ProductRepositoryDb(it.getId(), it.getName(), it.getDescription(), it.getQuantity(), it.getTypeProduct(), it.getPrice(), it.getTypeStatus(), it.getDateRegister()));
                    });

                return new ResponseEntity(list, HttpStatus.OK);
            } else {
                throw new ProductTypeNotFoundException(typeProduct);
            }
        } catch (RuntimeException ex) {
            throw new InvalidProductTypeException(type);
        }
    }

    public ResponseEntity<Integer> edit(ProductRepositoryDb product) {
        productRepository.save(product);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Integer> remove(ProductRepositoryDb product){
        product.setTypeStatus(TypeStatus.INACTIVE);
        productRepository.save(product);

        return ResponseEntity.ok().build();
    }

}