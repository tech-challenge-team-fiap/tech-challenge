package br.com.fiap.techchallenge.infrastructure.out;

import br.com.fiap.techchallenge.common.enums.TypeProduct;
import br.com.fiap.techchallenge.common.enums.TypeStatus;
import br.com.fiap.techchallenge.infrastructure.repository.ProductRepositoryDb;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductRepositoryDb, UUID> {

    List<ProductRepositoryDb> findByTypeProductAndTypeStatus(TypeProduct typeProduct, TypeStatus active);

    ProductRepositoryDb findAllById(UUID id);
}