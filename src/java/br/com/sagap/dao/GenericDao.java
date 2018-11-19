package br.com.sagap.dao;

import br.com.sagap.util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenericDao {
    public Integer IntQuery(String query){
        try {
            System.out.println(query);
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            ResultSet rs=sql.executeQuery();
            while(rs.next()){
                return rs.getInt("resultado");
            }
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Float FloatQuery(String query){
        try {
            System.out.println(query);
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            ResultSet rs=sql.executeQuery();
            while(rs.next()){
                return rs.getFloat("resultado");
            }
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}