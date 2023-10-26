package br.com.fiap.techchallenge.infrastructure.out;

import br.com.fiap.techchallenge.infrastructure.repository.ClientRepositoryDb;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<ClientRepositoryDb, UUID> {
    Optional<ClientRepositoryDb> findByCpf(String cpf);
}
