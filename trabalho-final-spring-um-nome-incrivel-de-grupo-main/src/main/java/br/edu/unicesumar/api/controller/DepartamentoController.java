package br.edu.unicesumar.api.controller;

import br.edu.unicesumar.api.entity.Departamento;
import br.edu.unicesumar.api.entity.Evento;
import br.edu.unicesumar.api.repository.DepartamentoRepository;
import br.edu.unicesumar.api.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DepartamentoController {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @GetMapping("/departments")
    public List<Departamento> listar() {
        return departamentoRepository.findAll();
    }

    @PostMapping("/departments")
    public Departamento criar(@RequestBody Departamento departamento) {
        return departamentoRepository.save(departamento);
    }

    @GetMapping("/departments/{id}")
    public Departamento buscar(@PathVariable Long id) {
        return departamentoRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Departamento não encontrado"));
    }

    @PutMapping("/departments/{id}")
    public Departamento atualizar(@PathVariable Long id, @RequestBody Departamento novo) {
        Departamento departamento = buscar(id);
        departamento.setNome(novo.getNome());
        departamento.setSigla(novo.getSigla());
        departamento.setResponsavel(novo.getResponsavel());
        return departamentoRepository.save(departamento);
    }

    @DeleteMapping("/departments/{id}")
    public void excluir(@PathVariable Long id) {
        departamentoRepository.deleteById(id);
    }

    @GetMapping("/departments/{id}/report")
    public Map<String, Object> gerarRelatorio(@PathVariable Long id) {
        Departamento depto = departamentoRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Departamento não encontrado"));

        List<Evento> eventos = eventoRepository.findByDepartamentoId(id);

        int totalEventos = eventos.size();
        int totalInscritos = eventos.stream()
                .mapToInt(e -> e.getInscricoes().size())
                .sum();

        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("departamento", depto.getNome());
        relatorio.put("totalEventos", totalEventos);
        relatorio.put("totalInscritos", totalInscritos);

        return relatorio;
    }
}
