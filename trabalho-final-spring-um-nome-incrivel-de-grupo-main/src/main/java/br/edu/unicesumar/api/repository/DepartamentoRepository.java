package br.edu.unicesumar.api.repository;

import br.edu.unicesumar.api.entity.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
}
