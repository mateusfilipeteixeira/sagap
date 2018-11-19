package br.com.sagap.dao;

import br.com.sagap.model.CursoModel;
import br.com.sagap.model.TurmaModel;
import br.com.sagap.util.Conexao;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TurmaDao implements Serializable{
    
    public TurmaModel buscar(int id){
        String query="select * from sg_turma where seq_turma="+id;
        try {
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            ResultSet rs=sql.executeQuery();
            TurmaModel retorno=new TurmaModel();
            while(rs.next()){
                CursoDao cursodao=new CursoDao();
                ArrayList<CursoModel> curso=cursodao.buscar("where seq_curso="+rs.getInt("seq_curso"));
                retorno=new TurmaModel(rs.getInt("seq_turma"), curso.get(0), rs.getDate("dt_cadastro"), rs.getInt("ativo"), rs.getString("periodo"));
            }
            con.conex.close();
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(TurmaDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TurmaDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public ArrayList buscar(String qry){
        String query="select * from sg_turma "+qry;
        try {
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            ResultSet rs=sql.executeQuery();
            TurmaModel retorno=new TurmaModel();
            ArrayList<TurmaModel> turmas=new ArrayList<>();
            while(rs.next()){
                CursoDao cursodao=new CursoDao();
                ArrayList<CursoModel> curso=cursodao.buscar("where seq_curso="+rs.getInt("seq_curso"));
                retorno=new TurmaModel(rs.getInt("seq_turma"), curso.get(0), rs.getDate("dt_cadastro"), rs.getInt("ativo"), rs.getString("periodo"));
                turmas.add(retorno);
            }
            con.conex.close();
            return turmas;
        } catch (SQLException ex) {
            Logger.getLogger(TurmaDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TurmaDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public boolean inserir(TurmaModel obj){
        String query="insert into sg_turma (seq_curso,ativo,periodo) values (?,?,?)";
        try {
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            sql.setInt(1, obj.getCurso().getSeq_curso());
            sql.setInt(2, obj.getAtivo());
            sql.setString(3, obj.getPeriodo());
            boolean retorno=!(sql.execute());
            con.conex.close();
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(TurmaDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TurmaDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean alterar(TurmaModel obj){
        String query="update sg_turma set seq_curso=?,ativo=?,periodo=? where seq_turma=?";
        try {
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            sql.setInt(1, obj.getCurso().getSeq_curso());
            sql.setInt(2, obj.getAtivo());
            sql.setString(3, obj.getPeriodo());
            sql.setInt(4, obj.getSeq_turma());
            boolean retorno=!(sql.execute());
            con.conex.close();
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(TurmaDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TurmaDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
