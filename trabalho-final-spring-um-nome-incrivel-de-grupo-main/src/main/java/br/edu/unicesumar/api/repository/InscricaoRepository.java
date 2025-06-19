package br.edu.unicesumar.api.repository;

import br.edu.unicesumar.api.entity.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
    List<Inscricao> findByAlunoId(Long alunoId);
}
