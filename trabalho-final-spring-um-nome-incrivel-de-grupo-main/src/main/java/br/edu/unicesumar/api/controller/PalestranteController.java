package br.edu.unicesumar.api.controller;

import br.edu.unicesumar.api.dto.PalestranteDTO;
import br.edu.unicesumar.api.entity.Palestrante;
import br.edu.unicesumar.api.repository.PalestranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/speakers")
public class PalestranteController {

    @Autowired
    private PalestranteRepository palestranteRepository;

    @GetMapping
    public List<PalestranteDTO> listarTodos() {
        return palestranteRepository.findAll().stream().map(p -> {
            PalestranteDTO dto = new PalestranteDTO();
            dto.setNome(p.getNome());
            dto.setMiniCurriculo(p.getMiniCurriculo());
            dto.setInstituicao(p.getInstituicao());
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PalestranteDTO> buscarPorId(@PathVariable Long id) {
        return palestranteRepository.findById(id).map(p -> {
            PalestranteDTO dto = new PalestranteDTO();
            dto.setNome(p.getNome());
            dto.setMiniCurriculo(p.getMiniCurriculo());
            dto.setInstituicao(p.getInstituicao());
            return ResponseEntity.ok(dto);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Palestrante> criar(@RequestBody PalestranteDTO dto) {
        Palestrante p = new Palestrante();
        p.setNome(dto.getNome());
        p.setMiniCurriculo(dto.getMiniCurriculo());
        p.setInstituicao(dto.getInstituicao());
        return ResponseEntity.ok(palestranteRepository.save(p));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Palestrante> atualizar(@PathVariable Long id, @RequestBody PalestranteDTO dto) {
        return palestranteRepository.findById(id).map(p -> {
            p.setNome(dto.getNome());
            p.setMiniCurriculo(dto.getMiniCurriculo());
            p.setInstituicao(dto.getInstituicao());
            return ResponseEntity.ok(palestranteRepository.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Optional<Palestrante> palestranteOpt = palestranteRepository.findById(id);

        if (palestranteOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Palestrante palestrante = palestranteOpt.get();
        if (palestrante.getEventos() != null && !palestrante.getEventos().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        palestranteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
