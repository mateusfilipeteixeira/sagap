package br.com.sagap.controller;

import br.com.sagap.model.PessoaModel;
import br.com.sagap.util.UtilMensagens;
import br.com.sagap.dao.PessoaDao;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


@ManagedBean(name = "loginMB")
@SessionScoped
public class loginMB implements Serializable {

    private String username;
    private String password;
    private PessoaModel usuario=new PessoaModel();
    private String nome="";
    
    public String login() {
        PessoaDao pessoadao=new PessoaDao();
        UtilMensagens message = new UtilMensagens();
        if(usuario.getLogin()!=null && usuario.getSenha()!=null){
            usuario=pessoadao.login(usuario.getLogin(), usuario.getSenha());
            if(usuario.getSeq_pessoa()!=0){
                //usuario=pessoadao.login(usuario.getLogin(), usuario.getSenha());
                HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                session.setAttribute("usuario", usuario);
                if(usuario.getTipo()!=0)
                    message.mensagemSucesso("Seja bem vindo(a) "+usuario.getNome()+"!");
                nome=usuario.getNome();
                if(usuario.getTipo()==1){
                    return "/paginas/adm/index.xhtml?faces-redirect=true";
                }else{
                    if(usuario.getTipo()==2){
                        return "/paginas/user/index.xhtml?faces-redirect=true";
                    }else{
                        if(usuario.getTipo()==0){
                            return "/paginas/adm/primeirouso/index.xhtml?faces-redirect=true";
                        }else{
                            return null;
                        }
                    }
                }
            }else {
                message.mensagemErro("Usuario e/ou senha incorretos!");
                return "/paginas/seguranca/login.xhtml?faces-redirect=true";
            }
        }else{
            message.mensagemErro("Dados de login inválidos!");
            return "/paginas/seguranca/login.xhtml?faces-redirect=true";
        }
    }
    
    public String sair(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/paginas/seguranca/login.xhtml?faces-redirect=true";
    }
    
    public loginMB() {
    }

    public loginMB(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public PessoaModel getUsuario() {
        return usuario;
    }

    public void setUsuario(PessoaModel usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
}
