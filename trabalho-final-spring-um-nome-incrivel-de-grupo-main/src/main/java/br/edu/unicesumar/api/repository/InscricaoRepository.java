package br.edu.unicesumar.api.repository;

import br.edu.unicesumar.api.entity.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
}
