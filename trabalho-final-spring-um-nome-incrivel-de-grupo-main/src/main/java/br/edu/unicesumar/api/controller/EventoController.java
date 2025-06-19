package br.edu.unicesumar.api.controller;

import br.edu.unicesumar.api.dto.EventoResumoDTO;
import br.edu.unicesumar.api.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.edu.unicesumar.api.entity.Evento;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
public class EventoController {

    @Autowired
    private EventoRepository repository;

    @GetMapping
    public List<EventoResumoDTO> listar() {
        return repository.findAll().stream().map(evento -> {
            EventoResumoDTO dto = new EventoResumoDTO();
            dto.setId(evento.getId());
            dto.setNome(evento.getNome());
            dto.setDescricao(evento.getDescricao());
            dto.setData(evento.getData().toString());
            dto.setLimiteParticipantes(evento.getLimiteParticipantes());
            dto.setNumeroInscritos(evento.getInscricoes().size());
            dto.setStatusVagas(evento.getInscricoes().size() >= evento.getLimiteParticipantes() ? "Lotado" : "Disponível");
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Evento criar(@RequestBody Evento evento) {
        return repository.save(evento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoResumoDTO> atualizar(@PathVariable Long id, @RequestBody Evento evento) {
        if (evento.getPalestrantes() == null || evento.getPalestrantes().isEmpty()) {
            return ResponseEntity.badRequest().build(); // ou .body("Evento deve ter pelo menos um palestrante")
        }

        return repository.findById(id).map(e -> {
            e.setNome(evento.getNome());
            e.setDescricao(evento.getDescricao());
            e.setData(evento.getData());
            e.setLimiteParticipantes(evento.getLimiteParticipantes());
            e.setDepartamento(evento.getDepartamento());
            e.setPalestrantes(evento.getPalestrantes());

            Evento atualizado = repository.save(e);
            EventoResumoDTO dto = new EventoResumoDTO();
            dto.setId(atualizado.getId());
            dto.setNome(atualizado.getNome());
            dto.setDescricao(atualizado.getDescricao());
            dto.setData(atualizado.getData().toString());
            dto.setLimiteParticipantes(atualizado.getLimiteParticipantes());
            dto.setNumeroInscritos(atualizado.getInscricoes().size());
            return ResponseEntity.ok(dto);
        }).orElse(ResponseEntity.notFound().build());
    }



    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        repository.deleteById(id);
    }


}