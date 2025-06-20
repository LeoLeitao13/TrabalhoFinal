package br.edu.unicesumar.api.controller;

import br.edu.unicesumar.api.entity.StatusInscricao;
import br.edu.unicesumar.api.dto.InscricaoDTO;
import br.edu.unicesumar.api.dto.InscricaoResumoDTO;
import br.edu.unicesumar.api.entity.*;
import br.edu.unicesumar.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class InscricaoController {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @PostMapping("/events/{id}/registrations")
    public ResponseEntity<String> inscrever(@PathVariable Long id, @RequestBody InscricaoDTO dto) {
        Long idAluno = dto.getIdAluno();


        Evento evento = eventoRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado"));

        Aluno aluno = alunoRepository.findById(idAluno).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));

        if (evento.getData().isBefore(LocalDate.now().atStartOfDay())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Evento já ocorreu");
        }

        if (evento.getInscricoes().size() >= evento.getLimiteParticipantes()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Evento lotado");
        }

        boolean jaInscrito = evento.getInscricoes().stream()
                .anyMatch(i -> i.getAluno().getId().equals(aluno.getId()));
        if (jaInscrito) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aluno já está inscrito neste evento");
        }

        List<Inscricao> inscricoesDoAluno = inscricaoRepository.findByAlunoId(aluno.getId());
        for (Inscricao inscricao : inscricoesDoAluno) {
            if (inscricao.getEvento().getData().equals(evento.getData())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Conflito de datas com outro evento");
            }
        }

        Inscricao inscricao = new Inscricao();
        inscricao.setEvento(evento);
        inscricao.setAluno(aluno);
        inscricao.setStatus(StatusInscricao.ATIVO);
        inscricao.setDataHora(LocalDateTime.now());
        inscricaoRepository.save(inscricao);

        return ResponseEntity.ok("Inscrição realizada com sucesso :)");
    }


    @GetMapping("/students/{id}/registrations")
    public List<InscricaoResumoDTO> listarInscricoes(@PathVariable Long id) {
        List<Inscricao> inscricoes = inscricaoRepository.findByAlunoId(id);
        return inscricoes.stream().map(inscricao -> {
            InscricaoResumoDTO dto = new InscricaoResumoDTO();
            dto.setId(inscricao.getId());
            dto.setIdEvento(inscricao.getEvento().getId());
            dto.setNomeEvento(inscricao.getEvento().getNome());
            dto.setIdAluno(inscricao.getAluno().getId());
            dto.setNomeAluno(inscricao.getAluno().getNome());
            dto.setDataInscricao(inscricao.getDataHora().toString());
            dto.setAtivo(inscricao.getStatus() == StatusInscricao.ATIVO);
            return dto;
        }).collect(Collectors.toList());
    }

    @DeleteMapping("/registrations/{id}")
    public ResponseEntity<String> cancelar(@PathVariable Long id) {
        Inscricao inscricao = inscricaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscrição não encontrada :("));

        if (inscricao.getStatus() == StatusInscricao.CANCELADO) {
            return ResponseEntity.badRequest().body("Inscrição já está cancelada.");
        }

        inscricao.setStatus(StatusInscricao.CANCELADO);
        inscricaoRepository.save(inscricao);

        return ResponseEntity.ok("Inscrição cancelada com sucesso :)");
    }

}