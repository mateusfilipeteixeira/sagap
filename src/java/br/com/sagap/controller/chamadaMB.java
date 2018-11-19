package br.com.sagap.controller;

import br.com.sagap.dao.AlunoDao;
import br.com.sagap.dao.AulaDao;
import br.com.sagap.dao.ChamadaDao;
import br.com.sagap.dao.GenericDao;
import br.com.sagap.dao.GradeMateriasDao;
import br.com.sagap.model.AlunoModel;
import br.com.sagap.model.AulaModel;
import br.com.sagap.model.ChamadaModel;
import br.com.sagap.model.GradeMateriasModel;
import br.com.sagap.model.PessoaModel;
import br.com.sagap.util.UtilMensagens;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


@ManagedBean(name = "chamadaMB")
@SessionScoped
public class chamadaMB implements Serializable {
    
    private ArrayList<AulaModel> aulas=new ArrayList<>();
    private AulaModel aula=new AulaModel();
    private PessoaModel professor=new PessoaModel();
    private ArrayList<AlunoModel> alunos=new ArrayList<>();
    private ArrayList<ChamadaModel> chamadas=new ArrayList<>();
    private ArrayList<GradeMateriasModel> materias=new ArrayList<>();
    private GradeMateriasModel materia=new GradeMateriasModel();
    private Integer totalAulas=0;
    
    public chamadaMB() {
    }
    
    public String AcompanhamentoFaltas(AulaModel obj){
        aula=obj;
        alunos=new ArrayList<>();
        AlunoDao dao=new AlunoDao();
        alunos=dao.buscar("where materia="+obj.getMateria().getSeq_grade_materias()+" and aluno in (select seq_pessoa from sg_pessoa where ativo=1)");
        GenericDao gd=new GenericDao();
        totalAulas=gd.IntQuery("select count(*) as resultado from sg_aula where materia="+obj.getMateria().getSeq_grade_materias());
        return "chamada?faces-redirect=true";
    }
    
    public String ConsultaFaltas(AlunoModel obj){
        GenericDao gd=new GenericDao();
        int faltas=gd.IntQuery("select count(*) as resultado from sg_chamada where presente=0 and aluno="+obj.getAluno().getSeq_pessoa()+" and aula="+aula.getSeq_aula());
        return Integer.toString(faltas);
    }
    
    public String quantidadeAulas(){
        GenericDao gd=new GenericDao();
        int qtdaulas=gd.IntQuery("select count(*) as resultado from sg_chamada where aula="+aula.getSeq_aula());
        return Integer.toString(qtdaulas);
    }
    
    public String FinalizaAlteracaoChamada(){
        ChamadaDao dao=new ChamadaDao();
        for(int i=0;i<chamadas.size();i++){
            dao.Alterar(chamadas.get(i));
        }
        chamadas=new ArrayList<>();
        UtilMensagens.mensagemSucesso("Chamada alterada com sucesso!");
        return ListarTurma();
    }
    
    public String EditarChamada(AulaModel obj){
        aula=obj;
        chamadas=new ArrayList<>();
        ChamadaDao dao=new ChamadaDao();
        chamadas=dao.Buscar("where aula="+aula.getSeq_aula());
        return "editar?faces-redirect=true";
    }
    
    public String ListarAulasAcompanhamento(GradeMateriasModel obj){
        materia=obj;
        alunos=new ArrayList<>();
        AlunoDao ad=new AlunoDao();
        alunos=ad.buscar("where materia="+obj.getSeq_grade_materias()+" and aluno in (select seq_pessoa from sg_pessoa where ativo=1)");
        GenericDao gd=new GenericDao();
        totalAulas=gd.IntQuery("select count(*) as resultado from sg_aula where materia="+obj.getSeq_grade_materias());
        return "chamada?faces-redirect=true";
    }
    
    public String ListarTurmaAcompanhamento(){
        HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        professor=(PessoaModel) session.getAttribute("usuario");
        GradeMateriasDao dao=new GradeMateriasDao();
        materias=dao.buscar("where professor="+professor.getSeq_pessoa());
        return "/paginas/user/chamada/acompanhamento/listarturma.xhtml?faces-redirect=true";
    }
    
    public String ListarAulas(GradeMateriasModel obj){
        materia=obj;
        AulaDao dao=new AulaDao();
        aulas=new ArrayList<>();
        aulas=dao.Buscar("where materia="+obj.getSeq_grade_materias()+" and seq_aula in (select aula from sg_chamada) order by seq_aula");
        return "/paginas/user/chamada/listaraulas.xhtml?faces-redirect=true";
    }
    
    public String ListarTurma(){
        HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        professor=(PessoaModel) session.getAttribute("usuario");
        GradeMateriasDao dao=new GradeMateriasDao();
        materias=dao.buscar("where professor="+professor.getSeq_pessoa());
        return "/paginas/user/chamada/listarturma.xhtml?faces-redirect=true";
    }
    
    public String RealizarChamada(AulaModel obj){
        aula=obj;
        AlunoDao ad=new AlunoDao();
        alunos=new ArrayList<>();
        int materia=aula.getMateria().getSeq_grade_materias();
        alunos=ad.buscar("where materia="+materia+" and aluno in (select seq_pessoa from sg_pessoa where ativo=1)");
        chamadas=new ArrayList<>();
        for(int i=0;i<alunos.size();i++){
            if(alunos.get(i).getAluno().getAtivo()==1){
                ChamadaModel chamada=new ChamadaModel(0, aula, alunos.get(i).getAluno(), 0);
                chamadas.add(chamada);
            }
        }
        return "/paginas/user/chamada/chamada.xhtml?faces-redirect=true";
    }
    
    public void TestarPresenca(){
        if(chamadas.get(0).getPresente()==1){
            UtilMensagens.mensagemSucesso("Presente");
            System.out.println("presente");
        }else{
            UtilMensagens.mensagemSucesso("Faltou");
            System.out.println("faltou");
        }
    }
    
    public String FinalizaChamada(){
        ChamadaDao dao=new ChamadaDao();
        for(int i=0;i<chamadas.size();i++){
            dao.Inserir(chamadas.get(i));
        }
        chamadas=new ArrayList<>();
        UtilMensagens.mensagemSucesso("Chamada registrada com sucesso!");
        return novaChamada();
    }
    
    public void SetarPresenca(ChamadaModel obj){
        chamadas.set(chamadas.indexOf(obj), obj);
    }
    
    public String novaChamada(){
        HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        professor=(PessoaModel) session.getAttribute("usuario");
        AulaDao auladao=new AulaDao();
        aulas=new ArrayList<>();
        aulas=auladao.Buscar("where seq_aula not in (select aula from sg_chamada) and professor="+professor.getSeq_pessoa()+" order by seq_aula");
        return "/paginas/user/chamada/novo.xhtml?faces-redirect=true";
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

    public PessoaModel getProfessor() {
        return professor;
    }

    public void setProfessor(PessoaModel professor) {
        this.professor = professor;
    }

    public ArrayList<AlunoModel> getAlunos() {
        return alunos;
    }

    public void setAlunos(ArrayList<AlunoModel> alunos) {
        this.alunos = alunos;
    }

    public ArrayList<ChamadaModel> getChamadas() {
        return chamadas;
    }

    public void setChamadas(ArrayList<ChamadaModel> chamadas) {
        this.chamadas = chamadas;
    }

    public ArrayList<GradeMateriasModel> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<GradeMateriasModel> materias) {
        this.materias = materias;
    }

    public GradeMateriasModel getMateria() {
        return materia;
    }

    public void setMateria(GradeMateriasModel materia) {
        this.materia = materia;
    }
}