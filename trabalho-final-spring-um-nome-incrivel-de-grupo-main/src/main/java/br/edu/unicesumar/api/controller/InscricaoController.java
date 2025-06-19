package br.edu.unicesumar.api.controller;

import br.edu.unicesumar.api.dto.InscricaoDTO;
import br.edu.unicesumar.api.dto.InscricaoResumoDTO;
import br.edu.unicesumar.api.entity.*;
import br.edu.unicesumar.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class InscricaoController {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @PostMapping("/events/{idEvento}/registrations")
    public ResponseEntity<?> inscreverAluno(@PathVariable Long idEvento, @RequestBody InscricaoDTO dto) {
        Optional<Evento> eventoOpt = eventoRepository.findById(idEvento);
        Optional<Aluno> alunoOpt = alunoRepository.findById(dto.getId());

        if (eventoOpt.isEmpty() || alunoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Evento evento = eventoOpt.get();
        Aluno aluno = alunoOpt.get();

        if (evento.getData().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Evento já ocorreu.");
        }

        long countAtivos = evento.getInscricoes().stream().filter(Inscricao::isAtivo).count();
        if (countAtivos >= evento.getLimiteParticipantes()) {
            return ResponseEntity.badRequest().body("Evento lotado.");
        }

        boolean jaInscrito = evento.getInscricoes().stream()
            .anyMatch(i -> i.getAluno().getId().equals(aluno.getId()) && i.isAtivo());
        if (jaInscrito) {
            return ResponseEntity.badRequest().body("Aluno já inscrito no evento.");
        }

        List<Inscricao> inscricoesAluno = aluno.getInscricoes();
        if (inscricoesAluno != null) {
            for (Inscricao i : inscricoesAluno) {
                if (i.isAtivo() && i.getEvento().getData().isEqual(evento.getData())) {
                    return ResponseEntity.badRequest().body("Conflito de horário com outro evento.");
                }
            }
        }

        Inscricao inscricao = new Inscricao();
        inscricao.setAluno(aluno);
        inscricao.setEvento(evento);
        inscricao.setDataInscricao(LocalDateTime.now());
        inscricao.setAtivo(true);
        Inscricao salva = inscricaoRepository.save(inscricao);

        InscricaoResumoDTO resposta = new InscricaoResumoDTO();
        resposta.setId(salva.getId());
        resposta.setIdAluno(aluno.getId());
        resposta.setNomeAluno(aluno.getNome());
        resposta.setIdEvento(evento.getId());
        resposta.setNomeEvento(evento.getNome());
        resposta.setDataInscricao(salva.getDataInscricao().toString());
        resposta.setAtivo(true);

        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/students/{idAluno}/registrations")
    public ResponseEntity<?> listarInscricoes(@PathVariable Long idAluno) {
        Optional<Aluno> alunoOpt = alunoRepository.findById(idAluno);
        if (alunoOpt.isEmpty()) return ResponseEntity.notFound().build();

        List<InscricaoResumoDTO> resposta = alunoOpt.get().getInscricoes().stream().map(i -> {
            InscricaoResumoDTO dto = new InscricaoResumoDTO();
            dto.setId(i.getId());
            dto.setIdAluno(i.getAluno().getId());
            dto.setNomeAluno(i.getAluno().getNome());
            dto.setIdEvento(i.getEvento().getId());
            dto.setNomeEvento(i.getEvento().getNome());
            dto.setDataInscricao(i.getDataInscricao().toString());
            dto.setAtivo(i.isAtivo());
            return dto;
        }).toList();

        return ResponseEntity.ok(resposta);

    }

    @DeleteMapping("/registrations/{id}")
    public ResponseEntity<?> cancelarInscricao(@PathVariable Long id) {
        Optional<Inscricao> opt = inscricaoRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        Inscricao inscricao = opt.get();
        inscricao.setAtivo(false);
        inscricaoRepository.save(inscricao);
        return ResponseEntity.ok("Inscrição cancelada.");
    }
}