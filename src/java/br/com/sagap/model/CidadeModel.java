package br.com.sagap.model;

import java.io.Serializable;
import javax.persistence.Id;

public class CidadeModel implements Serializable{
    @Id
    private Integer seq_cidade;
    private String nome;
    private EstadoModel estado;

    public CidadeModel() {
    }

    public EstadoModel getEstado() {
        return estado;
    }

    public void setEstado(EstadoModel estado) {
        this.estado = estado;
    }

    public Integer getSeq_cidade() {
        return seq_cidade;
    }

    public void setSeq_cidade(Integer seq_cidade) {
        this.seq_cidade = seq_cidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
}
