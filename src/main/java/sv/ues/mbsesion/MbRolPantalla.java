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
import sv.ues.dao.MenuDao;
import sv.ues.dao.PersonaDao;
import sv.ues.dao.RolesDao;
import sv.ues.dao.UsuarioDao;
import sv.ues.dominio.Menu;
import sv.ues.dominio.Persona;
import sv.ues.dominio.Rol;
import sv.ues.dominio.Usuario;

/**
 *
 * @author Miguel
 */
@ManagedBean(name="MbRolPantalla")
@ViewScoped
public class MbRolPantalla  implements Serializable{
    
    Rol rol;
    Rol rolModificar;
    
    
    Usuario usuario;
    Usuario usuarioModificar;
    private List<Menu> rolesSeleccionados;
    private List<Menu> rolesPrevios;
    
    public MbRolPantalla()
    {
        rol = new Rol();
        rolModificar = new Rol();
        
        usuario = new Usuario();
        rolesSeleccionados = new ArrayList<Menu>();
        rolesPrevios = new ArrayList<Menu>();
    }
    
    public List<Rol> lista_roles() throws Exception
    {
        RolesDao personaDao = new RolesDao();
        List<Rol> lista = personaDao.obtener_roles();
        rol = lista.get(0);
        try
        {
            return personaDao.obtener_roles();
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
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"","Debe seleccionar almenos una pantalla"));
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
                //rolModificar = rol;
                
            }
            else
            {
                //SI SE REGRESA DE LA PESTANA CONFIRMAR A ROLES
                //SETEAR A ROLES SELECCIONADOS LA VARIABLE ROLES PREVIOS
                //SINO SE HACE ESTO, LA VARIABLE ROLESSELECCIONADOS SE RESETEA
                if(event.getNewStep().equals("roles") && event.getOldStep().equals("confirmar"))
                {
                    //rol = rolModificar;
                    rolesSeleccionados = rolesPrevios;
                    
                }
            }
            rolesSeleccionados = new ArrayList<Menu>(rol.getMenus());
            //System.out.println("ROLES PREVIOS: "+rolesPrevios.size());
            //System.out.println("ROLES SELECCIONADOS: "+rolesSeleccionados.size());
            return event.getNewStep();
        }
    }
    
    public void asignarRoles() throws Exception
    {
     RolesDao usuarioDao = new RolesDao();
     //usuario = persona.getUsuario(); MODIFICADO
     
        //PersonaDao personaDao = new PersonaDao();
        Set setMenus = new HashSet(0);

        setMenus.addAll(this.rolesPrevios);//Agrega toda la lista de roles al set

        //usuario.setRols(setUsuarios);
        rol.setMenus(setMenus);

        usuarioDao.actualizar_rol(rol);

        rol = new Rol();
        //persona = new Persona();
        //personaModificar = new Persona(); MODIFICADO
        rolesSeleccionados = new ArrayList<Menu>();
        rolesPrevios = new ArrayList<Menu>();
        PrimeFaces.current().ajax().update("registros");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informacion","Pantallas asignadas con exito"));

    
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Persona getPersona() {
        
        List<Rol> rolesSeteados = new ArrayList<Rol>();
        //persona.getUsuario().getRols(); MODIFICADO
        //rolesSeleccionados = new ArrayList<Rol>(persona.getUsuario().getRols()); MODIFICADO
        
        //return persona; MODIFICADO
        return null;
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
    
    
    public List<Menu> lista_menu()
    {
        MenuDao rolesDao = new MenuDao();
        try
        {
            //rolesSeleccionados = new ArrayList<Menu>(rol.getMenus());
            return rolesDao.obtener_menus();
        }
        catch(Exception x)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,x.toString(),x.toString()));
            return null;
        }
    }

    public void setPersona(Persona persona) {
        //this.persona = persona; MODIFICADO
    }

    public List<Menu> getRolesSeleccionados() {
        //rolesSeleccionados = new ArrayList<Menu>(rol.getMenus());
        return rolesSeleccionados;
    }

    public void setRolesSeleccionados(List<Menu> rolesSeleccionados) {
        this.rolesSeleccionados = rolesSeleccionados;
    }

    public List<Menu> getRolesPrevios() {
        return rolesPrevios;
    }

    public void setRolesPrevios(List<Menu> rolesPrevios) {
        this.rolesPrevios = rolesPrevios;
    }

    public Rol getRol() {
        //persona.getUsuario().getRols(); MODIFICADO
        
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Rol getRolModificar() {
        return rolModificar;
    }

    public void setRolModificar(Rol rolModificar) {
        this.rolModificar = rolModificar;
    }

    public Usuario getUsuarioModificar() {
        return usuarioModificar;
    }

    public void setUsuarioModificar(Usuario usuarioModificar) {
        this.usuarioModificar = usuarioModificar;
    }
    
    
}
