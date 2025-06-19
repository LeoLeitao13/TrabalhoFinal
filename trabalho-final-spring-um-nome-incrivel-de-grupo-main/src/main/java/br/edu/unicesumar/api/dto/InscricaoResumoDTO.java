package br.edu.unicesumar.api.dto;

public class InscricaoResumoDTO {
    private Long id;
    private Long idEvento;
    private String nomeEvento;
    private Long idAluno;
    private String nomeAluno;
    private String dataInscricao;
    private boolean ativo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public Long getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Long idAluno) {
        this.idAluno = idAluno;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(String dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}