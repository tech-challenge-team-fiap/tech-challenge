package br.com.fiap.techchallenge.adapter.controllers;

import static br.com.fiap.techchallenge.domain.utils.ProblemAware.problemOf;

import br.com.fiap.techchallenge.application.dto.product.ProductEditFormDto;
import br.com.fiap.techchallenge.application.dto.product.ProductFormDto;
import br.com.fiap.techchallenge.domain.exception.InvalidProcessException;

import java.util.UUID;

import br.com.fiap.techchallenge.domain.interfaces.ProductUseCaseInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    private final ProductUseCaseInterface productUseCase;
    @Autowired
    public ProductController(ProductUseCaseInterface productUseCase) {
        this.productUseCase = productUseCase;
    }
    public ResponseEntity<?> register(@RequestBody ProductFormDto productFormDto) {
        try {
            return ResponseEntity.ok(productUseCase.register(productFormDto));
        } catch (InvalidProcessException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }
    public ResponseEntity<?> edit(@RequestBody ProductEditFormDto product) {
        try {
            return ResponseEntity.ok(productUseCase.edit(product));
        } catch (InvalidProcessException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }
    public ResponseEntity<?> remove(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(productUseCase.remove(id));
        } catch (InvalidProcessException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }
    public ResponseEntity<?> findByProductType(@PathVariable("productType") String productType) {
        try {
            return ResponseEntity.ok(productUseCase.findByProductType(productType));
        } catch (InvalidProcessException ex) {
            return ResponseEntity.badRequest().body(problemOf(ex));
        }
    }

    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(productUseCase.findAll());
    }
}