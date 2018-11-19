package br.com.sagap.dao;

import br.com.sagap.model.InstituicaoModel;
import br.com.sagap.util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InstituicaoDao {
    
    public boolean inserir(InstituicaoModel obj){
        String query="INSERT INTO `sagap`.`sg_instituicao` (nome,telefone,cnpj,logradouro,numero,bairro,complemento,cidade) "
                + "VALUES (?,?,?,?,?,?,?,?);";
        try {
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            sql.setString(1, obj.getNome());
            sql.setString(2, obj.getTelefone());
            sql.setString(3, obj.getCnpj());
            sql.setString(4, obj.getLogradouro());
            sql.setString(5, obj.getNumero());
            sql.setString(6, obj.getBairro());
            sql.setString(7, obj.getComplemento());
            sql.setInt(8, obj.getCidade().getSeq_cidade());
            return(! sql.execute());
        } catch (SQLException ex) {
            Logger.getLogger(InstituicaoDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
    public ArrayList Buscar(){
        String query="select * from sg_instituicao";
        try {
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            ResultSet rs=sql.executeQuery();
            ArrayList<InstituicaoModel> retorno=new ArrayList<>();
            while(rs.next()){
                InstituicaoModel inst=new InstituicaoModel();
                inst.setBairro(rs.getString("bairro"));
                CidadeDao cidadedao=new CidadeDao();
                inst.setCidade(cidadedao.buscar(rs.getInt("cidade")));
                inst.setCnpj(rs.getString("cnpj"));
                inst.setComplemento(rs.getString("complemento"));
                inst.setLogradouro(rs.getString("logradouro"));
                inst.setNome(rs.getString("nome"));
                inst.setNumero(rs.getString("numero"));
                inst.setSeq_instituicao(rs.getInt("seq_instituicao"));
                inst.setTelefone(rs.getString("telefone"));
                retorno.add(inst);
            }
            rs.close();
            con.conex.close();
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(InstituicaoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InstituicaoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public InstituicaoModel Buscar(int id){
        String query="select * from sg_instituicao where seq_instituicao="+id;
        try {
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            ResultSet rs=sql.executeQuery();
            InstituicaoModel inst=new InstituicaoModel();
            while(rs.next()){
                inst.setBairro(rs.getString("bairro"));
                CidadeDao cidadedao=new CidadeDao();
                inst.setCidade(cidadedao.buscar(rs.getInt("cidade")));
                inst.setCnpj(rs.getString("cnpj"));
                inst.setComplemento(rs.getString("complemento"));
                inst.setLogradouro(rs.getString("logradouro"));
                inst.setNome(rs.getString("nome"));
                inst.setNumero(rs.getString("numero"));
                inst.setSeq_instituicao(rs.getInt("seq_instituicao"));
                inst.setTelefone(rs.getString("telefone"));
            }
            rs.close();
            con.conex.close();
            return inst;
        } catch (SQLException ex) {
            Logger.getLogger(InstituicaoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InstituicaoDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}