package br.edu.unicesumar.api.DTO;

public class EventoResumoDTO {
    private Long id;
    private String nome;
    private int numeroInscritos;
    private String status;

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

    public int getNumeroInscritos() {
        return numeroInscritos;
    }

    public void setNumeroInscritos(int numeroInscritos) {
        this.numeroInscritos = numeroInscritos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}