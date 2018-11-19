package br.com.sagap.dao;

import br.com.sagap.model.GradeNotaModel;
import br.com.sagap.util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GradeDao {
    
    public boolean inserir(GradeNotaModel obj){
        String query="insert into sg_grade_notas (descricao,valor,peso) values (?,?,?)";
        try {
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            sql.setString(1, obj.getDescricao());
            sql.setInt(2, obj.getValor());
            sql.setInt(3, obj.getPeso());
            if(!sql.execute()){
                con.conex.close();
                return true;
            }else{
                con.conex.close();
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GradeDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GradeDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public ArrayList buscar(String qry){
        String query="select * from sg_grade_notas "+qry;
        try {
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            ResultSet rs=sql.executeQuery();
            ArrayList<GradeNotaModel> retorno=new ArrayList<>();
            while(rs.next()){
                GradeNotaModel grade=new GradeNotaModel(rs.getInt("seq_grade_nota"), rs.getString("descricao"), rs.getInt("valor"), rs.getInt("peso"));
                retorno.add(grade);
            }
            con.conex.close();
            return retorno;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GradeDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(GradeDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}