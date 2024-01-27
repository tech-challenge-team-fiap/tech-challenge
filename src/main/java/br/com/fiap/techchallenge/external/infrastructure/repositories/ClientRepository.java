package br.com.fiap.techchallenge.external.infrastructure.repositories;

import br.com.fiap.techchallenge.external.infrastructure.entities.ClientDB;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientDB, UUID> {
    Optional<ClientDB> findByCpf(String cpf);
}
