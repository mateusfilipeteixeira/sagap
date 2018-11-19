package br.com.sagap.controller;

import br.com.sagap.dao.AlunoDao;
import br.com.sagap.dao.CursoDao;
import br.com.sagap.dao.GradeMateriasDao;
import br.com.sagap.dao.PessoaDao;
import br.com.sagap.dao.TurmaDao;
import br.com.sagap.model.AlunoModel;
import br.com.sagap.model.CursoModel;
import br.com.sagap.model.GradeMateriasModel;
import br.com.sagap.model.PessoaModel;
import br.com.sagap.model.TurmaModel;
import br.com.sagap.util.UtilMensagens;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "turmaMB")
@SessionScoped
public class turmaMB implements Serializable {

    private ArrayList<CursoModel> cursos;
    private CursoModel cursotmp;
    private TurmaModel turma;
    private TurmaModel turmatmp;
    private ArrayList<TurmaModel> todasTurmas;
    private GradeMateriasModel grade;
    private ArrayList<GradeMateriasModel> grades;
    private ArrayList<GradeMateriasModel> todasGrades;
    private ArrayList<GradeMateriasModel> todasGradesMaterias;
    private ArrayList<PessoaModel> professores;
    private AlunoModel aluno;
    private AlunoModel aln;
    private ArrayList<PessoaModel> alunosCadastrados;
    private ArrayList<AlunoModel> alunos;
    private ArrayList<AlunoModel> materiasAluno;
    private ArrayList<AlunoModel> materiasSalvar=new ArrayList<>();
    private Integer integral = 1;
    private PessoaModel alunotmp;
    private GradeMateriasModel materiatmp;
    private Integer tipoBusca=3;
    private String busca="";

    public turmaMB() {
        CursoDao cursodao = new CursoDao();
        cursos = cursodao.buscar("order by seq_curso");
    }

    public String Nova() {
        CursoDao cursodao = new CursoDao();
        cursos = cursodao.buscar("order by seq_curso");
        turma = new TurmaModel();
        return "/paginas/adm/turmas/novo.xhtml?faces-redirect=true";
    }

    public String Grade() {
        grades = new ArrayList<>();
        grade = new GradeMateriasModel();
        TurmaDao turmadao = new TurmaDao();
        if (turmadao.inserir(turma)) {
            ArrayList<TurmaModel> t = turmadao.buscar("where seq_turma=(select max(seq_turma) from sg_turma)");
            turma = new TurmaModel();
            turma = t.get(0);
            return "/paginas/adm/turmas/novagrade.xhtml?faces-redirect=true";
        } else {
            UtilMensagens.mensagemErroDetalhe("Erro", "Não foi possível inserir o registro da Turma!");
            return null;
        }
    }

    public String Cancelar() {
        return "/paginas/adm/turmas.xhtml";
    }

    public String ExcluirGrade(GradeMateriasModel obj) {
        grades.remove(grades.indexOf(obj));
        return "/paginas/adm/turmas/novagrade.xhtml?faces-redirect=true";
    }

    public String AdicionarGrade() {
        grade = new GradeMateriasModel();
        PessoaDao pessoadao = new PessoaDao();
        professores = pessoadao.buscar("where tipo=2 and ativo=1 order by nome");
        return "/paginas/adm/turmas/adicionargrade.xhtml?faces-redirect=true";
    }

    public String SalvarGrade() {
        grades.add(grade);
        grade=new GradeMateriasModel();
        return "/paginas/adm/turmas/novagrade.xhtml?faces-redirect=true";
    }

    public String CancelarRegistroGrade() {
        grade = new GradeMateriasModel();
        return "/paginas/adm/turmas/novagrade.xhtml?faces-redirect=true";
    }

    public String EditarGrade(GradeMateriasModel obj) {
        grade = obj;
        return "/paginas/adm/turmas/editargrade.xhtml?faces-redirect=true";
    }

    public String SalvarAlteracaoGrade() {
        grades.set(grades.indexOf(grade), grade);
        grade = new GradeMateriasModel();
        return "/paginas/adm/turmas/novagrade.xhtml?faces-redirect=true";
    }

