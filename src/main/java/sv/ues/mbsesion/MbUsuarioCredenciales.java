package sv.ues.mbsesion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;
import org.springframework.security.crypto.bcrypt.BCrypt;
import sv.ues.dao.CargoDao;
import sv.ues.dao.PersonaDao;
import sv.ues.dao.RolesDao;
import sv.ues.dao.UsuarioDao;
import sv.ues.dominio.Cargo;
import sv.ues.dominio.Persona;
import sv.ues.dominio.Rol;
import sv.ues.dominio.Usuario;
import static sv.ues.mbsesion.MbPersona.generarPassword;
import sv.ues.utils.EnvioCorreos;

/**
 *
 * @author Miguel
 */
@ManagedBean(name="MbUsuarioCredenciales")
@ViewScoped
public class MbUsuarioCredenciales  implements Serializable{
    
    Usuario usuario;
    Usuario usuarioModificar;
    Persona persona;
    Persona personaModificar;
    
    //private List<Cargo>  rolesSeleccionados;
    private Cargo  rolesSeleccionados;
    private List<Cargo> rolesPrevios;
    private Date fecha;
    String clave;
    
    public MbUsuarioCredenciales()
    {
        usuario = new Usuario();
        persona = new Persona();
        
        personaModificar = new Persona();
        //rolesSeleccionados = new ArrayList<Cargo>();
        rolesSeleccionados = new Cargo();
        rolesPrevios = new ArrayList<Cargo>();
    }
    
    public void resetPasswordSeleccion(Persona persona)
    {
        personaModificar = persona;
        usuarioModificar= persona.getUsuario();
    }
    
