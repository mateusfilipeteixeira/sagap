package br.com.sagap.model;

import java.io.Serializable;
import javax.persistence.Id;

public class GradeNotaModel implements Serializable{
    @Id
    private Integer seq_grade_nota;
    private String descricao;
    private Integer valor;
    private Integer peso;

    public GradeNotaModel() {
    }

    public GradeNotaModel(Integer seq_grade_nota, String descricao, Integer valor, Integer peso) {
        this.seq_grade_nota = seq_grade_nota;
        this.descricao = descricao;
        this.valor = valor;
        this.peso = peso;
    }

    public Integer getSeq_grade_nota() {
        return seq_grade_nota;
    }

    public void setSeq_grade_nota(Integer seq_grade_nota) {
        this.seq_grade_nota = seq_grade_nota;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }
    
}
