package sv.ues.utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
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
import sv.ues.dao.UsuarioDao;
import sv.ues.dominio.Menu;
import sv.ues.dominio.Rol;
import sv.ues.dominio.Usuario;
import sv.ues.mbsesion.LoginBean;

//@author Miguel
//@WebFilter("*.xhtml")
public class LoginFilter implements Filter 
{
    FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException 
    {
         this.filterConfig= filterConfig;
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException
    {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        HttpSession session = httpRequest.getSession();
        httpRequest.setCharacterEncoding("UTF-8");  
        String requestUrl = httpRequest.getRequestURL().toString();
        
        httpResponse.addHeader("Pragma", "no-cache");
        httpResponse.addHeader("Cache-Control", "no-cache");
        // Stronger according to blog comment below that references HTTP spec
        httpResponse.addHeader("Cache-Control", "no-store");
        httpResponse.addHeader("Cache-Control", "must-revalidate");
        httpResponse.setDateHeader("Expires", 0); // Proxies.
        
        
        
        LoginBean loginBean = (LoginBean)((HttpServletRequest)request).getSession().getAttribute("LoginBean");
        
        if (loginBean == null || loginBean.isLogueado() == false)
        {
            System.out.println("********************************************");
            System.out.println("NO HA INICIADO SESION");
            System.out.println("PETICION: "+requestUrl);
            System.out.println("SERVLET PATH: "+httpRequest.getServletPath());
            System.out.println("CONTEXT PATH: "+httpRequest.getContextPath());
            
            if(requestUrl.trim().endsWith("login.xhtml")||requestUrl.trim().endsWith("cargar.xhtml")||requestUrl.trim().endsWith("/sirdeetv/"))
            {
                System.out.println("SE HA CARGADO LA URL POR DEFECTO, LA URL DE CARGAR O DIRECTAMENTE LOGIN");
                filterChain.doFilter(request, response);
            }
            else
            {
                System.out.println("CUALQUIER OTRA COSA REDIRIGIR A LOGIN");
                httpResponse.sendRedirect(httpRequest.getContextPath()+"/faces/login.xhtml?faces-redirect=true");
            }
        }
        else 
        {
            System.out.println("********************************************");
            System.out.println("HA INICIADO SESION");
            System.out.println("PETICION: "+requestUrl);
            System.out.println("SERVLET PATH: "+httpRequest.getServletPath());
            System.out.println("CONTEXT PATH: "+httpRequest.getContextPath());
            
            //si se cumple lo siguiente, redirigir a index
            if(requestUrl.trim().endsWith("login.xhtml")||requestUrl.trim().endsWith("cargar.xhtml")||requestUrl.trim().endsWith("/sirdeetv/"))
            {
                System.out.println("CONTIENE LOGIN CON SESION");
                httpResponse.sendRedirect(httpRequest.getContextPath()+"/faces/index.xhtml?faces-redirect=true");
            }
            else
            {
                try 
                {
                    UsuarioDao usuarioDao = new UsuarioDao();
                    Usuario usuario = usuarioDao.obtener_usuario_id(loginBean.getUsuario().getIdUsuario());
                    
                    Iterator iterator = usuario.getRols().iterator();
                    List<String> listaPaginas = new ArrayList<String>();
                    listaPaginas.add("index.xhtml");

                    
                    System.out.println("ITERANDO ROLES");
                    while(iterator.hasNext())
                    {
                        Rol element = (Rol) iterator.next();
                        Iterator iterator2 = element.getMenus().iterator();
                        System.out.println("ITERANDO PANTALLAS");
                        while(iterator2.hasNext())
                        {
                            Menu menu = (Menu) iterator2.next();
                            listaPaginas.add(menu.getNomMenu().toLowerCase()+".xhtml");
                            System.out.println(menu.getNomMenu().toLowerCase()+".xhtml");
                        }
                    }
                int flag = 0;
                for(int i = 0;i<listaPaginas.size();i++)
                {
                    if(requestUrl.trim().toLowerCase().endsWith(listaPaginas.get(i)))
                    {
                        i= i + listaPaginas.size()+1;
                        filterChain.doFilter(request, response);
                        
                    }
                }
                if(flag == 0)
                {
                    httpResponse.sendRedirect(httpRequest.getContextPath()+"/faces/index.xhtml?faces-redirect=true");
                }
                } 
                catch (Exception ex) 
                {
                    System.out.println(ex.toString());
                }
            }
        }
    }
    
    public FilterConfig getFilterConfig()
    {
        return filterConfig;
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }

    
}
