package sv.ues.mbsesion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;
import sv.ues.dao.PreservantesDao;
import sv.ues.dominio.Preservante;

/**
 *
 * @author Miguel
 */
@ManagedBean(name="MbPreservante")
@ViewScoped
public class MbPreservante  implements Serializable{
    
    private Preservante preservante;
    private List<Preservante> lspreservantes=new ArrayList();
    private Preservante selectedPreservante;

    public MbPreservante()
    {
        preservante=new Preservante();
    }
    
    public void registrar() throws Exception
    {
        if(validarCampos() == true)
        {
            PreservantesDao preservantesDao=new PreservantesDao(); 
            preservantesDao.registrar(preservante);
            preservante=new Preservante();
            PrimeFaces.current().ajax().update("preservanteCreacion");
            PrimeFaces.current().ajax().update("preservantesResgistrados");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informacion","Preservante registrado con exito")); 
        }
    }
    
    public void modificar(Preservante preservante)
    {
        selectedPreservante=preservante;
    }
    
    public void modificarDialog() throws Exception
    {
        if(validarCamposModificar() == true)
        {
            PreservantesDao preservantesDao = new PreservantesDao();
            preservantesDao.actualizar_preservante(selectedPreservante);
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('dialogoModificar').hide();");
            PrimeFaces.current().ajax().update("preservantesResgistrados");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informacion","Preservante modificado exitosamente"));
        }
    }
    
    public void setSelected(Preservante preservante)
    {
        selectedPreservante = preservante;
    }
    
    public void eliminarDialog()
    {
       if(selectedPreservante!=null)
       {
            PreservantesDao preservantesDao = new PreservantesDao();
            preservantesDao.eliminar_preservante(selectedPreservante);
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('dialogoEliminar').hide();");
            PrimeFaces.current().ajax().update("preservantesResgistrados");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informacion","Eliminacion exitosa"));
       }
    }
    
    public boolean validarCampos() throws Exception
    {    
        /*EN ESTAS VALIDACIONES SOLO PONER EL TRIM NO FUNCIONA
        ASI QUE SE TRABAJA CON LA LONGITUD*/
        if(preservante.getNomPreservante().trim().length() == 0 || preservante.getDescPreservante().trim().length() == 0)
        {
            if(preservante.getNomPreservante().trim().length() == 0)//VALIDAR QUE SE DIGITE EL NOMBRE DEL PRESERVANTE
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","Debe digitar el nombre"));
            }
            if(preservante.getDescPreservante().trim().length() == 0)//VALIDAR QUE SE DIGITE LA DESCRIPCION DEL PRESERVANTE
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","Debe digitar la descripcion"));
            }
        }
        else
        {
            if(preservante.getNomPreservante().trim().length() < 10 || preservante.getNomPreservante().trim().length() > 25 || preservante.getDescPreservante().trim().length() < 10 || preservante.getDescPreservante().trim().length() > 500)
            {
                if(preservante.getNomPreservante().trim().length() < 10)
                {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","El nombre del preservante debe tener al menos 10 caracteres"));
                }
                if(preservante.getNomPreservante().trim().length() > 25)
                {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","El nombre del preservante debe tener un maximo de 25 caracteres"));
                }
                if(preservante.getDescPreservante().trim().length() < 10)
                {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","La descripcion del preservante debe tener al menos 10 caracteres"));
                }
                if(preservante.getDescPreservante().trim().length() > 500)
                {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","La descripcion del preservante debe tener un maximo de 500 caracteres"));
                }
            }
            else
            {
                PreservantesDao preservantesDao = new PreservantesDao();  
               
                if(preservantesDao.validar_preservante(preservante.getNomPreservante())==true)
                {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","El preservante ya existe")); 
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
                if(selectedPreservante.getNomPreservante().trim().length() == 0 || selectedPreservante.getDescPreservante().trim().length() == 0)
                {
                    if(selectedPreservante.getNomPreservante().trim().length() == 0)//VALIDAR QUE SE DIGITE EL NOMBRE DEL ROL
                    {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","Debe digitar el nombre"));
                    }
                    if(selectedPreservante.getDescPreservante().trim().length() == 0)//VALIDAR QUE SE DIGITE LA DESCRIPCION DEL ROL
                    {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","Debe digitar la descripcion"));
                    }
                }
                else
                {
                    if(selectedPreservante.getNomPreservante().trim().length() < 10 || selectedPreservante.getNomPreservante().trim().length() > 25 || selectedPreservante.getDescPreservante().trim().length() < 10 || selectedPreservante.getDescPreservante().trim().length() > 500)
                    {
                        if(selectedPreservante.getNomPreservante().trim().length() < 10)
                        {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","El nombre del preservante debe tener al menos 10 caracteres"));
                        }
                        if(selectedPreservante.getNomPreservante().trim().length() > 25)
                        {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","El nombre del preservante debe tener un maximo de 25 caracteres"));
                        }
                        if(selectedPreservante.getDescPreservante().trim().length() < 10)
                        {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","La descripcion del preservante debe tener al menos 10 caracteres"));
                        }
                        if(selectedPreservante.getDescPreservante().trim().length() > 500)
                        {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","La descripcion del preservante debe tener un maximo de 500 caracteres"));
                        }
                    }
                    else
                    {
                        PreservantesDao preservantesDao =new PreservantesDao();
                        if(preservantesDao.validar_preservante_modificar(selectedPreservante.getNomPreservante().trim().toLowerCase(),selectedPreservante.getIdPreservante()) == true)
                        {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","El preservante ya existe")); 
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
    
    public List<Preservante> getLsPreservante() 
    {
        return lspreservantes;
    }

    public void setLsPreservante(List<Preservante> lspreservantes) 
    {
        this.lspreservantes = lspreservantes;
    }

    public Preservante getSelectedPreservante() 
    {
        return selectedPreservante;
    }

    public void setSelectedPreservante(Preservante selectedPreservante) 
    {
        this.selectedPreservante = selectedPreservante;
    }
    
    public Preservante getPreservante() 
    {
        return preservante;
    }
    
    public void setPreservante(Preservante preservante) 
    {
        this.preservante= preservante;
    }
    
    public List<Preservante> lista_preservante()
    {
        PreservantesDao preservantesDao =new PreservantesDao();
        try
        {
            return preservantesDao.obtener_preservantes();
        }
        catch(Exception x)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,x.toString(),x.toString()));
            return null;
        }
    }
    
    /**
     * Muestra el nombre del preservante segun ID
     * @param id
     * @return 
     */
    public String nombre_pres_por_ID(Integer id) {
        PreservantesDao pre_dao = new PreservantesDao();
        try {
            return pre_dao.findByPreservanteById(id).getNomPreservante();
        } catch (Exception x) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, x.toString(), x.toString()));
            return null;
        }
    }

 
}
