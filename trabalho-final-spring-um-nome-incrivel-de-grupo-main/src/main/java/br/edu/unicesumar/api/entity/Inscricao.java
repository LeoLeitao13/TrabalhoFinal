package br.edu.unicesumar.api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHoraInscricao;

    @ManyToOne
    private Evento evento;

    @ManyToOne
    private Aluno aluno;

    @Enumerated(EnumType.STRING)
    private StatusInscricao status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHoraInscricao() {
        return dataHoraInscricao;
    }

    public void setDataHoraInscricao(LocalDateTime dataHoraInscricao) {
        this.dataHoraInscricao = dataHoraInscricao;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public StatusInscricao getStatus() {
        return status;
    }

    public void setStatus(StatusInscricao status) {
        this.status = status;
    }
}
