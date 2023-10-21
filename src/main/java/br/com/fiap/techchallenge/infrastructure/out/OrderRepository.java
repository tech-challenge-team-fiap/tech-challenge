package br.com.fiap.techchallenge.infrastructure.out;

import br.com.fiap.techchallenge.common.enums.TypeProduct;
import br.com.fiap.techchallenge.common.enums.TypeStatus;
import br.com.fiap.techchallenge.infrastructure.repository.OrderRepositoryDb;
import br.com.fiap.techchallenge.infrastructure.repository.ProductRepositoryDb;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<OrderRepositoryDb, Integer> {

    Optional<OrderRepositoryDb> findByNumberOrder(String numberOrder);
}