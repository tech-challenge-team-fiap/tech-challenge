package br.com.fiap.techchallenge.infrastructure.gateway;

import br.com.fiap.techchallenge.adapter.driven.entities.Product;
import br.com.fiap.techchallenge.common.enums.TypeProduct;
import br.com.fiap.techchallenge.common.enums.TypeStatus;
import br.com.fiap.techchallenge.common.exception.BaseException;
import br.com.fiap.techchallenge.infrastructure.out.ProductRepository;
import br.com.fiap.techchallenge.infrastructure.repository.ProductRepositoryDb;
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

            ProductRepositoryDb prod = new ProductRepositoryDb(
                    product.getName(),
                    product.getDescription(),
                    product.getQuantity(),
                    product.getTypeProduct(),
                    product.getPrice(),
                    product.getTypeStatus(),
                    product.getDateRegister()
            );

            return ResponseEntity.ok(productRepository.save(prod));
        } catch (Exception e) {
            logger.error("Error registering new product", e);
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity findAll(TypeProduct typeProduct) {
        List<ProductRepositoryDb> list = new ArrayList<>();
        productRepository.findByTypeProductAndTypeStatus(typeProduct, TypeStatus.ACTIVE)
        .forEach(it -> {
            list.add(new ProductRepositoryDb( it.getId(), it.getName(), it.getDescription(), it.getQuantity(), it.getTypeProduct(), it.getPrice(), it.getTypeStatus(), it.getDateRegister() ));
        });

        return new ResponseEntity(list , HttpStatus.OK);
    }

    public ResponseEntity<Integer> edit(ProductRepositoryDb product) {
        ProductRepositoryDb productUpdate = new ProductRepositoryDb(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getQuantity(),
                product.getTypeProduct(),
                product.getPrice(),
                product.getTypeStatus(),
                product.getDateRegister()
        );

        productRepository.save(productUpdate);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Integer> remove(ProductRepositoryDb product){
        product.setTypeStatus(TypeStatus.INACTIVE);
        productRepository.save(product);

        return ResponseEntity.ok().build();
    }

}