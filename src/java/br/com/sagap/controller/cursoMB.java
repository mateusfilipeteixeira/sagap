package br.com.sagap.controller;

import br.com.sagap.dao.CursoDao;
import br.com.sagap.dao.InstituicaoDao;
import br.com.sagap.model.CursoModel;
import br.com.sagap.model.InstituicaoModel;
import br.com.sagap.util.UtilMensagens;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "cursoMB")
@SessionScoped
public class cursoMB implements Serializable {
    
    private CursoModel curso;
    private ArrayList<CursoModel> cursos=new ArrayList<>();
    private ArrayList<InstituicaoModel> instituicoes;

    public cursoMB() {
    }
    
    public String Novo(){
        curso=new CursoModel();
        InstituicaoDao instituicaodao=new InstituicaoDao();
        instituicoes=instituicaodao.Buscar();
        return "/paginas/adm/curso/novo.xhtml?faces-redirect=true";
    }
    
    public String Listar(){
        cursos=new ArrayList<>();
        CursoDao cursodao=new CursoDao();
        cursos=cursodao.buscar("order by seq_curso");
        return "/paginas/adm/curso/listar.xhtml?faces-redirect=true";
    }
    
    public String Cancelar(){
        curso=new CursoModel();
        return "/paginas/adm/cursos.xhtml?faces-redirect=true";
    }

    public CursoModel getCurso() {
        return curso;
    }

    public void setCurso(CursoModel curso) {
        this.curso = curso;
    }

    public ArrayList<CursoModel> getCursos() {
        return cursos;
    }

    public void setCursos(ArrayList<CursoModel> cursos) {
        this.cursos = cursos;
    }

    public ArrayList<InstituicaoModel> getInstituicoes() {
        return instituicoes;
    }

    public void setInstituicoes(ArrayList<InstituicaoModel> instituicoes) {
        this.instituicoes = instituicoes;
    }
    
    public String GravarCurso(){
        CursoDao cursodao=new CursoDao();
        if(cursodao.Inserir(curso)){
            UtilMensagens.mensagemSucesso("Curso inserido com sucesso!");
            return "/paginas/adm/cursos.xhtml?faces-redirect=true";
        }else{
            UtilMensagens.mensagemErro("Erro na inserção do curso!");
            return null;
        }
    }
    
    public String iconeBloqueiaDesbloqueia(CursoModel obj){
        if(obj.getAtivo()==1){
            return "ui-icon-locked";
        }else{
            return "ui-icon-unlocked";
        }
    }
    
    public String perguntaBloqueiaDesbloqueia(CursoModel obj){
        if(obj.getAtivo()==1){
            return "Confirma o bloqueio do registro "+obj.getSeq_curso()+"?";
        }else{
            return "Confirma o desbloqueio do registro "+obj.getSeq_curso()+"?";
        }
    }
    
    public void BloqueiaDesbloqueia(CursoModel obj){
        boolean ativoant=false;
        if(obj.getAtivo()==1){
            obj.setAtivo(0);
            ativoant=true;
        }else{
            obj.setAtivo(1);
        }
        CursoDao cursodao=new CursoDao();
        if(cursodao.alterar(obj)){
            if(ativoant){
                UtilMensagens.mensagemSucesso("Registro bloqueado com sucesso!");
            }else{
                UtilMensagens.mensagemSucesso("Registro desbloqueado com sucesso!");
            }
        }else{
            UtilMensagens.mensagemErro("Problemas na alteração do registro!");
        }
    }
    
    public String EditarCurso(CursoModel obj){
        curso=obj;
        InstituicaoDao instituicaodao=new InstituicaoDao();
        instituicoes=instituicaodao.Buscar();
        return "editar?faces-redirect=true";
    }
    
    public String SalvarCurso(){
        CursoDao cursodao=new CursoDao();
        if(cursodao.alterar(curso)){
            UtilMensagens.mensagemSucesso("Registro alterado com sucesso!");
            return (Listar());
        }else{
            UtilMensagens.mensagemErro("Problemas na alteração do registro!");
            return null;
        }
    }
}