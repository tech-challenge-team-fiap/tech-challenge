package br.com.fiap.techchallenge.infrastructure.out;

import br.com.fiap.techchallenge.infrastructure.repository.OrderQueueRepositoryDB;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderQueueRepository extends CrudRepository<OrderQueueRepositoryDB, Integer> {

}