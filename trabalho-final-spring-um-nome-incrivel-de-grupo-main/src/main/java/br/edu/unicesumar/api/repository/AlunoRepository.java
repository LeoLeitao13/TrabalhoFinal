package br.edu.unicesumar.api.repository;

import br.edu.unicesumar.api.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
