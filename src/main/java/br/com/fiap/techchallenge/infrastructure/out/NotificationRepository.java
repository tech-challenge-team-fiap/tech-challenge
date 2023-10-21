package br.com.fiap.techchallenge.infrastructure.out;

import br.com.fiap.techchallenge.infrastructure.repository.NotificationRepositoryDB;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends CrudRepository<NotificationRepositoryDB, Integer> {

}