package br.com.fiap.techchallenge.infrastructure.out;

import br.com.fiap.techchallenge.common.enums.StatusOrder;
import br.com.fiap.techchallenge.infrastructure.repository.OrderQueueRepositoryDB;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderQueueRepository extends CrudRepository<OrderQueueRepositoryDB, Integer> {

    List<OrderQueueRepositoryDB> findAllByStatusOrder(Sort dateRegister, StatusOrder statusOrder);
}