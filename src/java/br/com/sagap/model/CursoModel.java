package br.com.sagap.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Id;

public class CursoModel  implements Serializable{
    @Id
    private Integer seq_curso;
    private InstituicaoModel instituicao;
    private String nome;
    private String descricao;
    private Date dt_cadastro;
    private Integer ativo;

    public CursoModel() {
    }

    public CursoModel(Integer seq_curso, InstituicaoModel instituicao, String nome, String descricao, Date dt_cadastro, Integer ativo) {
        this.seq_curso = seq_curso;
        this.instituicao = instituicao;
        this.nome = nome;
        this.descricao = descricao;
        this.dt_cadastro = dt_cadastro;
        this.ativo = ativo;
    }

    public Integer getSeq_curso() {
        return seq_curso;
    }

    public void setSeq_curso(Integer seq_curso) {
        this.seq_curso = seq_curso;
    }

    public InstituicaoModel getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(InstituicaoModel instituicao) {
        this.instituicao = instituicao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
    
    
}
