package br.com.sagap.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Id;

public class TurmaModel implements Serializable{
    @Id
    private Integer seq_turma;
    private CursoModel curso;
    private Date dt_cadastro;
    private Integer ativo;
    private String periodo;

    public TurmaModel() {
    }

    public TurmaModel(Integer seq_turma, CursoModel curso, Date dt_cadastro, Integer ativo, String periodo) {
        this.seq_turma = seq_turma;
        this.curso = curso;
        this.dt_cadastro = dt_cadastro;
        this.ativo = ativo;
        this.periodo = periodo;
    }

    public Integer getSeq_turma() {
        return seq_turma;
    }

    public void setSeq_turma(Integer seq_turma) {
        this.seq_turma = seq_turma;
    }

    public CursoModel getCurso() {
        return curso;
    }

    public void setCurso(CursoModel curso) {
        this.curso = curso;
    }

    public Date getDt_cadastro() {
        return dt_cadastro;
    }

    public void setDt_cadastro(Date dt_cadastro) {
        this.dt_cadastro = dt_cadastro;
    }

    public Integer getAtivo() {
        return ativo;
    }

    public void setAtivo(Integer ativo) {
        this.ativo = ativo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    
    
}
