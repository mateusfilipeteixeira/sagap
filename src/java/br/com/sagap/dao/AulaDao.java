package br.com.sagap.dao;

import br.com.sagap.model.AulaModel;
import br.com.sagap.model.GradeMateriasModel;
import br.com.sagap.model.GradeNotaModel;
import br.com.sagap.model.NotaModel;
import br.com.sagap.model.PessoaModel;
import br.com.sagap.util.Conexao;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AulaDao implements Serializable{
    public boolean Inserir(AulaModel obj){
        try {
            Conexao con=new Conexao();
            String query="insert into sg_aula (professor,materia,tema,observacao) values (?,?,?,?)";
            PreparedStatement sql=con.conex.prepareStatement(query);
            sql.setInt(1, obj.getProfessor().getSeq_pessoa());
            sql.setInt(2, obj.getMateria().getSeq_grade_materias());
            sql.setString(3, obj.getTema());
            sql.setString(4, obj.getObservacao());
            boolean retorno=!sql.execute();
            con.conex.close();
            return retorno;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AulaDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(AulaDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean Alterar(AulaModel obj){
        try {
            Conexao con=new Conexao();
            String query="update sg_aula set professor=?, materia=?,tema=?,observacao=? where seq_aula="+obj.getSeq_aula();
            PreparedStatement sql=con.conex.prepareStatement(query);
            sql.setInt(1, obj.getProfessor().getSeq_pessoa());
            sql.setInt(2, obj.getMateria().getSeq_grade_materias());
            sql.setString(3, obj.getTema());
            sql.setString(4, obj.getObservacao());
            boolean retorno=!sql.execute();
            con.conex.close();
            return retorno;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AulaDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(AulaDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public ArrayList Buscar(String qry){
        try {
            Conexao con=new Conexao();
            String query="select * from sg_aula "+qry;
            System.out.println(query);
            PreparedStatement sql=con.conex.prepareStatement(query);
            ArrayList<AulaModel> retorno=new ArrayList<>();
            ResultSet rs=sql.executeQuery();
            while(rs.next()){
                PessoaDao pd=new PessoaDao();
                ArrayList<PessoaModel> professor=new ArrayList<>();
                professor=pd.buscar("where seq_pessoa="+rs.getInt("professor"));
                GradeMateriasDao gd=new GradeMateriasDao();
                ArrayList<GradeMateriasModel> materia=new ArrayList<>();
                materia=gd.buscar("where seq_grade_materias="+rs.getInt("materia"));
                AulaModel aula=new AulaModel(rs.getInt("seq_aula"), professor.get(0), materia.get(0), rs.getString("tema"), rs.getString("observacao"), rs.getDate("data"));
                retorno.add(aula);
            }
            rs.close();
            con.conex.close();
            return retorno;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AulaDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(AulaDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}