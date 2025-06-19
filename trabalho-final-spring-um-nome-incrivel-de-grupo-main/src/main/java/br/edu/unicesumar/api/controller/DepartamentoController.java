package br.edu.unicesumar.api.controller;

import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import br.edu.unicesumar.api.dto.DepartamentoDTO;
import br.edu.unicesumar.api.entity.Departamento;
import br.edu.unicesumar.api.repository.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartamentoController {

    @Autowired
    private DepartamentoRepository repository;
    private DepartamentoRepository departamentoRepository;

    @GetMapping
    public List<Departamento> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Departamento> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Departamento criar(@RequestBody DepartamentoDTO dto) {
        Departamento departamento = new Departamento();
        departamento.setNome(dto.getNome());
        departamento.setSigla(dto.getSigla());
        departamento.setResponsavel(dto.getResponsavel());
        return repository.save(departamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Departamento> atualizar(@PathVariable Long id, @RequestBody DepartamentoDTO dto) {
        return repository.findById(id).map(dep -> {
            dep.setNome(dto.getNome());
            dep.setSigla(dto.getSigla());
            dep.setResponsavel(dto.getResponsavel());
            return ResponseEntity.ok(repository.save(dep));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        repository.deleteById(id);
    }


    @GetMapping("/{id}/report")
    public ResponseEntity<Map<String, Object>> relatorioDepartamento(@PathVariable Long id) {
        Optional<Departamento> departamentoOpt = departamentoRepository.findById(id);
        if (departamentoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Departamento departamento = departamentoOpt.get();
        int totalEventos = departamento.getEventos().size();
        int totalInscritos = departamento.getEventos().stream()
                .mapToInt(evento -> evento.getInscricoes().size())
                .sum();

        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("departamento", departamento.getNome());
        relatorio.put("totalEventos", totalEventos);
        relatorio.put("totalInscritos", totalInscritos);

        return ResponseEntity.ok(relatorio);
    }

}