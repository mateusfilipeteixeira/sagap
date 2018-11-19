package br.com.sagap.controller;

import br.com.sagap.dao.AulaDao;
import br.com.sagap.dao.GradeMateriasDao;
import br.com.sagap.model.AulaModel;
import br.com.sagap.model.GradeMateriasModel;
import br.com.sagap.model.PessoaModel;
import br.com.sagap.util.UtilMensagens;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "aulaMB")
@SessionScoped
public class aulaMB implements Serializable {
    
    private ArrayList<AulaModel> aulas=new ArrayList<>();
    private AulaModel aula;
    private ArrayList<GradeMateriasModel> materias=new ArrayList<>();
    private PessoaModel professor=new PessoaModel();
    private Integer tipoBusca;
    private String busca;
    
    public aulaMB() {
    }
    
    public String NovaAula(){
        HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        professor=(PessoaModel) session.getAttribute("usuario");
        aula=new AulaModel();
        aula.setProfessor(professor);
        GradeMateriasDao dao=new GradeMateriasDao();
        materias=dao.buscar("where professor="+professor.getSeq_pessoa());
        return "/paginas/user/aulas/novo.xhtml?faces-redirect=true";
    }
    
    public String CancelarGenerico(){
        return "/paginas/user/index.xhtml?faces-redirect=true";
    }
    
    public String ListarAulas(){
        HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        professor=(PessoaModel) session.getAttribute("usuario");
        System.out.println("******");
        System.out.println(professor.getSeq_pessoa());
        AulaDao dao=new AulaDao();
        aulas=new ArrayList<>();
        aulas=dao.Buscar("where professor="+professor.getSeq_pessoa()+" order by data desc");
        return "/paginas/user/aulas/listar.xhtml?faces-redirect=true";
    }
    
    public String SalvarAula(){
        System.out.println(aula.getMateria().getSeq_grade_materias());
        AulaDao dao=new AulaDao();
        if(dao.Inserir(aula)){
            UtilMensagens.mensagemSucesso("Registro inserido com sucesso!");
            return ListarAulas();
        }else{
            UtilMensagens.mensagemErro("Erro ao inserir o registro!");
            return null;
        }
    }
    
    public String SalvarAlteracoesAula(){
        AulaDao dao=new AulaDao();
        if(dao.Alterar(aula)){
            UtilMensagens.mensagemSucesso("Registro alterado com sucesso!");
            return ListarAulas();
        }else{
            UtilMensagens.mensagemErro("Erro ao alterar o registro!");
            return null;
        }
    }
    
    public void Buscar(){
        aulas=new ArrayList<>();
        AulaDao dao=new AulaDao();
        if(tipoBusca==1){
            aulas=dao.Buscar("where data like %"+busca+"% order by data desc");
        }else{
            aulas=dao.Buscar("where tema like %"+busca+"% order by data desc");
        }
    }
    
    public void Todos(){
        aulas=new ArrayList<>();
        AulaDao dao=new AulaDao();
        aulas=dao.Buscar("order by data desc");
    }
    
    public String EditarAula(AulaModel obj){
        aula=obj;
        HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        professor=(PessoaModel) session.getAttribute("usuario");
        GradeMateriasDao dao=new GradeMateriasDao();
        materias=dao.buscar("where professor="+professor.getSeq_pessoa());
        return "editar?faces-redirect=true";
    }
    
    public String CancelarEdicao(){
        aula=new AulaModel();
        return "listar?faces-redirect=true";
    }

    public ArrayList<AulaModel> getAulas() {
        return aulas;
    }

    public void setAulas(ArrayList<AulaModel> aulas) {
        this.aulas = aulas;
    }

    public AulaModel getAula() {
        return aula;
    }

    public void setAula(AulaModel aula) {
        this.aula = aula;
    }

    public ArrayList<GradeMateriasModel> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<GradeMateriasModel> materias) {
        this.materias = materias;
    }

    public PessoaModel getProfessor() {
        return professor;
    }

    public void setProfessor(PessoaModel professor) {
        this.professor = professor;
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
    
    
}
