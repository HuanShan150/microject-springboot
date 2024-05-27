package com.microsoft.microject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.microsoft.microject.domain.Projeto;
import java.util.List;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    List<Projeto> findByClienteId(Long clienteId);
}
