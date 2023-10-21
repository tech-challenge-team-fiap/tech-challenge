package br.com.fiap.techchallenge.infrastructure.out;

import br.com.fiap.techchallenge.common.enums.TypeProduct;
import br.com.fiap.techchallenge.common.enums.TypeStatus;
import br.com.fiap.techchallenge.infrastructure.repository.ProductRepositoryDb;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<ProductRepositoryDb, Integer> {

    List<ProductRepositoryDb>  findByTypeProductAndTypeStatus(TypeProduct typeProduct, TypeStatus active);
}