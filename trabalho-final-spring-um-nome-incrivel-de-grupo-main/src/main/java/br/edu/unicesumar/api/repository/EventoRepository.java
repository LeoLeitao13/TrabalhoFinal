package br.edu.unicesumar.api.repository;

import br.edu.unicesumar.api.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}
