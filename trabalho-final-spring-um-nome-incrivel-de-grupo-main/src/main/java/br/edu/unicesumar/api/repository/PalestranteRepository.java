package br.edu.unicesumar.api.repository;

import br.edu.unicesumar.api.entity.Palestrante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PalestranteRepository extends JpaRepository<Palestrante, Long> {
}
