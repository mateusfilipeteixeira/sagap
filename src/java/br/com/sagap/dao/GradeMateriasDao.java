package br.com.sagap.dao;

import br.com.sagap.model.GradeMateriasModel;
import br.com.sagap.model.PessoaModel;
import br.com.sagap.model.TurmaModel;
import br.com.sagap.util.Conexao;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GradeMateriasDao  implements Serializable{
    
    public boolean inserir(GradeMateriasModel obj){
        String query="insert into sg_grade_materias (nome_materia,turma,professor) values (?,?,?)";
        try {
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            sql.setString(1, obj.getNome_materia());
            sql.setInt(2, obj.getTurma().getSeq_turma());
            sql.setInt(3, obj.getProfessor().getSeq_pessoa());
            boolean retorno=!(sql.execute());
            con.conex.close();
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(GradeMateriasDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GradeMateriasDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public ArrayList buscar(String qry){
        String query="select * from sg_grade_materias "+qry;
        System.out.println(query);
        try {
            Conexao con=new Conexao();
            PreparedStatement sql=con.conex.prepareStatement(query);
            ResultSet rs=sql.executeQuery();
            ArrayList<GradeMateriasModel> retorno=new ArrayList<>();
            TurmaDao turmadao=new TurmaDao();
            PessoaDao pessoadao=new PessoaDao();
            while(rs.next()){
                TurmaModel turma=turmadao.buscar(rs.getInt("turma"));
                ArrayList<PessoaModel> professor=pessoadao.buscar("where seq_pessoa="+rs.getInt("professor")+" limit 1");
                GradeMateriasModel mat=new GradeMateriasModel(rs.getInt("seq_grade_materias"), rs.getString("nome_materia"), turma, rs.getDate("dt_cadastro"), professor.get(0));
                retorno.add(mat);
            }
            rs.close();
            con.conex.close();
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(GradeMateriasDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GradeMateriasDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}