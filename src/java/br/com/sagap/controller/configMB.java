package br.com.sagap.controller;

import br.com.sagap.dao.GradeDao;
import br.com.sagap.model.GradeNotaModel;
import br.com.sagap.dao.InstituicaoDao;
import br.com.sagap.dao.PessoaDao;
import br.com.sagap.model.CidadeModel;
import br.com.sagap.model.InstituicaoModel;
import br.com.sagap.model.PessoaModel;
import br.com.sagap.util.UtilMensagens;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "configMB")
@SessionScoped
public class configMB implements Serializable{
    
    private InstituicaoModel instituicao;
    private GradeNotaModel grade=new GradeNotaModel();
    private ArrayList<GradeNotaModel> grades=new ArrayList<>();
    private int notafinal=0;
    private boolean botao=true;
    private PessoaModel usuario;
    private CidadeModel cidade=new CidadeModel();
    private String estado="";

    public PessoaModel getUsuario() {
        return usuario;
    }

    public void setUsuario(PessoaModel usuario) {
        this.usuario = usuario;
    }

    public GradeNotaModel getGrade() {
        return grade;
    }

    public void setGrade(GradeNotaModel grade) {
        this.grade = grade;
    }

    public ArrayList<GradeNotaModel> getGrades() {
        return grades;
    }

    public void setGrades(ArrayList<GradeNotaModel> grades) {
        this.grades = grades;
    }

    public configMB() {
        instituicao=new InstituicaoModel();
        HttpSession session=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        usuario=(PessoaModel) session.getAttribute("usuario");
    }
    
    public String inicio(){
        return "config?faces-redirect=true";
    }
    
    public String config(){
        System.out.println("DIRECIONAMENTO PARA GRADE");
        return "/paginas/adm/primeirouso/grade.xhtml";
    }

    public InstituicaoModel getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(InstituicaoModel instituicao) {
        this.instituicao = instituicao;
    }
    
    public String adicionaNota(){
        grades.add(grade);
        notafinal=notafinal+(grade.getValor() * (grade.getPeso()));
        if(notafinal==100){
            botao=false;
        }else{
            botao=true;
        }
        return "/paginas/adm/primeirouso/grade.xhtml";
    }
    
    public String salvaNota(){
        grades.set(grades.indexOf(grade), grade);
        notafinal=0;
        for(int i=0;i<grades.size();i++){
            notafinal=notafinal+(grades.get(i).getPeso() * grades.get(i).getValor());
        }
        if(notafinal==100){
            botao=false;
        }else{
            botao=true;
        }
        return "/paginas/adm/primeirouso/grade.xhtml";
    }
    
    public String nova(){
        grade=new GradeNotaModel();
        return "/paginas/adm/primeirouso/novagrade.xhtml";
    }

    public int getNotafinal() {
        return notafinal;
    }

    public void setNotafinal(int notafinal) {
        this.notafinal = notafinal;
    }
    
    public String cancelarGrade(){
        grade=new GradeNotaModel();
        return "/paginas/adm/primeirouso/grade.xhtml";
    }
    
    public String excluirGrade(GradeNotaModel obj){
        grades.remove(grades.indexOf(obj));
        notafinal=notafinal-(obj.getPeso() * obj.getValor());
        if(notafinal==100){
            botao=false;
        }else{
            botao=true;
        }
        return "/paginas/adm/primeirouso/grade.xhtml?faces-redirect=true";
    }
    
    public String editarGrade(GradeNotaModel obj){
        grade=obj;
        return "/paginas/adm/primeirouso/alter.xhtml";
    }

    public boolean isBotao() {
        return botao;
    }

    public void setBotao(boolean botao) {
        this.botao = botao;
    }
    
    public String revisaDados(){
        return "/paginas/adm/primeirouso/final.xhtml";
    }
    
    public String salvarConfiguracao(){
        InstituicaoDao instituicaodao=new InstituicaoDao();
        instituicao.setCidade(cidade);
        if(instituicaodao.inserir(instituicao)){
            GradeDao gradedao=new GradeDao();
            for(int i=0;i<grades.size();i++){
                gradedao.inserir(grades.get(i));
            }
            PessoaDao pessoadao=new PessoaDao();
            usuario.setTipo(1);
            if(pessoadao.alterar(usuario)){
                UtilMensagens.mensagemSucesso("Configuração concluída com sucesso!");
                FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
                return "/paginas/seguranca/login.xhtml";
            }
        }
        UtilMensagens.mensagemErro("Configuração não pôde ser salva!");
        return null;
    }

    public CidadeModel getCidade() {
        return cidade;
    }

    public void setCidade(CidadeModel cidade) {
        estado=cidade.getEstado().getNome();
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
