package br.com.fiap.techchallenge.infrastructure.in;

import br.com.fiap.techchallenge.adapter.driven.entities.form.ProductEditFormDto;
import br.com.fiap.techchallenge.adapter.driven.entities.form.ProductFormDto;
import br.com.fiap.techchallenge.adapter.driven.entities.useCase.product.EditProductUseCase;
import br.com.fiap.techchallenge.adapter.driven.entities.useCase.product.RegisterNewProductUseCase;
import br.com.fiap.techchallenge.adapter.driven.entities.useCase.product.RemoveProductUseCase;
import br.com.fiap.techchallenge.common.enums.TypeProduct;
import br.com.fiap.techchallenge.infrastructure.gateway.ClientGateway;
import br.com.fiap.techchallenge.infrastructure.gateway.ProductGateway;
import br.com.fiap.techchallenge.infrastructure.repository.ProductRepositoryDb;
import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;
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

    @PostMapping(path = "register")
    @Transactional
    public ResponseEntity<ProductRepositoryDb> create(@RequestBody ProductFormDto productFormDto) {
        return registerNewProductUseCase.register(productFormDto);
    }

    @PutMapping(path = "update")
    public ResponseEntity<Integer> edit(@RequestBody ProductEditFormDto product) {
        return editProductUseCase.edit(product);
    }

    @DeleteMapping
    public ResponseEntity<Integer> remove(@PathParam("id") Integer id) {
        return removeProductUseCase.remove(id);
    }


    @GetMapping
    @Transactional
    public ResponseEntity findAll(@PathParam("type") String type) {
        return gateway.findAll(TypeProduct.valueOf(type));
    }

}
