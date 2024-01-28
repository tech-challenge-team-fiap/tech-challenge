package br.com.fiap.techchallenge.external.infrastructure.repositories;

import br.com.fiap.techchallenge.domain.enums.StatusOrder;
import br.com.fiap.techchallenge.external.infrastructure.entities.OrderDB;

import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<OrderDB, UUID> {

    Optional<OrderDB> findByNumberOrder(String numberOrder);

    List<OrderDB> findAllByStatusOrder(Sort dateRegister, StatusOrder status);
}