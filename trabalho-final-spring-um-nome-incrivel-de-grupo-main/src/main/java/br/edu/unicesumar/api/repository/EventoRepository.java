package br.edu.unicesumar.api.repository;
import br.edu.unicesumar.api.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByDepartamentoId(Long departamentoId);
}
