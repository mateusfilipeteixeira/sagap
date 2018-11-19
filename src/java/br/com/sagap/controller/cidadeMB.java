package br.com.sagap.controller;

import br.com.sagap.model.CidadeModel;
import br.com.sagap.model.EstadoModel;
import br.com.sagap.dao.CidadeDao;
import br.com.sagap.dao.EstadoDao;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "cidadeMB")
@SessionScoped
public class cidadeMB implements Serializable{
    private CidadeModel cidade;
    private ArrayList<CidadeModel> cidades;
    private EstadoModel estado=new EstadoModel();
    private ArrayList<EstadoModel> estados;
    private ArrayList<CidadeModel> cidadesFiltro;
    private boolean selectCidade=true;
    

    public cidadeMB() {
        CidadeDao cidadedao=new CidadeDao();
        EstadoDao estadodao=new EstadoDao();
        cidade=new CidadeModel();
        cidades=cidadedao.buscar();
        estados=estadodao.buscar();
        cidadesFiltro=new ArrayList<>();
    }

    public CidadeModel getCidade() {
        return cidade;
    }

    public void setCidade(CidadeModel cidade) {
        this.cidade = cidade;
    }

    public ArrayList<CidadeModel> getCidades() {
        return cidades;
    }

    public void setCidades(ArrayList<CidadeModel> cidades) {
        this.cidades = cidades;
    }

    public EstadoModel getEstado() {
        return estado;
    }

    public void setEstado(EstadoModel estado) {
        this.estado = estado;
    }

    public ArrayList<EstadoModel> getEstados() {
        return estados;
    }

    public void setEstados(ArrayList<EstadoModel> estados) {
        this.estados = estados;
    }

    public ArrayList<CidadeModel> getCidadesFiltro() {
        return cidadesFiltro;
    }

    public void setCidadesFiltro(ArrayList<CidadeModel> cidadesFiltro) {
        this.cidadesFiltro = cidadesFiltro;
    }
    
    public void atualizaCidades(){
        if(estado!=null){
            System.out.println("ATUALIZANDO CIDADES2");

            System.out.println(estado.getSeq_estado());
            //System.out.println(estado.getSeq_estado());
            System.out.println(estado.getSigla());
            cidadesFiltro=new ArrayList<>();
            if(cidade.getSeq_cidade()!=null){
                /*for(CidadeModel cid : cidades){
                    if (cid.getEstado()==estado){
                        cidadesFiltro.add(cid);
                    }
                }*/
            }
        }else{
            cidadesFiltro=new ArrayList<>();
        }
        System.out.println("ATUALIZANDO CIDADES");
        System.out.println(cidadesFiltro.size());
    }

    public boolean isSelectCidade() {
        return selectCidade;
    }

    public void setSelectCidade(boolean selectCidade) {
        this.selectCidade = selectCidade;
    }
}
