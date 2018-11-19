package br.com.sagap.dao;

import br.com.sagap.model.CidadeModel;
import br.com.sagap.model.PessoaModel;
import br.com.sagap.util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PessoaDao {
    CidadeDao cidadedao=new CidadeDao();
    
    public boolean alterar(PessoaModel obj){
        String query="update sg_pessoa set nome=?,tipo=?,rg=?,cpf=?,dt_nascimento=?,login=?,senha=?,ativo=?,"
                + "logradouro=?,numero=?,bairro=?,complemento=?,cidade=?,telefone=? where seq_pessoa=?";
        try {
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            sql.setString(1, obj.getNome());
            sql.setInt(2, obj.getTipo());
            sql.setString(3, obj.getRg());
            sql.setString(4, obj.getCpf());
            sql.setString(5, obj.getDt_nascimento());
            sql.setString(6, obj.getLogin());
            sql.setString(7, obj.getSenha());
            sql.setInt(8, obj.getAtivo());
            sql.setString(9, obj.getLogradouro());
            sql.setString(10, obj.getNumero());
            sql.setString(11, obj.getBairro());
            sql.setString(12, obj.getComplemento());
            sql.setInt(13, obj.getCidade().getSeq_cidade());
            sql.setString(14, obj.getTelefone());
            sql.setInt(15, obj.getSeq_pessoa());
            return(!sql.execute());
        } catch (SQLException ex) {
            Logger.getLogger(PessoaDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PessoaDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public ArrayList buscar(String qr){
        String query="select * from sg_pessoa "+qr;
        try {
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            ResultSet rs=sql.executeQuery();
            ArrayList<PessoaModel> retorno=new ArrayList<>();
            while(rs.next()){
                CidadeModel cidade=new CidadeModel();
                cidade=cidadedao.buscar(rs.getInt("cidade"));
                PessoaModel pessoa=new PessoaModel(rs.getInt("seq_pessoa"), rs.getString("nome"), rs.getInt("tipo"), rs.getString("rg"), rs.getString("cpf"), rs.getString("dt_nascimento"), rs.getString("login"), rs.getString("senha"), rs.getString("logradouro"), rs.getString("numero"), rs.getString("bairro"), rs.getString("complemento"), cidade, rs.getInt("ativo"),rs.getString("telefone"),rs.getDate("dt_cadastro"));
                retorno.add(pessoa);
            }
            rs.close();
            con.conex.close();
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(PessoaDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PessoaDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }        
    }
    
    public PessoaModel login(String login,String senha){
        String query="select * from sg_pessoa where login=? and senha=?";
        try {
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            sql.setString(1, login);
            sql.setString(2, senha);
            ResultSet rs=sql.executeQuery();
            PessoaModel pessoa=new PessoaModel();
            pessoa.setSeq_pessoa(0);
            while(rs.next()){
                CidadeModel cidade=new CidadeModel();
                cidade=cidadedao.buscar(rs.getInt("cidade"));
                pessoa=new PessoaModel(rs.getInt("seq_pessoa"), rs.getString("nome"), rs.getInt("tipo"), rs.getString("rg"), rs.getString("cpf"), rs.getString("dt_nascimento"), rs.getString("login"), rs.getString("senha"), rs.getString("logradouro"), rs.getString("numero"), rs.getString("bairro"), rs.getString("complemento"), cidade, rs.getInt("ativo"),rs.getString("telefone"),rs.getDate("dt_cadastro"));
            }
            con.conex.close();
            return pessoa;
        } catch (SQLException ex) {
            Logger.getLogger(PessoaDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }catch (Exception e){
            return null;
        }
    }
    
    public boolean salvar(PessoaModel obj){
        String query="insert into sg_pessoa (nome,tipo,rg,cpf,dt_nascimento,ativo,logradouro,numero,bairro,complemento,"
                + "cidade,telefone) "
                + "values (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            sql.setString(1, obj.getNome());
            sql.setInt(2, obj.getTipo());
            sql.setString(3, obj.getRg());
            sql.setString(4, obj.getCpf());
            sql.setString(5, obj.getDt_nascimento());
            sql.setInt(6, obj.getAtivo());
            sql.setString(7, obj.getLogradouro());
            sql.setString(8, obj.getNumero());
            sql.setString(9, obj.getBairro());
            sql.setString(10, obj.getComplemento());
            sql.setInt(11, obj.getCidade().getSeq_cidade());
            sql.setString(12, obj.getTelefone());
            if(!sql.execute()){
                con.conex.close();
                return true;
            }else{
                con.conex.close();
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PessoaDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}