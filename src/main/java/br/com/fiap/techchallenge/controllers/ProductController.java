package br.com.fiap.techchallenge.controllers;

import static br.com.fiap.techchallenge.common.utils.ProblemAware.problemOf;

import br.com.fiap.techchallenge.db.dto.product.ProductDto;
import br.com.fiap.techchallenge.db.dto.product.ProductEditFormDto;
import br.com.fiap.techchallenge.db.dto.product.ProductFormDto;
import br.com.fiap.techchallenge.useCase.product.EditProductUseCase;
import br.com.fiap.techchallenge.useCase.product.ListProductUseCase;
import br.com.fiap.techchallenge.useCase.product.RegisterNewProductUseCase;
import br.com.fiap.techchallenge.useCase.product.RemoveProductUseCase;
import br.com.fiap.techchallenge.common.exception.InvalidProcessException;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final EditProductUseCase editProductUseCase;

    private final RemoveProductUseCase removeProductUseCase;

    private final RegisterNewProductUseCase registerNewProductUseCase;

    private final ListProductUseCase listProductUseCase;

    public ProductController(RegisterNewProductUseCase registerNewProductUseCase, EditProductUseCase editProductUseCase, RemoveProductUseCase removeProductUseCase, ListProductUseCase listProductUseCase) {
        this.registerNewProductUseCase = registerNewProductUseCase;
        this.editProductUseCase = editProductUseCase;
        this.removeProductUseCase = removeProductUseCase;
        this.listProductUseCase = listProductUseCase;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody ProductFormDto productFormDto) {
        try {
            return ResponseEntity.ok(registerNewProductUseCase.register(productFormDto));
        } catch (InvalidProcessException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }

    @PutMapping()
    public ResponseEntity<?> edit(@RequestBody ProductEditFormDto product) {
        try {
            return ResponseEntity.ok(editProductUseCase.edit(product));
        } catch (InvalidProcessException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(removeProductUseCase.remove(id));
        } catch (InvalidProcessException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }

    @GetMapping("/{productType}")
    @Transactional
    public ResponseEntity<?> findAll(@PathVariable("productType") String productType) {
        try {
            return ResponseEntity.ok(listProductUseCase.findByProductType(productType));
        } catch (InvalidProcessException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }

    @GetMapping
    @Transactional
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(listProductUseCase.findAll());
    }
}