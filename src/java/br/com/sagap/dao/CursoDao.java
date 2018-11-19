package br.com.sagap.dao;

import br.com.sagap.model.CursoModel;
import br.com.sagap.model.InstituicaoModel;
import br.com.sagap.util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CursoDao {
    
    public ArrayList buscar(String qry){
        ArrayList<CursoModel> retorno=new ArrayList<>();
        String query="select * from sg_curso "+qry;
        try {
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            ResultSet rs=sql.executeQuery();
            while(rs.next()){
                //buscar instituição
                InstituicaoDao instituicaodao=new InstituicaoDao();
                InstituicaoModel instituicao=instituicaodao.Buscar(rs.getInt("instituicao"));
                CursoModel curso=new CursoModel(rs.getInt("seq_curso"), instituicao, rs.getString("nome"), rs.getString("descricao"), rs.getDate("dt_cadastro"), rs.getInt("ativo"));
                retorno.add(curso);
            }
            rs.close();
            con.conex.close();
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(CursoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean Inserir(CursoModel obj){
        String query="insert into sg_curso (instituicao,nome,descricao,ativo) values(?,?,?,?)";
        try {
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            sql.setInt(1, obj.getInstituicao().getSeq_instituicao());
            sql.setString(2, obj.getNome());
            sql.setString(3, obj.getDescricao());
            sql.setInt(4, obj.getAtivo());
            if(sql.execute()){
                con.conex.close();
                return false;
            }else{
                con.conex.close();
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CursoDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CursoDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean alterar(CursoModel obj){
        String query="update sg_curso set instituicao=?, nome=?, descricao=?, ativo=? where seq_curso=?";
        try {
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            sql.setInt(1, obj.getInstituicao().getSeq_instituicao());
            sql.setString(2, obj.getNome());
            sql.setString(3, obj.getDescricao());
            sql.setInt(4, obj.getAtivo());
            sql.setInt(5, obj.getSeq_curso());
            if(sql.execute()){
                con.conex.close();
                return false;
            }else{
                con.conex.close();
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CursoDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CursoDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
