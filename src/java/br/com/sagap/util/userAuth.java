package br.com.sagap.util;

import br.com.sagap.model.PessoaModel;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "userAuth", urlPatterns = {"/paginas/user/*"})
public class userAuth implements Filter {
    
    private static final boolean debug = false;

    private FilterConfig filterConfig = null;
    
    public userAuth() {
    }    
    
    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("loginAuth:DoBeforeProcessing");
        }
    }    
    
    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("loginAuth:DoAfterProcessing");
        }
    }
    
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest) request;
        HttpServletResponse res=(HttpServletResponse) response;
        HttpSession session=req.getSession();
        PessoaModel pessoa=(PessoaModel)session.getAttribute("usuario");
        
        if(pessoa==null){
            res.sendRedirect(req.getContextPath()+"/paginas/seguranca/login.xhtml");
        }else{
            if(pessoa.getTipo()==1){
                res.sendRedirect(req.getContextPath()+"/paginas/adm/index.xhtml");
            }else{
                if(pessoa.getTipo()==2){
                    chain.doFilter(request, response);
                }else{
                    if(pessoa.getTipo()==0){
                        res.sendRedirect(req.getContextPath()+"/paginas/adm/primeirouso/index.xhtml");
                    }else{
                        res.sendRedirect(req.getContextPath()+"/paginas/seguranca/login.xhtml");
                    }
                }
            }
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {        
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {                
                log("loginAuth:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("loginAuth()");
        }
        StringBuffer sb = new StringBuffer("loginAuth(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);        
        
        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);                
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");                
                pw.print(stackTrace);                
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }
    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);        
    }
    
}
