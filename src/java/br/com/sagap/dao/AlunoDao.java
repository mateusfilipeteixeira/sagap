package br.com.sagap.dao;

import br.com.sagap.model.AlunoModel;
import br.com.sagap.model.GradeMateriasModel;
import br.com.sagap.model.GradeNotaModel;
import br.com.sagap.model.NotaModel;
import br.com.sagap.model.PessoaModel;
import br.com.sagap.model.TurmaModel;
import br.com.sagap.util.Conexao;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlunoDao implements Serializable{
    
    public ArrayList buscar(String qry){
        String query="select * from sg_grade_alunos "+qry;
        System.out.println(query);
        try {
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            ResultSet rs=sql.executeQuery();
            ArrayList<AlunoModel> retorno=new ArrayList<>();
            while(rs.next()){
                TurmaDao td=new TurmaDao();
                TurmaModel turma=td.buscar(rs.getInt("turma"));
                PessoaDao pd=new PessoaDao();
                ArrayList<PessoaModel> aluno=pd.buscar("where seq_pessoa="+rs.getInt("aluno"));
                GradeMateriasDao gd=new GradeMateriasDao();
                ArrayList<GradeMateriasModel> materia=gd.buscar("where seq_grade_materias="+rs.getInt("materia"));
                AlunoModel a=new AlunoModel(rs.getInt("seq_grade_alunos"), turma, rs.getDate("dt_cadastro"), aluno.get(0), materia.get(0), rs.getInt("integral"));
                retorno.add(a);
            }
            con.conex.close();
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(AlunoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AlunoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public boolean inserir(AlunoModel obj){
        String query="insert into sg_grade_alunos (turma,aluno,materia,integral) values (?,?,?,?)";
        try {
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            sql.setInt(1, obj.getTurma().getSeq_turma());
            System.out.println(obj.getAluno().getSeq_pessoa());
            sql.setInt(2, obj.getAluno().getSeq_pessoa());
            sql.setInt(3, obj.getMateria().getSeq_grade_materias());
            sql.setInt(4, obj.getIntegral());
            
            ArrayList<GradeNotaModel> grades=new ArrayList<>();
            GradeDao gd=new GradeDao();
            grades=gd.buscar("order by seq_grade_nota");
            NotaDao nd=new NotaDao();
            Date data = new Date(System.currentTimeMillis());
            for(int i=0;i<grades.size();i++){
                
                NotaModel nota=new NotaModel(0, grades.get(i), Float.parseFloat("0"), Float.parseFloat("0"), data, obj.getAluno(), obj.getMateria());
                nd.inserir(nota);
            }
            
            return !(sql.execute());
        } catch (SQLException ex) {
            Logger.getLogger(AlunoDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AlunoDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}