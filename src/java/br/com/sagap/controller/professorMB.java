package br.com.sagap.controller;

import br.com.sagap.dao.PessoaDao;
import br.com.sagap.model.CidadeModel;
import br.com.sagap.model.PessoaModel;
import br.com.sagap.util.UtilMensagens;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "professorMB")
@SessionScoped
public class professorMB implements Serializable {
    
    private PessoaModel professor;
    Integer ativo=1;
    String estado="";
    private CidadeModel cidade=new CidadeModel();
    Integer tipoBusca=3;
    String busca="";
    ArrayList<PessoaModel> professoresBusca=new ArrayList<>();
    String login="";
    String senha="";

    public professorMB() {
        PessoaDao pessoadao=new PessoaDao();
        professoresBusca=pessoadao.buscar("where tipo=2");
    }

    public PessoaModel getProfessor() {
        return professor;
    }

    public void setProfessor(PessoaModel professor) {
        this.professor = professor;
    }

    public Integer getAtivo() {
        return ativo;
    }

    public void setAtivo(Integer ativo) {
        this.ativo = ativo;
    }
    
    public void Estado(){
        estado=cidade.getEstado().getNome();
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public CidadeModel getCidade() {
        return cidade;
    }

    public void setCidade(CidadeModel cidade) {
        estado=cidade.getEstado().getNome();
        this.cidade = cidade;
    }
    
    public String salvarProfessor(){
        professor.setAtivo(ativo);
        if(professor.getTipo()==null){
            professor.setTipo(2);
        }
        professor.setCidade(cidade);
        PessoaDao pessoadao=new PessoaDao();
        professor.setLogin(login);
        professor.setSenha(senha);
        if(pessoadao.salvar(professor)){
            professor=new PessoaModel();
            UtilMensagens.mensagemSucesso("Professor salvo com sucesso!");
            professoresBusca=pessoadao.buscar("where tipo=2 or tipo=1");
            return "/paginas/adm/professores/listar.xhtml?faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Erro ao salvar o registro!");
            return null;
        }
    }

    public Integer getTipoBusca() {
        return tipoBusca;
    }

    public void setTipoBusca(Integer tipoBusca) {
        this.tipoBusca = tipoBusca;
    }

    public String getBusca() {
        return busca;
    }

    public void setBusca(String busca) {
        this.busca = busca;
    }

    public ArrayList<PessoaModel> getProfessoresBusca() {
        return professoresBusca;
    }

    public void setProfessoresBusca(ArrayList<PessoaModel> professoresBusca) {
        this.professoresBusca = professoresBusca;
    }
    
    public String iconeBloqueiaDesbloqueia(PessoaModel obj){
        if(obj.getAtivo()==1){
            return "ui-icon-locked";
        }else{
            return "ui-icon-unlocked";
        }
    }
    
    public String perguntaBloqueiaDesbloqueia(PessoaModel obj){
        if(obj.getAtivo()==1){
            return "Confirma o bloqueio do registro "+obj.getSeq_pessoa()+"?";
        }else{
            return "Confirma o desbloqueio do registro "+obj.getSeq_pessoa()+"?";
        }
    }
    
    public void Todos(){
        PessoaDao pessoadao=new PessoaDao();
        professoresBusca=pessoadao.buscar("where tipo=2 or tipo=1");
    }
    
    public String Listar(){
        PessoaDao pessoadao=new PessoaDao();
        professoresBusca=pessoadao.buscar("where tipo=2 or tipo=1");
        return "/paginas/adm/professores/listar.xhtml?faces-redirect=true";
    }
    
    public void Busca(){
        PessoaDao pessoadao=new PessoaDao();
        professoresBusca=new ArrayList<>();
        if(!busca.equals("")){
            if(tipoBusca==0){
                professoresBusca=pessoadao.buscar("where seq_pessoa="+busca);
            }else{
                if(tipoBusca==1){
                    professoresBusca=pessoadao.buscar("where cpf like '%"+busca+"%'");
                }else{
                    professoresBusca=pessoadao.buscar("where nome like '%"+busca+"%'");
                }
            }
        }else{
            Todos();
        }
    }
    
    public String editarProfessor(PessoaModel obj){
        professor=new PessoaModel();
        login="";
        senha="";
        login=obj.getLogin();
        senha=obj.getSenha();
        professor=obj;
        return "editar?faces-redirect=true";
    }
    
    public String SalvarCadastroProfessor(){
        PessoaDao pessoadao=new PessoaDao();
        professor.setLogin(login);
        professor.setSenha(senha);
        if(pessoadao.alterar(professor)){
            UtilMensagens.mensagemSucesso("Registro alterado com sucesso!");
            professor=new PessoaModel();
            return "/paginas/adm/professores/listar.xhtml?faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Erro ao salvar os dados!");
            return null;
        }
    }
    
    public String Cancelar(){
        professor=new PessoaModel();
        return "/paginas/adm/professores.xhtml?faces-redirect=true";
    }
    
    public String CancelarEdicao(){
        professor=new PessoaModel();
        return "/paginas/adm/professores/listar.xhtml?faces-redirect=true";
    }
    
    public String novo(){
        professor=new PessoaModel();
        login="";
        senha="";
        return"/paginas/adm/professores/novo.xhtml?faces-redirect=true";
    }
    
    public void BloqueiaDesbloqueia(PessoaModel obj){
        boolean ativoant=false;
        if(obj.getAtivo()==1){
            obj.setAtivo(0);
            ativoant=true;
        }else{
            obj.setAtivo(1);
        }
        PessoaDao pessoadao=new PessoaDao();
        if(pessoadao.alterar(obj)){
            if(ativoant){
                UtilMensagens.mensagemSucesso("Registro bloqueado com sucesso!");
            }else{
                UtilMensagens.mensagemSucesso("Registro desbloqueado com sucesso!");
            }
        }else{
            UtilMensagens.mensagemErro("Problemas na alteração do registro!");
        }
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
    
    public String TipoPessoa(PessoaModel obj){
        if(obj.getTipo()==1){
            return "Administrador";
        }else{
            return "Professor";
        }
    }
}