package br.com.sagap.dao;

import br.com.sagap.model.EstadoModel;
import br.com.sagap.util.Conexao;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EstadoDao implements Serializable{ 
    
    public ArrayList buscar(){
        ArrayList<EstadoModel> retorno=new ArrayList<>();
        String query="select * from sg_estado order by nome";
        PreparedStatement sql;
        try {
            Conexao con=new Conexao();
            sql = con.conex.prepareStatement(query);
            ResultSet rs=sql.executeQuery();
            while(rs.next()){
                EstadoModel estado=new EstadoModel();
                estado.setNome(rs.getString("nome"));
                estado.setSeq_estado(rs.getInt("seq_estado"));
                estado.setSigla(rs.getString("sigla"));
                retorno.add(estado);
            }
            rs.close();
            con.conex.close();
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(EstadoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EstadoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public EstadoModel buscar(int id){
        String query="select * from sg_estado where seq_estado=?";
        try {
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            sql.setInt(1, id);
            ResultSet rs=sql.executeQuery();
            EstadoModel retorno=new EstadoModel();
            while(rs.next()){
                retorno.setNome(rs.getString("nome"));
                retorno.setSeq_estado(rs.getInt("seq_estado"));
                retorno.setSigla(rs.getString("sigla"));
            }
            rs.close();
            con.conex.close();
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(EstadoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EstadoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }        
    }
}
