package br.com.sagap.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Id;

public class NotaModel implements Serializable{
    @Id
    private Integer seq_nota;
    private GradeNotaModel grade_nota;
    private Float valor_sem_peso;
    private Float valor_com_peso;
    private Date dt_cadastro;
    private PessoaModel aluno;
    private GradeMateriasModel materia;

    public NotaModel() {
    }

    public NotaModel(Integer seq_nota, GradeNotaModel grade_nota, Float valor_sem_peso, Float valor_com_peso, Date dt_cadastro, PessoaModel aluno, GradeMateriasModel materia) {
        this.seq_nota = seq_nota;
        this.grade_nota = grade_nota;
        this.valor_sem_peso = valor_sem_peso;
        this.valor_com_peso = valor_com_peso;
        this.dt_cadastro = dt_cadastro;
        this.aluno = aluno;
        this.materia = materia;
    }

    public Integer getSeq_nota() {
        return seq_nota;
    }

    public void setSeq_nota(Integer seq_nota) {
        this.seq_nota = seq_nota;
    }

    public GradeNotaModel getGrade_nota() {
        return grade_nota;
    }

    public void setGrade_nota(GradeNotaModel grade_nota) {
        this.grade_nota = grade_nota;
    }

    public Float getValor_sem_peso() {
        return valor_sem_peso;
    }

    public void setValor_sem_peso(Float valor_sem_peso) {
        this.valor_sem_peso = valor_sem_peso;
        valor_com_peso=valor_sem_peso * grade_nota.getPeso();
    }

    public Float getValor_com_peso() {
        return valor_com_peso;
    }

    public void setValor_com_peso(Float valor_com_peso) {
        this.valor_com_peso = valor_com_peso;
    }

    public Date getDt_cadastro() {
        return dt_cadastro;
    }

    public void setDt_cadastro(Date dt_cadastro) {
        this.dt_cadastro = dt_cadastro;
    }

    public PessoaModel getAluno() {
        return aluno;
    }

    public void setAluno(PessoaModel aluno) {
        this.aluno = aluno;
    }

    public GradeMateriasModel getMateria() {
        return materia;
    }

    public void setMateria(GradeMateriasModel materia) {
        this.materia = materia;
    }
    
}