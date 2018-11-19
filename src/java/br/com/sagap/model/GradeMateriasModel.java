package br.com.sagap.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Id;

public class GradeMateriasModel implements Serializable{
    @Id
    private Integer seq_grade_materias;
    private String nome_materia;
    private TurmaModel turma;
    private Date dt_cadastro;
    private PessoaModel professor;

    public GradeMateriasModel() {
    }

    public GradeMateriasModel(Integer seq_grade_materias, String nome_materia, TurmaModel turma, Date dt_cadastro, PessoaModel professor) {
        this.seq_grade_materias = seq_grade_materias;
        this.nome_materia = nome_materia;
        this.turma = turma;
        this.dt_cadastro = dt_cadastro;
        this.professor = professor;
    }

    public Integer getSeq_grade_materias() {
        return seq_grade_materias;
    }

    public void setSeq_grade_materias(Integer seq_grade_materias) {
        this.seq_grade_materias = seq_grade_materias;
    }

    public String getNome_materia() {
        return nome_materia;
    }

    public void setNome_materia(String nome_materia) {
        this.nome_materia = nome_materia;
    }

    public TurmaModel getTurma() {
        return turma;
    }

    public void setTurma(TurmaModel turma) {
        this.turma = turma;
    }

    public Date getDt_cadastro() {
        return dt_cadastro;
    }

    public void setDt_cadastro(Date dt_cadastro) {
        this.dt_cadastro = dt_cadastro;
    }

    public PessoaModel getProfessor() {
        return professor;
    }

    public void setProfessor(PessoaModel professor) {
        this.professor = professor;
    }
    
}
