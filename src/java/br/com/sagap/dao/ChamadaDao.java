package br.com.sagap.dao;

import br.com.sagap.model.AulaModel;
import br.com.sagap.model.ChamadaModel;
import br.com.sagap.model.PessoaModel;
import br.com.sagap.util.Conexao;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChamadaDao implements Serializable{
    public ArrayList Buscar(String qry){
        try {
            Conexao con=new Conexao();
            String query="select * from sg_chamada "+qry;
            System.out.println(query);
            ArrayList<ChamadaModel> retorno=new ArrayList<>();
            PreparedStatement sql=con.conex.prepareStatement(query);
            ResultSet rs=sql.executeQuery();
            while(rs.next()){
                AulaDao auladao=new AulaDao();
                ArrayList<AulaModel> aula=new ArrayList<>();
                aula=auladao.Buscar("where seq_aula="+rs.getInt("aula"));
                ArrayList<PessoaModel> aluno=new ArrayList<>();
                PessoaDao pessoadao=new PessoaDao();
                aluno=pessoadao.buscar("where seq_pessoa="+rs.getInt("aluno"));
                ChamadaModel chamada=new ChamadaModel(rs.getInt("seq_chamada"), aula.get(0), aluno.get(0), rs.getInt("presente"));
                retorno.add(chamada);
            }
            con.conex.close();
            return retorno;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChamadaDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(ChamadaDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public boolean Inserir(ChamadaModel obj){
        try {
            Conexao con=new Conexao();
            String query="insert into sg_chamada (aula,aluno,presente) values (?,?,?)";
            PreparedStatement sql=con.conex.prepareStatement(query);
            sql.setInt(1, obj.getAula().getSeq_aula());
            sql.setInt(2, obj.getAluno().getSeq_pessoa());
            sql.setInt(3, obj.getPresente());
            return !(sql.execute());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChamadaDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(ChamadaDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean Alterar(ChamadaModel obj){
        try {
            Conexao con=new Conexao();
            String query="update sg_chamada set aula=?,aluno=?,presente=? where seq_chamada="+obj.getSeq_chamada();
            PreparedStatement sql=con.conex.prepareStatement(query);
            sql.setInt(1, obj.getAula().getSeq_aula());
            sql.setInt(2, obj.getAluno().getSeq_pessoa());
            sql.setInt(3, obj.getPresente());
            return !(sql.execute());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChamadaDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(ChamadaDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}