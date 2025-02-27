package com.ryuge.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ryuge.desafio.model.Contato;
import java.util.Optional;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
    Optional<Contato> findByCelular(String celular);
}
