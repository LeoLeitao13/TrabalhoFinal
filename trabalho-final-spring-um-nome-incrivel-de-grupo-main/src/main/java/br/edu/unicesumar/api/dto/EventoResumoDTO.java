package br.edu.unicesumar.api.dto;

public class EventoResumoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private String data;
    private int limiteParticipantes;
    private int numeroInscritos;
    private String statusVagas; // "Lotado" ou "Disponível"

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public int getLimiteParticipantes() { return limiteParticipantes; }
    public void setLimiteParticipantes(int limiteParticipantes) { this.limiteParticipantes = limiteParticipantes; }

    public int getNumeroInscritos() { return numeroInscritos; }
    public void setNumeroInscritos(int numeroInscritos) { this.numeroInscritos = numeroInscritos; }

    public String getStatusVagas() { return statusVagas; }
    public void setStatusVagas(String statusVagas) { this.statusVagas = statusVagas; }
}