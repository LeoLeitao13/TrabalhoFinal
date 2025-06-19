package br.edu.unicesumar.api.controller;

import br.edu.unicesumar.api.dto.DepartamentoDTO;
import br.edu.unicesumar.api.entity.Departamento;
import br.edu.unicesumar.api.repository.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departments")
public class DepartamentoController {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @GetMapping
    public List<Departamento> listarTodos() {
        return departamentoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Departamento> buscarPorId(@PathVariable Long id) {
        return departamentoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Departamento> criar(@RequestBody DepartamentoDTO dto) {
        Departamento d = new Departamento();
        d.setNome(dto.getNome());
        d.setSigla(dto.getSigla());
        d.setResponsavel(dto.getResponsavel());
        return ResponseEntity.ok(departamentoRepository.save(d));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Departamento> atualizar(@PathVariable Long id, @RequestBody DepartamentoDTO dto) {
        return departamentoRepository.findById(id).map(d -> {
            d.setNome(dto.getNome());
            d.setSigla(dto.getSigla());
            d.setResponsavel(dto.getResponsavel());
            return ResponseEntity.ok(departamentoRepository.save(d));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (departamentoRepository.existsById(id)) {
            departamentoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/report")
    public ResponseEntity<?> relatorio(@PathVariable Long id) {
        return departamentoRepository.findById(id).map(depto -> {
            int totalEventos = depto.getEventos() != null ? depto.getEventos().size() : 0;
            int totalInscricoes = depto.getEventos().stream()
                .mapToInt(e -> e.getInscricoes() != null ? e.getInscricoes().size() : 0)
                .sum();
            Map<String, Object> relatorio = new HashMap<>();
            relatorio.put("totalEventos", totalEventos);
            relatorio.put("totalInscritos", totalInscricoes);
            return ResponseEntity.ok(relatorio);
        }).orElse(ResponseEntity.notFound().build());
    }
}