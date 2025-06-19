package br.edu.unicesumar.api.DTO;

import java.time.LocalDateTime;
import java.util.List;

public class EventoDTO {
    private String nome;
    private String descricao;
    private LocalDateTime data;
    private int limiteParticipantes;
    private Long departamentoId;
    private List<Long> palestranteIds;

    // Getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public int getLimiteParticipantes() {
        return limiteParticipantes;
    }

    public void setLimiteParticipantes(int limiteParticipantes) {
        this.limiteParticipantes = limiteParticipantes;
    }

    public Long getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(Long departamentoId) {
        this.departamentoId = departamentoId;
    }

    public List<Long> getPalestranteIds() {
        return palestranteIds;
    }

    public void setPalestranteIds(List<Long> palestranteIds) {
        this.palestranteIds = palestranteIds;
    }
}