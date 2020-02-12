package sv.ues.mbsesion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;
import sv.ues.dao.MenuDao;
import sv.ues.dao.RolesDao;
import sv.ues.dominio.Menu;
import sv.ues.dominio.Rol;

/**
 *
 * @author Miguel
 */
@ManagedBean(name="MbMenu")
@ViewScoped
public class MbMenu  implements Serializable{
    
    private Menu roles;
    private List<Menu> lsroles=new ArrayList();
    private Menu selectedRol;

    public MbMenu()
    {
        roles=new Menu();
        //roles.setEstadoRol(true);
    }
    
    //Este metodo controla el flujo del wizard de registro
    public String flujoResgistrar(FlowEvent event) throws Exception 
    {
        if(event.getOldStep().equals("general") && event.getNewStep().equals("confirmar"))
        {
            MenuDao rolesDao=new MenuDao();  
            if(rolesDao.validar_menu(roles.getNomMenu())==true)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","El menu ya existe")); 
                return event.getOldStep();
            }
            else
            {
                return event.getNewStep();
            }
        }
        return event.getNewStep();
    }
    
    //Este metodo controla el flujo del wizard de registro
    public String flujoModificar(FlowEvent event) throws Exception 
    {
        if(event.getOldStep().equals("general") && event.getNewStep().equals("confirmar"))
        {
            MenuDao rolesDao=new MenuDao();  
            if(rolesDao.validar_menu_modificar(selectedRol.getNomMenu(),selectedRol.getIdMenu())==true)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","El menu ya existe")); 
                return event.getOldStep();
            }
            else
            {
                return event.getNewStep();
            }
        }
        return event.getNewStep();
    }
    
    public void registrar() throws Exception
    {
        MenuDao rolesDao=new MenuDao();
        rolesDao.registrar(roles);
        roles = new Menu();
        //roles.setEstadoRol(true);
        PrimeFaces.current().ajax().update("F01:registro");
        PrimeFaces.current().ajax().update("F01");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informacion","Menu registrado con exito")); 
    }

    public void modificar(Menu rol)
    {
        selectedRol=rol;
    }
    
    public void modificarDialog() throws Exception
    {   MenuDao rolesDao=new MenuDao();
        rolesDao.actualizar_menu(selectedRol);
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dialogoModificar').hide();");
        PrimeFaces.current().ajax().update("registros");
        selectedRol = new Menu();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informacion","Menu modificado exitosamente"));
        
    }
    
    public void setSelected(Menu roles)
    {
        selectedRol=roles;
    }
    
    /*public void eliminarDialog()
    {
       if(selectedRol!=null)
       {
            MenuDao rolesDao=new MenuDao();
            rolesDao.eliminar_rol(selectedRol);
            selectedRol = new Menu();
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('dialogoEliminar').hide();");
            PrimeFaces.current().ajax().update("registros");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informacion","Eliminacion exitosa"));
       }
    }*/
    
    public boolean validarCampos() throws Exception
    {    
        /*EN ESTAS VALIDACIONES SOLO PONER EL TRIM NO FUNCIONA
        ASI QUE SE TRABAJA CON LA LONGITUD*/
        if(roles.getNomMenu().trim().length() == 0 /*|| roles.getDescripcion().trim().length() == 0*/)
        {
            if(roles.getNomMenu().trim().length() == 0)//VALIDAR QUE SE DIGITE EL NOMBRE DEL ROL
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","Debe digitar el nombre"));
                PrimeFaces current = PrimeFaces.current();
                current.executeScript("PF('bui').hide()");
            }
            /*if(roles.getDescripcion().trim().length() == 0)//VALIDAR QUE SE DIGITE LA DESCRIPCION DEL ROL
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","Debe digitar la descripcion"));
                PrimeFaces current = PrimeFaces.current();
                current.executeScript("PF('bui').hide()");
            }*/
        }
        else
        {
            if(roles.getNomMenu().trim().length() < 10 || roles.getNomMenu().trim().length() > 25 /*|| roles.getDescripcion().trim().length() < 10 || roles.getDescripcion().trim().length() > 500*/)
            {
                if(roles.getNomMenu().trim().length() < 10)
                {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","El nombre del menu debe tener al menos 10 caracteres"));
                    PrimeFaces current = PrimeFaces.current();
                    current.executeScript("PF('bui').hide()");
                }
                if(roles.getNomMenu().trim().length() > 25)
                {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","El nombre del menu debe tener un maximo de 25 caracteres"));
                    PrimeFaces current = PrimeFaces.current();
                    current.executeScript("PF('bui').hide()");
                }
                /*if(roles.getDescripcion().trim().length() < 10)
                {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","La descripcion del rol debe tener al menos 10 caracteres"));
                    PrimeFaces current = PrimeFaces.current();
                    current.executeScript("PF('bui').hide()");
                }
                if(roles.getDescripcion().trim().length() > 500)
                {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","La descripcion del rol debe tener un maximo de 500 caracteres"));
                    PrimeFaces current = PrimeFaces.current();
                    current.executeScript("PF('bui').hide()");
                }*/
            }
            else
            {
                MenuDao rolesDao=new MenuDao();  
               
                if(rolesDao.validar_menu(roles.getNomMenu())==true)
                {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","El menu ya existe")); 
                    PrimeFaces current = PrimeFaces.current();
                    current.executeScript("PF('bui').hide()");
                    return false;
                }
                else
                {
                    return true;
                }
            }
        }
    return false;
    }
    
        public boolean validarCamposModificar() throws Exception
    {   
                if(selectedRol.getNomMenu().trim().length() == 0 /*|| selectedRol.getDescripcion().trim().length() == 0*/)
                {
                    if(selectedRol.getNomMenu().trim().length() == 0)//VALIDAR QUE SE DIGITE EL NOMBRE DEL ROL
                    {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","Debe digitar el nombre"));
                    }
                    /*if(selectedRol.getDescripcion().trim().length() == 0)//VALIDAR QUE SE DIGITE LA DESCRIPCION DEL ROL
                    {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","Debe digitar la descripcion"));
                    }*/
                }
                else
                {
                    if(selectedRol.getNomMenu().trim().length() < 10 || selectedRol.getNomMenu().trim().length() > 25 /*|| selectedRol.getDescripcion().trim().length() < 10 || selectedRol.getDescripcion().trim().length() > 500*/)
                    {
                        if(selectedRol.getNomMenu().trim().length() < 10)
                        {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","El nombre del menu debe tener al menos 10 caracteres"));
                        }
                        if(selectedRol.getNomMenu().trim().length() > 25)
                        {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","El nombre del menu debe tener un maximo de 25 caracteres"));
                        }
                        /*if(selectedRol.getDescripcion().trim().length() < 10)
                        {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","La descripcion del rol debe tener al menos 10 caracteres"));
                        }
                        if(selectedRol. getDescripcion().trim().length() > 500)
                        {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","La descripcion del rol debe tener un maximo de 500 caracteres"));
                        }*/
                    }
                    else
                    {
                        MenuDao rolesDao=new MenuDao();
                        System.out.println(selectedRol.getNomMenu().trim().toLowerCase() + "   " + selectedRol.getIdMenu());
                        if(rolesDao.validar_menu_modificar(selectedRol.getNomMenu().trim().toLowerCase(),selectedRol.getIdMenu()) == true)
                        {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","El menu ya existe")); 
                            return false;
                        }
                        else
                        {
                            return true;
                        }
                    }
                }
            
        return false;
    }
    
    public List<Menu> getLsroles() 
    {
        return lsroles;
    }

    public void setLsroles(List<Menu> lsroles) 
    {
        this.lsroles = lsroles;
    }

    public Menu getSelectedRol() 
    {
        return selectedRol;
    }

    public void setSelectedRol(Menu selectedRol) 
    {
        this.selectedRol = selectedRol;
    }
    
    public Menu getRoles() 
    {
        return roles;
    }
    
    public void setRoles(Menu roles) 
    {
        this.roles = roles;
    }
    
    public List<Menu> lista_rol()
    {
        MenuDao rolesDao = new MenuDao();
        try
        {
            return rolesDao.obtener_menus();
        }
        catch(Exception x)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,x.toString(),x.toString()));
            return null;
        }
    }
}