    private String contrasenaVieja;
    private String contrasenaNueva;
    private String contrasenaNuevaConfirmar;
    
    
    public void modificarPasswordPersonal() throws Exception
    {
        UsuarioDao usuarioDao = new UsuarioDao();
        Usuario usuarioPassword = new Usuario();
        
        //PersonaDao personaDao = new PersonaDao();
        //Persona personaPassword = new Persona();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        LoginBean neededBean = (LoginBean)facesContext.getApplication() .createValueBinding("#{LoginBean}").getValue(facesContext);
        
        usuarioPassword = usuarioDao.obtener_usuario_id(neededBean.getUsuario().getIdUsuario());
        
        System.out.println("USUARIO: " + usuarioPassword.getNomUsuario());
        System.out.println("PERSONA: " + neededBean.getPersonaPassword().getCorreoPersona());
        
        if(!contrasenaNueva.equals(contrasenaNuevaConfirmar))
        {
            PrimeFaces.current().ajax().update("registros:mensaje");
            PrimeFaces.current().executeScript("PF('dialogoModificar').hide();");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"","Los password no coinciden"));
        }
        else
        {
            //"fg-pTzFT"
            if(BCrypt.checkpw(contrasenaVieja, usuarioPassword.getClave()))
            {
                //System.out.println("COINCIDE");
                usuarioPassword.setClave(BCrypt.hashpw(contrasenaNuevaConfirmar, BCrypt.gensalt()));
                usuarioPassword.setFechaUltimaModificacion(new Date());
                usuarioDao.modificar(usuarioPassword);
                
                EnvioCorreos correos = new EnvioCorreos(neededBean.getPersonaPassword().getCorreoPersona(), "Nuevo Password SIRDEETV", asunto_email_(usuarioPassword.getNomUsuario(),contrasenaNuevaConfirmar));
                correos.start();
                
                contrasenaNueva = "";
                contrasenaNuevaConfirmar = "";
                contrasenaVieja = "";
                PrimeFaces.current().ajax().update("registros");
                PrimeFaces.current().executeScript("PF('dialogoModificar').hide();");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"","Password modificado con exito"));
            }
            else
            {
                PrimeFaces.current().ajax().update("registros:mensaje");
                PrimeFaces.current().executeScript("PF('dialogoModificar').hide();");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"","Password actual incorrecto"));
            }
        }
    }

    public void dialogos()
    {
        //PrimeFaces.current().executeScript("PF('bui').show();");
        PrimeFaces.current().executeScript("PF('dialogoModificar').show();");
    }
    
    
    public String getContrasenaVieja() {
        return contrasenaVieja;
    }

    public void setContrasenaVieja(String contrasenaVieja) {
        this.contrasenaVieja = contrasenaVieja;
    }

    public String getContrasenaNueva() {
        return contrasenaNueva;
    }

    public void setContrasenaNueva(String contrasenaNueva) {
        this.contrasenaNueva = contrasenaNueva;
    }

    public String getContrasenaNuevaConfirmar() {
        return contrasenaNuevaConfirmar;
    }

    public void setContrasenaNuevaConfirmar(String contrasenaNuevaConfirmar) {
        this.contrasenaNuevaConfirmar = contrasenaNuevaConfirmar;
    }
    
    
    
    
    public void modificarPassword() throws Exception
    {
    
            this.clave = generarPassword(8);
            this.usuarioModificar.setClave(BCrypt.hashpw(this.clave, BCrypt.gensalt()));
            this.usuarioModificar.setFechaUltimaModificacion(fecha);
            
            UsuarioDao usuarioDao = new UsuarioDao();
            usuarioDao.modificar(usuarioModificar);
            EnvioCorreos correos = new EnvioCorreos(this.personaModificar.getCorreoPersona(), "Password Modificado SIRDEETV", asunto_email(this.usuarioModificar.getNomUsuario(),this.clave,this.personaModificar.getPrimerNombre().substring(0, 1).toUpperCase() + this.personaModificar.getPrimerNombre().substring(1).toLowerCase(),this.personaModificar.getPrimerApellido().substring(0, 1).toUpperCase() + this.personaModificar.getPrimerApellido().substring(1).toLowerCase()));
            
            correos.start();
            
            PrimeFaces.current().ajax().update("registros");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informacion","Password generado con exito")); 
            PrimeFaces.current().executeScript("PF('dialogoModificar').hide();");
    }
    
    private String asunto_email(String user, String password, String nombre, String apellido)
 {
     return "<h1>SIRDEETV</h1>\n" +
            "\n" +
            "<p>"+nombre+" " +apellido+", Nuevas credenciales generadas</p>\n" +
            "\n" +
            "<h4>Credenciales</h4>\n" +
            "<spam>Usuario: <strong>"+user+"</strong></spam><br/>\n" +
            "<spam>Contrase�a: <strong>"+password+"</strong></spam>\n" +
            "\n" +
            "<br/><br/>\n" +
            "\n" +
            "<spam>- Se recomienda ingresar a SIRDEETV y cambiar la contrase�a generada por defecto.</spam><br/>\n" +
            "<spam>- Si en alg�n momento olvida sus credenciales, contacte con el administrador de SIRDEETV.</spam>";
 }
    
    private String asunto_email_(String user, String password)
 {
     return "<h1>SIRDEETV</h1>\n" +
            "\n" +
            "<p>Password modificado</p>\n" +
            "\n" +
            "<h4>Credenciales</h4>\n" +
            "<spam>Usuario: <strong>"+user+"</strong></spam><br/>\n" +
            "<spam>Contrase�a: <strong>"+password+"</strong></spam>\n" +
            "\n" +
            "<br/><br/>\n" +
            "\n" +
            "<spam>- Si en alg�n momento olvida sus credenciales, contacte con el administrador de SIRDEETV.</spam>";
 }
    
    static String generarPassword(int n) 
    { 
        String AlphaNumericString = "ABCDEFGHJKMNPRSTUVWXYZ2356789abcdefghjkmnprstuvxyz$#%*+-&.-_"; 

        StringBuilder sb = new StringBuilder(n); 
  
        for (int i = 0; i < n; i++)
        { 

            int index = (int)(AlphaNumericString.length()* Math.random()); 
  
            sb.append(AlphaNumericString.charAt(index)); 
        } 
        return sb.toString(); 
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
