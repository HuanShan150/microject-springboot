package com.microsoft.microject.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Projeto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    
    @OneToMany(mappedBy = "projeto")
    private List<Atividade> atividades;

    @Enumerated(EnumType.STRING)
    private ProjetoStatus projetoStatus;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Atividade> getAtividades() {
        return this.atividades;
    }

    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }


    public ProjetoStatus getStatus() {
        return this.projetoStatus;
    }

    public void setStatus(ProjetoStatus projetoStatus) {
        this.projetoStatus = projetoStatus;
    }

}
