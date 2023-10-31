package br.com.fiap.techchallenge.infrastructure.in;

import static br.com.fiap.techchallenge.common.utils.ProblemAware.problemOf;

import br.com.fiap.techchallenge.adapter.driven.entities.form.ProductEditFormDto;
import br.com.fiap.techchallenge.adapter.driven.entities.form.ProductFormDto;
import br.com.fiap.techchallenge.adapter.driven.entities.useCase.product.EditProductUseCase;
import br.com.fiap.techchallenge.adapter.driven.entities.useCase.product.RegisterNewProductUseCase;
import br.com.fiap.techchallenge.adapter.driven.entities.useCase.product.RemoveProductUseCase;
import br.com.fiap.techchallenge.common.enums.TypeProduct;
import br.com.fiap.techchallenge.common.exception.InvalidProcessException;
import br.com.fiap.techchallenge.infrastructure.gateway.ProductGateway;
import br.com.fiap.techchallenge.infrastructure.repository.ProductRepositoryDb;
import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private EditProductUseCase editProductUseCase;

    private RemoveProductUseCase removeProductUseCase;

    private final RegisterNewProductUseCase registerNewProductUseCase;
    private final ProductGateway gateway;

    @Autowired
    public ProductController(RegisterNewProductUseCase registerNewProductUseCase, EditProductUseCase editProductUseCase, ProductGateway gateway, RemoveProductUseCase removeProductUseCase) {
        this.registerNewProductUseCase = registerNewProductUseCase;
        this.gateway = gateway;
        this.editProductUseCase = editProductUseCase;
        this.removeProductUseCase = removeProductUseCase;
    }

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody ProductFormDto productFormDto) {
        try {
            return registerNewProductUseCase.register(productFormDto);
        } catch (InvalidProcessException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }

    @PutMapping()
    public ResponseEntity edit(@RequestBody ProductEditFormDto product) {
        try {
            return editProductUseCase.edit(product);
        } catch (InvalidProcessException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity remove(@PathVariable UUID id) {
        try {
            return removeProductUseCase.remove(id);
        } catch (InvalidProcessException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }


    @GetMapping("/{productType}")
    @Transactional
    public ResponseEntity findAll(@PathVariable("productType") String productType) {
        try {
            return gateway.findByProductType(productType);
        } catch (InvalidProcessException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }

    @GetMapping
    @Transactional
    public ResponseEntity findAll() {
        return gateway.findByProductType();
    }

}
