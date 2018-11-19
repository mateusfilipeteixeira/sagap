package br.com.sagap.model;

import java.io.Serializable;
import javax.persistence.Id;

public class EstadoModel implements Serializable{
    
    @Id
    private Integer seq_estado;
    private String nome;
    private String sigla;

    public EstadoModel() {
    }

    public Integer getSeq_estado() {
        return seq_estado;
    }

    public void setSeq_estado(Integer seq_estado) {
        this.seq_estado = seq_estado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    
}
