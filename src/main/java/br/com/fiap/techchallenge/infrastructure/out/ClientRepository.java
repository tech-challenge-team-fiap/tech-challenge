package br.com.fiap.techchallenge.infrastructure.out;

import br.com.fiap.techchallenge.infrastructure.repository.ClientRepositoryDb;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientRepositoryDb, UUID> {
    Optional<ClientRepositoryDb> findByCpf(String cpf);
}