    public boolean ExisteGradeMat() {
        if (grades.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public String AlunosTurma() {
        GradeMateriasDao dao = new GradeMateriasDao();
        for (int i = 0; i < grades.size(); i++) {
            grades.get(i).setTurma(turma);
            System.out.println(grades.get(i).getNome_materia());
            dao.inserir(grades.get(i));
        }
        PessoaDao pessoadao = new PessoaDao();
        alunosCadastrados = pessoadao.buscar("where tipo=3 and ativo=1 order by nome");
        alunos = new ArrayList<>();
        aluno = new AlunoModel();
        System.out.println("select das disciplinas da turma");
        grades = dao.buscar("where turma=" + turma.getSeq_turma());
        return "/paginas/adm/alunos/turma/grade.xhtml?faces-redirect=true";
    }

    public String SelecionaAluno(PessoaModel obj) {
        aluno.setAluno(obj);
        aluno.setIntegral(integral);
        aluno.setTurma(turma);
        aluno.setMateria(grade);
        alunotmp = obj;
        return "/paginas/adm/alunos/turma/confirmar.xhtml?faces-redirect=true";
    }

    public String RetornoTipo(AlunoModel obj) {
        if (obj.getIntegral() == 1) {
            return "Integral";
        } else {
            return "Parcelado";
        }
    }

    public String AdicionarAluno() {
        aluno = new AlunoModel();
        integral = 1;
        return "/paginas/adm/alunos/turma/busca.xhtml?faces-redirect=true";
    }

    public String ExcluirAluno(AlunoModel obj) {
        alunos.remove(alunos.indexOf(obj));
        return "/paginas/adm/alunos/turma/grade.xhtml?faces-redirect=true";
    }
    
    public String GravaAluno(){
        if(aluno.getIntegral()==1){
            /*for(int i=0;i<grades.size();i++){
                System.out.println("sequencial");
                System.out.println(grades.get(i).getSeq_grade_materias());
                AlunoModel aln1=aluno;
                aln1.setTurma(grades.get(i).getTurma());
                aln1.setMateria(grades.get(i));
                aln1.setMateria(grades.get(i));
                alunos.add(aln1);
            }*/
            //alunos.add(aluno);
            materiasAluno=new ArrayList<>();
            for(int i=0;i<grades.size();i++){
                aln=new AlunoModel();
                aln.setAluno(alunotmp);
                aln.setIntegral(1);
                aln.setMateria(grades.get(i));
                aln.setTurma(turma);
                materiasAluno.add(aln);
            }
            for(int i=0;i<materiasAluno.size();i++){
                alunos.add(materiasAluno.get(i));
            }
            return "grade?faces-redirect=true";
            //return "materias?faces-redirect=true";
        }else{
            materiasAluno=new ArrayList<>();
            materiasSalvar=new ArrayList<>();
            aln=new AlunoModel();
            return "materias?faces-redirect=true";
        }
    }
    
    public void AtualizarDados(){
        materiatmp=aln.getMateria();
        aln.setTurma(materiatmp.getTurma());
        turmatmp=materiatmp.getTurma();
        cursotmp=turmatmp.getCurso();
    }
    
    public String AdicionarMateria(){
        GradeMateriasDao dao=new GradeMateriasDao();
        todasGrades=dao.buscar("");
        aln=new AlunoModel();
        aln.setAluno(alunotmp);
        aln.setIntegral(0);
        return "adicionarmateria?faces-redirect=true";
    }
    
    public String ConfirmarAdicao(){
        try {
            materiasAluno.add(aln);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "materias?faces-redirect=true";
    }
    
    public String CancelarAdicaoMateria(){
        cursotmp=new CursoModel();
        materiatmp=new GradeMateriasModel();
        turmatmp=new TurmaModel();
        return "materias?faces-redirect=true";
    }
    
    public String SalvarMateriasAluno(){
        AlunoDao dao=new AlunoDao();
        for(int i=0;i<alunos.size();i++){
            System.out.println("Aqui");
            System.out.println(alunos.get(i).getMateria().getSeq_grade_materias());
            dao.inserir(alunos.get(i));
        }
        UtilMensagens.mensagemSucesso("Registros inseridos com sucesso!");
        return "/paginas/adm/index.xhtml?faces-redirect=true";
    }
    
    public String GravarMateriasAluno(){
        for(int i=0;i<materiasAluno.size();i++){
            alunos.add(materiasAluno.get(i));
        }
        return "grade?faces-redirect=true";
    }
    
    public String ListarTurmas(){
        TurmaDao dao=new TurmaDao();
        todasTurmas=new ArrayList<>();
        todasTurmas=dao.buscar("order by seq_turma");
        materiasSalvar=new ArrayList<>();
        return "/paginas/adm/turmas/listar.xhtml?faces-redirect=true";
    }
    
    public String EditarTurma(TurmaModel obj){
        turma=obj;
        todasGrades=new ArrayList<>();
        GradeMateriasDao dao=new GradeMateriasDao();
        todasGrades=dao.buscar("where turma="+obj.getSeq_turma());
        return "editar?faces-redirect=true";
    }
    
    public String SalvarAlteracoes(){
        TurmaDao dao=new TurmaDao();
        if(dao.alterar(turma)){
            UtilMensagens.mensagemSucesso("Registro salvo com sucesso!");
            return "listar?faces-redirect=true";
        }else{
            UtilMensagens.mensagemSucesso("Problemas na alteração!");
            return null;
        }
    }
    
    public String EditarMateria(GradeMateriasModel obj){
        grade=obj;
        PessoaDao dao=new PessoaDao();
        professores=new ArrayList<>();
        professores=dao.buscar("where tipo=2 and ativo=1");
        return "editarmateria?faces-redirect=true";
    }
    
    public String SalvarAlteracoesMateria(){
        todasGrades.set(todasGrades.indexOf(grade), grade);
        return "editar?faces-redirect=true";
    }
    
    public String CancelarEdicaoMateria(){
        return "editar?faces-redirect=true";
    }
    
    public String iconeBloqueiaDesbloqueia(TurmaModel obj){
        if(obj.getAtivo()==1){
            return "ui-icon-locked";
        }else{
            return "ui-icon-unlocked";
        }
    }
    
    public String perguntaBloqueiaDesbloqueia(TurmaModel obj){
        if(obj.getAtivo()==1){
            return "Confirma o bloqueio do registro "+obj.getSeq_turma()+"?";
        }else{
            return "Confirma o desbloqueio do registro "+obj.getSeq_turma()+"?";
        }
    }
    
    public void BloqueiaDesbloqueia(TurmaModel obj){
        boolean ativoant=false;
        if(obj.getAtivo()==1){
            obj.setAtivo(0);
            ativoant=true;
        }else{
            obj.setAtivo(1);
        }
        TurmaDao turmadao=new TurmaDao();
        if(turmadao.alterar(obj)){
            if(ativoant){
                UtilMensagens.mensagemSucesso("Registro bloqueado com sucesso!");
            }else{
                UtilMensagens.mensagemSucesso("Registro desbloqueado com sucesso!");
            }
        }else{
            UtilMensagens.mensagemErro("Problemas na alteração do registro!");
        }
    }
    
    public String GradeCurricular(){
        PessoaDao dao=new PessoaDao();
        alunos=dao.buscar("where tipo=3 order by seq_pessoa");
        return "/paginas/adm/alunos/disciplinas/listar.xhtml?faces-redirect=true";
    }
    
    public String ListaGrade(PessoaModel obj){
        alunotmp=obj;
        AlunoDao dao=new AlunoDao();
        materiasAluno=new ArrayList<>();
        materiasAluno=dao.buscar("where aluno="+obj.getSeq_pessoa());
        return "/paginas/adm/alunos/disciplinas/listardisciplinas.xhtml?faces-redirect=true";
    }
    
    public String EditarDisciplinas(){
        PessoaDao dao=new PessoaDao();
        alunosCadastrados=new ArrayList<>();
        alunosCadastrados=dao.buscar("where tipo=3 order by seq_pessoa");
        return "/paginas/adm/alunos/disciplinas/listar.xhtml?faces-redirect=true";
    }
    
    public String AdicionarGradeCurricular(){
        GradeMateriasDao dao=new GradeMateriasDao();
        todasGradesMaterias=dao.buscar("");
        aln=new AlunoModel();
        aln.setAluno(alunotmp);
        return "adicionardisciplina?faces-redirect=true";
    }
    
    public String SalvarNovaGrade(){
        materiasAluno.add(aln);
        materiasSalvar.add(aln);
        return "listardisciplinas?faces-redirect=true";
    }
    
    public String SalvarAlteracoesGrade(){
        AlunoDao dao=new AlunoDao();
        System.out.println(materiasSalvar.size());
        for(int i=0;i<materiasSalvar.size();i++){
            System.out.println("TA NO FOR");
            materiasSalvar.get(i).setIntegral(0);
            dao.inserir(materiasSalvar.get(i));
        }
        UtilMensagens.mensagemSucesso("Novos registros salvos com sucesso!");
        return "listar?faces-redirect=true";
    }
    
    public void Busca(){
        PessoaDao pessoadao=new PessoaDao();
        alunosCadastrados=new ArrayList<>();
        if(!busca.equals("")){
            if(tipoBusca==0){
                alunosCadastrados=pessoadao.buscar("where seq_pessoa="+busca+" and tipo=3");
            }else{
                if(tipoBusca==1){
                    alunosCadastrados=pessoadao.buscar("where cpf like '%"+busca+"%'"+" and tipo=3");
                }else{
                    alunosCadastrados=pessoadao.buscar("where nome like '%"+busca+"%'"+" and tipo=3");
                }
            }
        }else{
            Todos();
        }
    }
    
    public void Todos(){
        PessoaDao pessoadao=new PessoaDao();
        alunosCadastrados=pessoadao.buscar("where tipo=3");
    }
    
    //**************GETERS E SETTERS********************************************
    
    public ArrayList<GradeMateriasModel> getTodasGrades() {
        return todasGrades;
    }
    
    public void setTodasGrades(ArrayList<GradeMateriasModel> todasGrades) {
        this.todasGrades = todasGrades;
    }

    public ArrayList<CursoModel> getCursos() {
        return cursos;
    }

    public void setCursos(ArrayList<CursoModel> cursos) {
        this.cursos = cursos;
    }

    public TurmaModel getTurma() {
        return turma;
    }

    public void setTurma(TurmaModel turma) {
        this.turma = turma;
    }

    public GradeMateriasModel getGrade() {
        return grade;
    }

    public void setGrade(GradeMateriasModel grade) {
        this.grade = grade;
    }

    public ArrayList<GradeMateriasModel> getGrades() {
        return grades;
    }

    public void setGrades(ArrayList<GradeMateriasModel> grades) {
        this.grades = grades;
    }

    public ArrayList<PessoaModel> getProfessores() {
        return professores;
    }

    public void setProfessores(ArrayList<PessoaModel> professores) {
        this.professores = professores;
    }

    public AlunoModel getAluno() {
        return aluno;
    }

    public void setAluno(AlunoModel aluno) {
        this.aluno = aluno;
    }

    public ArrayList<PessoaModel> getAlunosCadastrados() {
        return alunosCadastrados;
    }

    public void setAlunosCadastrados(ArrayList<PessoaModel> alunosCadastrados) {
        this.alunosCadastrados = alunosCadastrados;
    }

    public ArrayList<AlunoModel> getAlunos() {
        return alunos;
    }

    public void setAlunos(ArrayList<AlunoModel> alunos) {
        this.alunos = alunos;
    }

    public ArrayList<AlunoModel> getMateriasAluno() {
        return materiasAluno;
    }

    public void setMateriasAluno(ArrayList<AlunoModel> materiasAluno) {
        this.materiasAluno = materiasAluno;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public PessoaModel getAlunotmp() {
        return alunotmp;
    }

    public void setAlunotmp(PessoaModel alunotmp) {
        this.alunotmp = alunotmp;
    }

    public CursoModel getCursotmp() {
        return cursotmp;
    }

    public void setCursotmp(CursoModel cursotmp) {
        this.cursotmp = cursotmp;
    }

    public TurmaModel getTurmatmp() {
        return turmatmp;
    }

    public void setTurmatmp(TurmaModel turmatmp) {
        this.turmatmp = turmatmp;
    }

    public ArrayList<TurmaModel> getTodasTurmas() {
        return todasTurmas;
    }

    public void setTodasTurmas(ArrayList<TurmaModel> todasTurmas) {
        this.todasTurmas = todasTurmas;
    }

    public GradeMateriasModel getMateriatmp() {
        return materiatmp;
    }

    public void setMateriatmp(GradeMateriasModel materiatmp) {
        this.materiatmp = materiatmp;
    }

    public AlunoModel getAln() {
        return aln;
    }

    public void setAln(AlunoModel aln) {
        this.aln = aln;
    }

    public ArrayList<GradeMateriasModel> getTodasGradesMaterias() {
        return todasGradesMaterias;
    }

    public void setTodasGradesMaterias(ArrayList<GradeMateriasModel> todasGradesMaterias) {
        this.todasGradesMaterias = todasGradesMaterias;
    }

    public ArrayList<AlunoModel> getMateriasSalvar() {
        return materiasSalvar;
    }

    public void setMateriasSalvar(ArrayList<AlunoModel> materiasSalvar) {
        this.materiasSalvar = materiasSalvar;
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
