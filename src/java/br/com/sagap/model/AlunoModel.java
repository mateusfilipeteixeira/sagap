package br.com.sagap.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.Id;

public class AlunoModel implements Serializable{
    @Id
    private Integer seq_grade_alunos;
    private TurmaModel turma;
    private Date dt_cadastro;
    private PessoaModel aluno;
    private GradeMateriasModel materia;
    private Integer Integral;
    private Integer faltas;
    private ArrayList<NotaModel> notas;

    public AlunoModel() {
    }

    public AlunoModel(Integer seq_grade_alunos, TurmaModel turma, Date dt_cadastro, PessoaModel aluno, GradeMateriasModel materia, Integer Integral) {
        this.seq_grade_alunos = seq_grade_alunos;
        this.turma = turma;
        this.dt_cadastro = dt_cadastro;
        this.aluno = aluno;
        this.materia = materia;
        this.Integral = Integral;
    }

    public Integer getSeq_grade_alunos() {
        return seq_grade_alunos;
    }

    public void setSeq_grade_alunos(Integer seq_grade_alunos) {
        this.seq_grade_alunos = seq_grade_alunos;
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

    public Integer getIntegral() {
        return Integral;
    }

    public void setIntegral(Integer Integral) {
        this.Integral = Integral;
    }

    public Integer getFaltas() {
        return faltas;
    }

    public void setFaltas(Integer faltas) {
        this.faltas = faltas;
    }

    public ArrayList<NotaModel> getNotas() {
        return notas;
    }

    public void setNotas(ArrayList<NotaModel> notas) {
        this.notas = notas;
    }
           
}
