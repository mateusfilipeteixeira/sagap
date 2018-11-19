package br.com.sagap.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Id;

public class PessoaModel implements Serializable{

    @Id
    private Integer seq_pessoa;
    private String nome;
    private Integer tipo;
    private String rg;
    private String cpf;
    private String dt_nascimento;
    private String login;
    private String senha;
    private String logradouro;
    private String numero;
    private String bairro;
    private String complemento;
    private CidadeModel cidade;
    private Integer ativo;
    private String telefone;
    private Date dt_cadastro;

    public PessoaModel() {
    }

    public PessoaModel(Integer seq_pessoa, String nome, Integer tipo, String rg, String cpf, String dt_nascimento, String login, String senha, String logradouro, String numero, String bairro, String complemento, CidadeModel cidade, Integer ativo,String telefone,Date dt_cadastro) {
        this.seq_pessoa = seq_pessoa;
        this.nome = nome;
        this.tipo = tipo;
        this.rg = rg;
        this.cpf = cpf;
        this.dt_nascimento = dt_nascimento;
        this.login = login;
        this.senha = senha;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.complemento = complemento;
        this.cidade = cidade;
        this.ativo = ativo;
        this.telefone=telefone;
        this.dt_cadastro=dt_cadastro;
    }

    public Integer getSeq_pessoa() {
        return seq_pessoa;
    }

    public void setSeq_pessoa(Integer seq_pessoa) {
        this.seq_pessoa = seq_pessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDt_nascimento() {
        return dt_nascimento;
    }

    public void setDt_nascimento(String dt_nascimento) {
        this.dt_nascimento = dt_nascimento;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    public Integer getAtivo() {
        return ativo;
    }

    public void setAtivo(Integer ativo) {
        this.ativo = ativo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getDt_cadastro() {
        return dt_cadastro;
    }

    public void setDt_cadastro(Date dt_cadastro) {
        this.dt_cadastro = dt_cadastro;
    }
    
}