package br.edu.unicesumar.api.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String matricula;
    private String curso;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inscricao> inscricoes;

    public List<Inscricao> getInscricoes() {
        return inscricoes;
    }

    public void setInscricoes(List<Inscricao> inscricoes) {
        this.inscricoes = inscricoes;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}