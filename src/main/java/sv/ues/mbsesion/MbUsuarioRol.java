package sv.ues.mbsesion;

import java.io.Serializable;
//import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
//import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;
import sv.ues.dao.PersonaDao;
import sv.ues.dao.RolesDao;
import sv.ues.dao.UsuarioDao;
import sv.ues.dominio.Persona;
import sv.ues.dominio.Rol;
import sv.ues.dominio.Usuario;

/**
 *
 * @author Miguel
 */
@ManagedBean(name="MbUsuarioRol")
@ViewScoped
public class MbUsuarioRol  implements Serializable{
    
    Usuario usuario;
    Usuario usuarioModificar;
    Persona persona;
    Persona personaModificar;
    
    private List<Rol> rolesSeleccionados;
    private List<Rol> rolesPrevios;
    
    public MbUsuarioRol()
    {
        usuario = new Usuario();
        persona = new Persona();
        
        personaModificar = new Persona();
        rolesSeleccionados = new ArrayList<Rol>();
        rolesPrevios = new ArrayList<Rol>();
    }
    
    public List<Persona> lista_personas() throws Exception
    {
        
        PersonaDao personaDao = new PersonaDao();
        List<Persona> lista = personaDao.obtener_personas();
        persona = lista.get(0);
        try
        {
            return personaDao.obtener_personas();
        }
        catch(Exception x)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,x.toString(),x.toString()));
            return null;
        }
    }
    
    //Este metodo controla el flujo del wizard
    public String onFlowProcess(FlowEvent event) {
               
        //SI SE QUIERE PASAR A LA PESTANA DE CONFIRMAR PERO NO SE HA SELECCIONADO NINGUN ROL
        //QUEDARSE EN LA PESTANA DE ROLES
        if(event.getNewStep().equals("confirmar") && this.rolesSeleccionados.isEmpty())
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"","Debe seleccionar almenos un rol"));
            PrimeFaces.current().ajax().update("registros:mensaje");
            return "roles";
        }
        else
        {
            //SI SE QUIERE PASAR A CONFIRMAR, ASIGNAR A LA VARIABLE ROLESPREVIOS LOS ROLES SELECCIONADOS
            //SINO SE HACE ESTO, LA VARIABLE ROLESSELECCIONADOS SE RESETEA
            if(event.getNewStep().equals("confirmar") && event.getOldStep().equals("roles"))
            {
                rolesPrevios = rolesSeleccionados;
                personaModificar = persona;
                
            }
            else
            {
                //SI SE REGRESA DE LA PESTANA CONFIRMAR A ROLES
                //SETEAR A ROLES SELECCIONADOS LA VARIABLE ROLES PREVIOS
                //SINO SE HACE ESTO, LA VARIABLE ROLESSELECCIONADOS SE RESETEA
                if(event.getNewStep().equals("roles") && event.getOldStep().equals("confirmar"))
                {
                    rolesSeleccionados = rolesPrevios;
                    persona = personaModificar;
                }
            }
            return event.getNewStep();
        }
    }
    
    public void asignarRoles() throws Exception
    {
     UsuarioDao usuarioDao = new UsuarioDao();
     usuario = persona.getUsuario();
     
    //PersonaDao personaDao = new PersonaDao();
    Set setUsuarios = new HashSet(0);
     
    setUsuarios.addAll(this.rolesPrevios);//Agrega toda la lista de roles al set
     
    usuario.setRols(setUsuarios);
    
    usuarioDao.modificar(usuario);
     
    usuario = new Usuario();
    //persona = new Persona();
    personaModificar = new Persona();
    rolesSeleccionados = new ArrayList<Rol>();
    rolesPrevios = new ArrayList<Rol>();
    PrimeFaces.current().ajax().update("registros");
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informacion","Roles asignados con exito"));

    
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Persona getPersona() {
        
        List<Rol> rolesSeteados = new ArrayList<Rol>();
        persona.getUsuario().getRols();
        rolesSeleccionados = new ArrayList<Rol>(persona.getUsuario().getRols());
        
        return persona;
    }
    
    public List<Rol> lista_rol()
    {
        RolesDao rolesDao = new RolesDao();
        try
        {
            return rolesDao.obtener_roles();
        }
        catch(Exception x)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,x.toString(),x.toString()));
            return null;
        }
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public List<Rol> getRolesSeleccionados() {
        rolesSeleccionados = new ArrayList<Rol>(persona.getUsuario().getRols());
        return rolesSeleccionados;
    }

    public void setRolesSeleccionados(List<Rol> rolesSeleccionados) {
        this.rolesSeleccionados = rolesSeleccionados;
    }

    public List<Rol> getRolesPrevios() {
        return rolesPrevios;
    }

    public void setRolesPrevios(List<Rol> rolesPrevios) {
        this.rolesPrevios = rolesPrevios;
    }
    
    
}
