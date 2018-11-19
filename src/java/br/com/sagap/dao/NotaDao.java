package br.com.sagap.dao;

import br.com.sagap.model.GradeMateriasModel;
import br.com.sagap.model.GradeNotaModel;
import br.com.sagap.model.NotaModel;
import br.com.sagap.model.PessoaModel;
import br.com.sagap.util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotaDao {
    public boolean inserir(NotaModel obj){
        String query="insert into sg_notas (grade_nota,valor_sem_peso,valor_com_peso,aluno,materia) values (?,?,?,?,?)";
        try {
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            sql.setInt(1, obj.getGrade_nota().getSeq_grade_nota());
            sql.setFloat(2, obj.getValor_sem_peso());
            sql.setFloat(3, obj.getValor_com_peso());
            sql.setInt(4, obj.getAluno().getSeq_pessoa());
            sql.setInt(5, obj.getMateria().getSeq_grade_materias());
            boolean retorno=!sql.execute();
            if(retorno){
                System.out.println("INSERIDO");
            }else{
                System.out.println("NÃO INSERIDO");
            }
            con.conex.close();
            return retorno;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NotaDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(NotaDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean alterar(NotaModel obj){
        String query="update sg_notas set grade_nota=?,valor_sem_peso=?,valor_com_peso=?,aluno=?,materia=? where seq_nota=?";
        try {
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            sql.setInt(1, obj.getGrade_nota().getSeq_grade_nota());
            sql.setFloat(2, obj.getValor_sem_peso());
            sql.setFloat(3, obj.getValor_com_peso());
            sql.setInt(4, obj.getAluno().getSeq_pessoa());
            sql.setInt(5, obj.getMateria().getSeq_grade_materias());
            sql.setInt(6, obj.getSeq_nota());
            boolean retorno=!sql.execute();
            con.conex.close();
            if(retorno){
                System.out.println("INSERIDO");
            }else{
                System.out.println("NÃO INSERIDO");
            }
            return retorno;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NotaDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(NotaDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public ArrayList buscar(String qry){
        String query="select * from sg_notas "+qry;
        System.out.println(query);
        try {
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            ResultSet rs=sql.executeQuery();
            ArrayList<NotaModel> retorno=new ArrayList<>();
            GradeDao gd=new GradeDao();
            PessoaDao pd=new PessoaDao();
            GradeMateriasDao md=new GradeMateriasDao();
            while(rs.next()){
                ArrayList<GradeNotaModel> grade=new ArrayList<>();
                grade=gd.buscar("where seq_grade_nota="+rs.getInt("grade_nota"));
                ArrayList<PessoaModel> aluno=new ArrayList<>();
                aluno=pd.buscar("where seq_pessoa="+rs.getInt("aluno"));
                ArrayList<GradeMateriasModel> materia=new ArrayList<>();
                materia=md.buscar("where seq_grade_materias="+rs.getInt("materia"));
                NotaModel nota=new NotaModel(rs.getInt("seq_nota"), grade.get(0), rs.getFloat("valor_sem_peso"), rs.getFloat("valor_com_peso"), rs.getDate("dt_cadastro"), aluno.get(0), materia.get(0));
                retorno.add(nota);
            }
            con.conex.close();
            return retorno;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NotaDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(NotaDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}