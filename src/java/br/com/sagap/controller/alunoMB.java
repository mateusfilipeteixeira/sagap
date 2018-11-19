package br.com.sagap.controller;

import br.com.sagap.dao.PessoaDao;
import br.com.sagap.model.CidadeModel;
import br.com.sagap.model.PessoaModel;
import br.com.sagap.util.UtilMensagens;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "alunoMB")
@SessionScoped
public class alunoMB implements Serializable {
    
    private PessoaModel aluno = new PessoaModel();
    private Integer ativo=1;
    private String estado="";
    private CidadeModel cidade=new CidadeModel();
    private Integer tipoBusca=3;
    private String busca="";
    private ArrayList<PessoaModel> alunosBusca=new ArrayList<>();

    public alunoMB() {
        PessoaDao pessoadao=new PessoaDao();
        alunosBusca=pessoadao.buscar("where tipo=3");
    }

    public PessoaModel getAluno() {
        return aluno;
    }

    public void setAluno(PessoaModel aluno) {
        this.aluno = aluno;
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
    
    public String salvarAluno(){
        aluno.setAtivo(ativo);
        aluno.setTipo(3);
        aluno.setCidade(cidade);
        PessoaDao pessoadao=new PessoaDao();
        if(pessoadao.salvar(aluno)){
            aluno=new PessoaModel();
            UtilMensagens.mensagemSucesso("Aluno salvo com sucesso!");
            return "/paginas/adm/index.xhtml?faces-redirect=true";
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

    public ArrayList<PessoaModel> getAlunosBusca() {
        return alunosBusca;
    }

    public void setAlunosBusca(ArrayList<PessoaModel> alunosBusca) {
        this.alunosBusca = alunosBusca;
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
        alunosBusca=pessoadao.buscar("where tipo=3");
    }
    
    public String Listar(){
        PessoaDao pessoadao=new PessoaDao();
        alunosBusca=pessoadao.buscar("where tipo=3");
        return "/paginas/adm/alunos/listar.xhtml?faces-redirect=true";
    }
    
    public void Busca(){
        PessoaDao pessoadao=new PessoaDao();
        alunosBusca=new ArrayList<>();
        if(!busca.equals("")){
            if(tipoBusca==0){
                alunosBusca=pessoadao.buscar("where seq_pessoa="+busca);
            }else{
                if(tipoBusca==1){
                    alunosBusca=pessoadao.buscar("where cpf like '%"+busca+"%'");
                }else{
                    alunosBusca=pessoadao.buscar("where nome like '%"+busca+"%'");
                }
            }
        }else{
            Todos();
        }
    }
    
    public String editarAluno(PessoaModel obj){
        aluno=obj;
        return "editar?faces-redirect=true";
    }
    
    public String SalvarCadastroAluno(){
        PessoaDao pessoadao=new PessoaDao();
        if(pessoadao.alterar(aluno)){
            UtilMensagens.mensagemSucesso("Registro alterado com sucesso!");
            aluno=new PessoaModel();
            return "/paginas/adm/alunos/listar.xhtml?faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Erro ao salvar os dados!");
            return null;
        }
    }
    
    public String Cancelar(){
        aluno=new PessoaModel();
        return "/paginas/adm/alunos.xhtml?faces-redirect=true";
    }
    
    public String CancelarEdicao(){
        aluno=new PessoaModel();
        return "/paginas/adm/alunos/listar.xhtml?faces-redirect=true";
    }
    
    public String novo(){
        aluno=new PessoaModel();
        return"/paginas/adm/alunos/novo.xhtml?faces-redirect=true";
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
    
}