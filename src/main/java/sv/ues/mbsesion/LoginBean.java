/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.mbsesion;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;
import org.springframework.security.crypto.bcrypt.BCrypt;
import sv.ues.dao.PersonaDao;
import sv.ues.dao.UsuarioDao;
import sv.ues.dominio.Persona;
import sv.ues.dominio.Usuario;

/**
 *
 * @author Miguel
 */
@ManagedBean(name="LoginBean")
@SessionScoped
public class LoginBean implements Serializable 
{
    
    //private String username;
    //private String password;
    
    private Usuario usuario;
    private String nombre;
    private String contrasenia;
    private boolean logueado;
    Persona personaPassword = new Persona();
    PersonaDao personaDao = new PersonaDao();

    public LoginBean() 
    {
        HttpSession miSession=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        miSession.setMaxInactiveInterval(1800);//Media hora
        this.nombre = null;
        this.contrasenia = null;
        this.usuario = null;
        this.logueado = false;
    }
    
    
    
    public String iniciar_sesion()
    {
        try
        {
            UsuarioDao validar = new UsuarioDao();
            this.usuario = validar.obtener_usuario_nombre(this.nombre);

            if(this.usuario!=null)
            {
               if(BCrypt.checkpw(this.contrasenia, this.usuario.getClave())==true)
               {
                    //RequestContext.getCurrentInstance().execute("limpiar('formulario_login')"); 
                   this.nombre = "";
                   this.contrasenia = "";
                   this.logueado = true;
                   personaPassword = personaDao.obtener_persona_usuario(usuario);
                   //System.out.println("PERSONA: " + personaPassword.getCorreoPersona());
                   //PrimeFaces.current().ajax().update("F01");
                   return "/index.xhtml?faces-redirect=true";
                   //return "index?faces-redirect=true";
                    //HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                    //httpSession.setAttribute("nombre", this.nombre);
                    //httpSession.setAttribute("tipo", this.usuario.getTipo());
                    
               }
                System.out.println("ingresa 2");
                System.out.println(this.usuario.getClave());
                System.out.println(BCrypt.checkpw(this.contrasenia, this.usuario.getClave()));
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Acceso denegado"));
            this.nombre = null;
            this.contrasenia = null;
            this.usuario = null;
            this.logueado = false;
            //RequestContext.getCurrentInstance().execute("limpiar('formulario_login')");
            //System.out.println("METIDO");
            return "/login.xhtml?faces-redirect=true";
        }
        catch(Exception x)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error:\n"+x.toString()));
            return null; 
        }   
    }
    
    public String cerrar_sesion()
    {
        /*HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        httpSession.invalidate();*/
        this.usuario = null;
        this.contrasenia = null;
        this.nombre = null;
        this.logueado = false;
        //this.tipo = 0;
        //this.administrativo = new Administrativo();
        System.out.println("METIDO A CERRAR SESION");
        return "/login.xhtml?faces-redirect=true";
    }

    public String cambiarPasswordUSR()
    {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = request.getRequestURL().toString();
        System.out.println(url);
        if(url.endsWith("index.xhtml"))
        {
            return "usuario/credencialesusr.xhtml";
        }
        else
        {
            return  "../usuario/credencialesusr.xhtml";
        }
    }
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public boolean isLogueado() {
        return logueado;
    }

    public void setLogueado(boolean logueado) {
        this.logueado = logueado;
    }

    public Persona getPersonaPassword() {
        return personaPassword;
    }

    public void setPersonaPassword(Persona personaPassword) {
        this.personaPassword = personaPassword;
    }

    public PersonaDao getPersonaDao() {
        return personaDao;
    }

    public void setPersonaDao(PersonaDao personaDao) {
        this.personaDao = personaDao;
    }
    
  
}
