package br.com.sagap.model;

import java.io.Serializable;
import javax.persistence.Id;

public class ChamadaModel implements Serializable{
    @Id
    private Integer seq_chamada;
    private AulaModel aula;
    private PessoaModel aluno;
    private Integer presente;

    public ChamadaModel() {
    }

    public ChamadaModel(Integer seq_chamada, AulaModel aula, PessoaModel aluno, Integer presente) {
        this.seq_chamada = seq_chamada;
        this.aula = aula;
        this.aluno = aluno;
        this.presente = presente;
    }

    public Integer getSeq_chamada() {
        return seq_chamada;
    }

    public void setSeq_chamada(Integer seq_chamada) {
        this.seq_chamada = seq_chamada;
    }

    public AulaModel getAula() {
        return aula;
    }

    public void setAula(AulaModel aula) {
        this.aula = aula;
    }

    public PessoaModel getAluno() {
        return aluno;
    }

    public void setAluno(PessoaModel aluno) {
        this.aluno = aluno;
    }

    public Integer getPresente() {
        return presente;
    }

    public void setPresente(Integer presente) {
        this.presente = presente;
    }

    public Boolean getPresenca() {
        return presente==1;
    }

    public void setPresenca(Boolean presenca) {
        if(presenca){
            presente=1;
        }else{
            presente=0;
        }
    }
    
}