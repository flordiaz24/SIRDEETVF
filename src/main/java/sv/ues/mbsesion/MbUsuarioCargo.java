package sv.ues.mbsesion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;
import sv.ues.dao.CargoDao;
import sv.ues.dao.PersonaDao;
import sv.ues.dao.RolesDao;
import sv.ues.dao.UsuarioDao;
import sv.ues.dominio.Cargo;
import sv.ues.dominio.Persona;
import sv.ues.dominio.Rol;
import sv.ues.dominio.Usuario;

/**
 *
 * @author Miguel
 */
@ManagedBean(name="MbUsuarioCargo")
@ViewScoped
public class MbUsuarioCargo  implements Serializable{
    
    Usuario usuario;
    Usuario usuarioModificar;
    Persona persona;
    Persona personaModificar;
    
    //private List<Cargo>  rolesSeleccionados;
    private Cargo  rolesSeleccionados;
    private List<Cargo> rolesPrevios;
    
    public MbUsuarioCargo()
    {
        usuario = new Usuario();
        persona = new Persona();
        
        personaModificar = new Persona();
        //rolesSeleccionados = new ArrayList<Cargo>();
        rolesSeleccionados = new Cargo();
        rolesPrevios = new ArrayList<Cargo>();
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
 
        if(event.getNewStep().equals("roles") && event.getOldStep().equals("personal"))
        {
            //rolesSeleccionados.add(persona.getUsuario().getCargo());
            rolesSeleccionados = persona.getUsuario().getCargo();
            System.out.println("################# "+rolesSeleccionados.getIdCargo());
            //System.out.println("################# "+rolesSeleccionados.get(0).getCargo());
        }
        /*else
        {
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
        }*/
        return event.getNewStep();
    }
    
    public void asignarRoles() throws Exception
    {
     UsuarioDao usuarioDao = new UsuarioDao();
     usuario = persona.getUsuario();
     
    //PersonaDao personaDao = new PersonaDao();
    //Set setUsuarios = new HashSet(0);
     
    //setUsuarios.addAll(this.rolesPrevios);//Agrega toda la lista de roles al set
     
    //usuario.setRols(setUsuarios);
    usuario.setCargo(rolesSeleccionados);
    
    usuarioDao.modificar(usuario);
     
    usuario = new Usuario();
    //persona = new Persona();
    personaModificar = new Persona();
    //rolesSeleccionados = new ArrayList<Cargo>();
    rolesSeleccionados = new Cargo();
    rolesPrevios = new ArrayList<Cargo>();
    PrimeFaces.current().ajax().update("registros");
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informacion","Cargo asignado con exito"));

    
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
        //rolesSeleccionados.add(persona.getUsuario().getCargo());
        return persona;
    }
    
    public List<Cargo> lista_rol()
    {
        CargoDao rolesDao = new CargoDao();
        try
        {
            return rolesDao.obtenerCargo();
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

    public Cargo getRolesSeleccionados() 
    {
        //System.out.println("CARGO DE PERSONA SELECCIONADA: "+persona.getUsuario().getCargo());
        //rolesSeleccionados.add(persona.getUsuario().getCargo());
        return rolesSeleccionados;
    }

    public void setRolesSeleccionados(Cargo rolesSeleccionados) {
        this.rolesSeleccionados = rolesSeleccionados;
    }

    public List<Cargo> getRolesPrevios() {
        return rolesPrevios;
    }

    public void setRolesPrevios(List<Cargo> rolesPrevios) {
        this.rolesPrevios = rolesPrevios;
    }
    
    
}
