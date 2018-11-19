package br.com.sagap.controller;

import br.com.sagap.dao.AlunoDao;
import br.com.sagap.dao.GenericDao;
import br.com.sagap.dao.GradeDao;
import br.com.sagap.dao.GradeMateriasDao;
import br.com.sagap.dao.NotaDao;
import br.com.sagap.model.AlunoModel;
import br.com.sagap.model.GradeMateriasModel;
import br.com.sagap.model.GradeNotaModel;
import br.com.sagap.model.NotaModel;
import br.com.sagap.model.PessoaModel;
import br.com.sagap.util.UtilMensagens;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "notaMB")
@SessionScoped
public class notaMB implements Serializable {

    private PessoaModel professor=new PessoaModel();
    private ArrayList<GradeMateriasModel> materias=new ArrayList<>();
    private ArrayList<AlunoModel> alunos=new ArrayList<>();
    private AlunoModel cadAluno=new AlunoModel();
    private GradeMateriasModel materia=new GradeMateriasModel();
    private ArrayList<NotaModel> notas=new ArrayList<>();
    private ArrayList<GradeNotaModel> gradeNotas=new ArrayList();
    
    public notaMB() {
    }
    
    public String salvarEdicao(){
        NotaDao nd=new NotaDao();
        for(int i=0;i<notas.size();i++){
            nd.alterar(notas.get(i));
        }
        UtilMensagens.mensagemSucesso("Nota registrada com sucesso!");
        AlunoDao ad=new AlunoDao();
        alunos=new ArrayList<>();
        alunos=ad.buscar("where materia="+materia.getSeq_grade_materias()+" and aluno in (select seq_pessoa from sg_pessoa where ativo=1)");
        notas=new ArrayList<>();
        for(int i=0;i<alunos.size();i++){
            ArrayList<NotaModel> nt=new ArrayList<>();
            nt=nd.buscar("where aluno="+alunos.get(i).getAluno().getSeq_pessoa()+" and materia="+materia.getSeq_grade_materias()+" order by grade_nota");
            //notas.add(nt.get(0));
            alunos.get(i).setNotas(notas);
        }
        GradeDao gd=new GradeDao();
        gradeNotas=new ArrayList<>();
        gradeNotas=gd.buscar("order by seq_grade_nota");
        return "/paginas/user/notas/listaralunos.xhtml?faces-redirect=true";
    }
    
    public String cancelarEdicao(){
        AlunoDao ad=new AlunoDao();
        alunos=new ArrayList<>();
        alunos=ad.buscar("where materia="+materia.getSeq_grade_materias()+" and aluno in (select seq_pessoa from sg_pessoa where ativo=1)");
        NotaDao nd=new NotaDao();
        notas=new ArrayList<>();
        for(int i=0;i<alunos.size();i++){
            ArrayList<NotaModel> nt=new ArrayList<>();
            nt=nd.buscar("where aluno="+alunos.get(i).getAluno().getSeq_pessoa()+" and materia="+materia.getSeq_grade_materias()+" order by grade_nota");
            //notas.add(nt.get(0));
            alunos.get(i).setNotas(notas);
        }
        GradeDao gd=new GradeDao();
        gradeNotas=new ArrayList<>();
        gradeNotas=gd.buscar("order by seq_grade_nota");
        return "/paginas/user/notas/listaralunos.xhtml?faces-redirect=true";
    }
    
    public String editarNota(AlunoModel obj){
        cadAluno=obj;
        NotaDao dao=new NotaDao();
        notas=new ArrayList<>();
        notas=dao.buscar("where aluno="+obj.getAluno().getSeq_pessoa()+" and materia="+materia.getSeq_grade_materias()+" order by grade_nota");
        return "/paginas/user/notas/editarnota.xhtml?faces-redirect=true";
    }
    
    public String notaAluno(AlunoModel obj){
        GenericDao gd=new GenericDao();
        float nota=gd.FloatQuery("select sum(valor_com_peso) as resultado from sg_notas where aluno="+obj.getAluno().getSeq_pessoa()+" and materia="+materia.getSeq_grade_materias());
        NumberFormat format=new DecimalFormat("#,#");
        System.out.println(nota);
        //return format.format(nota/10);
        return Float.toString(nota/10);
    }
    
    public String aprovado(AlunoModel obj){
        GenericDao gd=new GenericDao();
        float nota=gd.FloatQuery("select sum(valor_com_peso) as resultado from sg_notas where aluno="+obj.getAluno().getSeq_pessoa()+" and materia="+materia.getSeq_grade_materias());
        if(nota>=60){
            return "A";
        }else{
            return "R";
        }
    }
    
    public String ListarTurmas(){
        HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        professor=(PessoaModel) session.getAttribute("usuario");
        GradeMateriasDao dao=new GradeMateriasDao();
        materias=new ArrayList<>();
        materias=dao.buscar("where professor="+professor.getSeq_pessoa());
        System.out.println(materias.size());
        return "/paginas/user/notas/listarturma.xhtml?faces-redirect=true";
    }
    
    public String SelecionaTurma(GradeMateriasModel obj){
        materia=obj;
        AlunoDao ad=new AlunoDao();
        alunos=new ArrayList<>();
        alunos=ad.buscar("where materia="+materia.getSeq_grade_materias()+" and aluno in (select seq_pessoa from sg_pessoa where ativo=1)");
        NotaDao nd=new NotaDao();
        notas=new ArrayList<>();
        for(int i=0;i<alunos.size();i++){
            ArrayList<NotaModel> nt=new ArrayList<>();
            nt=nd.buscar("where aluno="+alunos.get(i).getAluno().getSeq_pessoa()+" and materia="+materia.getSeq_grade_materias()+" order by grade_nota");
            //notas.add(nt.get(0));
            alunos.get(i).setNotas(notas);
        }
        GradeDao gd=new GradeDao();
        gradeNotas=new ArrayList<>();
        gradeNotas=gd.buscar("order by seq_grade_nota");
        return "/paginas/user/notas/listaralunos.xhtml?faces-redirect=true";
    }

    public PessoaModel getProfessor() {
        return professor;
    }

    public void setProfessor(PessoaModel professor) {
        this.professor = professor;
    }

    public ArrayList<GradeMateriasModel> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<GradeMateriasModel> materias) {
        this.materias = materias;
    }

    public ArrayList<AlunoModel> getAlunos() {
        return alunos;
    }

    public void setAlunos(ArrayList<AlunoModel> alunos) {
        this.alunos = alunos;
    }

    public GradeMateriasModel getMateria() {
        return materia;
    }

    public void setMateria(GradeMateriasModel materia) {
        this.materia = materia;
    }

    public ArrayList<NotaModel> getNotas() {
        return notas;
    }

    public void setNotas(ArrayList<NotaModel> notas) {
        this.notas = notas;
    }

    public AlunoModel getCadAluno() {
        return cadAluno;
    }

    public void setCadAluno(AlunoModel aluno) {
        this.cadAluno = aluno;
    }

    public ArrayList<GradeNotaModel> getGradeNotas() {
        return gradeNotas;
    }

    public void setGradeNotas(ArrayList<GradeNotaModel> gradeNotas) {
        this.gradeNotas = gradeNotas;
    }
    
}