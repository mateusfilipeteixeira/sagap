package br.com.sagap.dao;

import br.com.sagap.model.CidadeModel;
import br.com.sagap.model.EstadoModel;
import br.com.sagap.util.Conexao;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CidadeDao implements Serializable{
    EstadoDao estadodao=new EstadoDao();
       
    public CidadeModel buscar(int id){
        String query="select * from sg_cidade where seq_cidade=?";
        try {
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            sql.setInt(1, id);
            ResultSet rs=sql.executeQuery();
            CidadeModel retorno=new CidadeModel();
            while(rs.next()){
                retorno.setSeq_cidade(rs.getInt("seq_cidade"));
                retorno.setNome(rs.getString("nome"));
                EstadoModel estado=new EstadoModel();
                estado=estadodao.buscar(rs.getInt("estado"));
                retorno.setEstado(estado);
            }
            rs.close();
            con.conex.close();
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(CidadeDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CidadeDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public ArrayList buscar(){
        String query="select * from sg_cidade order by nome";
        try {
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            ResultSet rs=sql.executeQuery();
            ArrayList<CidadeModel> retorno=new ArrayList<>();
            while(rs.next()){
                CidadeModel cidade=new CidadeModel();
                cidade.setSeq_cidade(rs.getInt("seq_cidade"));
                cidade.setNome(rs.getString("nome"));
                EstadoModel estado=new EstadoModel();
                estado=estadodao.buscar(rs.getInt("estado"));
                cidade.setEstado(estado);
                retorno.add(cidade);
            }
            rs.close();
            con.conex.close();
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(CidadeDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CidadeDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}