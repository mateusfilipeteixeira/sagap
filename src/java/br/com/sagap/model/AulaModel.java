package br.com.sagap.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Id;

public class AulaModel implements Serializable{
    @Id
    private Integer seq_aula;
    private PessoaModel professor;
    private GradeMateriasModel materia;
    private String tema;
    private String observacao;
    private Date data;

    public AulaModel() {
    }

    public AulaModel(Integer seq_aula, PessoaModel professor, GradeMateriasModel materia, String tema, String observacao, Date data) {
        this.seq_aula = seq_aula;
        this.professor = professor;
        this.materia = materia;
        this.tema = tema;
        this.observacao = observacao;
        this.data=data;
    }

    public Integer getSeq_aula() {
        return seq_aula;
    }

    public void setSeq_aula(Integer seq_aula) {
        this.seq_aula = seq_aula;
    }

    public PessoaModel getProfessor() {
        return professor;
    }

    public void setProfessor(PessoaModel professor) {
        this.professor = professor;
    }

    public GradeMateriasModel getMateria() {
        return materia;
    }

    public void setMateria(GradeMateriasModel materia) {
        this.materia = materia;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
}