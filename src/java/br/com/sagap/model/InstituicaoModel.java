package br.com.sagap.model;

import java.io.Serializable;
import javax.persistence.Id;

public class InstituicaoModel implements Serializable{
    @Id
    private Integer seq_instituicao;
    private String nome;
    private String cnpj;
    private String telefone;
    private String logradouro;
    private String numero;
    private String bairro;
    private String complemento;
    private CidadeModel cidade;

    public InstituicaoModel() {
    }

    public Integer getSeq_instituicao() {
        return seq_instituicao;
    }

    public void setSeq_instituicao(Integer seq_instituicao) {
        this.seq_instituicao = seq_instituicao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public CidadeModel getCidade() {
        return cidade;
    }

    public void setCidade(CidadeModel cidade) {
        this.cidade = cidade;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
}
