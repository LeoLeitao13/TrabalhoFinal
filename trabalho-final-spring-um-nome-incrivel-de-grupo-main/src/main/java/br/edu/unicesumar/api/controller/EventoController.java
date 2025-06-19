package br.edu.unicesumar.api.controller;

import br.edu.unicesumar.api.DTO.EventoDTO;
import br.edu.unicesumar.api.DTO.EventoResumoDTO;
import br.edu.unicesumar.api.entity.*;
import br.edu.unicesumar.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private PalestranteRepository palestranteRepository;

    @GetMapping
    public List<Object> listarEventos() {
        return eventoRepository.findAll().stream().map(evento -> {
            int inscritos = evento.getInscricoes() != null ? evento.getInscricoes().size() : 0;
            boolean lotado = inscritos >= evento.getLimiteParticipantes();
            return new Object() {
                public final Long id = evento.getId();
                public final String nome = evento.getNome();
                public final int numeroInscritos = inscritos;
                public final String status = lotado ? "Lotado" : "Com vagas";
            };
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> obterEvento(@PathVariable Long id) {
        return eventoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> criarEvento(@RequestBody EventoDTO dto) {
        if (dto.getPalestranteIds() == null || dto.getPalestranteIds().isEmpty()) {
            return ResponseEntity.badRequest().body("Evento deve ter ao menos um palestrante.");
        }

        Optional<Departamento> deptoOpt = departamentoRepository.findById(dto.getDepartamentoId());
        if (deptoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Departamento não encontrado.");
        }

        List<Palestrante> palestrantes = palestranteRepository.findAllById(dto.getPalestranteIds());
        if (palestrantes.isEmpty()) {
            return ResponseEntity.badRequest().body("Palestrantes não encontrados.");
        }

        Evento evento = new Evento();
        evento.setNome(dto.getNome());
        evento.setDescricao(dto.getDescricao());
        evento.setData(dto.getData());
        evento.setLimiteParticipantes(dto.getLimiteParticipantes());
        evento.setDepartamento(deptoOpt.get());
        evento.setPalestrantes(palestrantes);

        Evento salvo = eventoRepository.save(evento);

        EventoResumoDTO resumo = new EventoResumoDTO();
        resumo.setId(salvo.getId());
        resumo.setNome(salvo.getNome());
        resumo.setNumeroInscritos(salvo.getInscricoes() != null ? salvo.getInscricoes().size() : 0);
        resumo.setStatus("Com vagas"); // ou calcule dinamicamente

        return ResponseEntity.ok(resumo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarEvento(@PathVariable Long id, @RequestBody EventoDTO dto) {
        Optional<Evento> existente = eventoRepository.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Departamento> deptoOpt = departamentoRepository.findById(dto.getDepartamentoId());
        if (deptoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Departamento não encontrado.");
        }

        List<Palestrante> palestrantes = palestranteRepository.findAllById(dto.getPalestranteIds());
        if (palestrantes.isEmpty()) {
            return ResponseEntity.badRequest().body("Palestrantes não encontrados.");
        }

        Evento evento = existente.get();
        evento.setNome(dto.getNome());
        evento.setDescricao(dto.getDescricao());
        evento.setData(dto.getData());
        evento.setLimiteParticipantes(dto.getLimiteParticipantes());
        evento.setDepartamento(deptoOpt.get());
        evento.setPalestrantes(palestrantes);

        return ResponseEntity.ok(eventoRepository.save(evento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarEvento(@PathVariable Long id) {
        Optional<Evento> evento = eventoRepository.findById(id);
        if (evento.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        eventoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}