
package br.edu.unicesumar.api.dto;

import java.time.LocalDateTime;
import java.util.List;

public class EventoDTO {
    private String nome;
    private String descricao;
    private LocalDateTime data;
    private Integer limiteParticipantes;
    private Long departamentoId;
    private List<Long> palestrantes;

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }

    public Integer getLimiteParticipantes() { return limiteParticipantes; }
    public void setLimiteParticipantes(Integer limiteParticipantes) { this.limiteParticipantes = limiteParticipantes; }

    public Long getDepartamentoId() { return departamentoId; }
    public void setDepartamentoId(Long departamentoId) { this.departamentoId = departamentoId; }

    public List<Long> getPalestrantes() { return palestrantes; }
    public void setPalestrantes(List<Long> palestrantes) { this.palestrantes = palestrantes; }
}
