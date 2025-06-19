package br.edu.unicesumar.api.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Palestrante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String miniCurriculo;
    private String instituicao;

    @ManyToMany(mappedBy = "palestrantes")
    private List<Evento> eventos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMiniCurriculo() {
        return miniCurriculo;
    }

    public void setMiniCurriculo(String miniCurriculo) {
        this.miniCurriculo = miniCurriculo;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }
}