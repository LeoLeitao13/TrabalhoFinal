package br.edu.unicesumar.api.controller;

import br.edu.unicesumar.api.dto.AlunoDTO;
import br.edu.unicesumar.api.entity.Aluno;
import br.edu.unicesumar.api.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @GetMapping
    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {
        return alunoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Aluno> criar(@RequestBody AlunoDTO dto) {
        Aluno aluno = new Aluno();
        aluno.setNome(dto.getNome());
        aluno.setMatricula(dto.getMatricula());
        aluno.setCurso(dto.getCurso());
        return ResponseEntity.ok(alunoRepository.save(aluno));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizar(@PathVariable Long id, @RequestBody AlunoDTO dto) {
        return alunoRepository.findById(id).map(aluno -> {
            aluno.setNome(dto.getNome());
            aluno.setMatricula(dto.getMatricula());
            aluno.setCurso(dto.getCurso());
            return ResponseEntity.ok(alunoRepository.save(aluno));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (alunoRepository.existsById(id)) {
            alunoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}