package sv.ues.mbsesion;

/*import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;
import sv.ues.dao.RolesDao;
import sv.ues.dominio.Cargo;
import sv.ues.dominio.Rol;*/

import java.io.Serializable;
import java.text.ParseException;
/*import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;*/
import java.util.Date;
import java.util.List;
//import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;
import sv.ues.dao.CargoDao;
//import sv.ues.dao.PersonaDao;
//import sv.ues.dao.RolesDao;
import sv.ues.dominio.Cargo;
//import sv.ues.dominio.Rol;

//@author Miguel

@ManagedBean(name = "MbCargo")
@ViewScoped
public class MbCargo implements Serializable 
{

    private Cargo cargo;
    private Cargo selectedCargo;
    private CargoDao cargoDao;
    private List<Cargo> lscargo;
    
    private Date fecha;
    
    public MbCargo() 
    {
        this.cargo = new Cargo();
        this.selectedCargo = new Cargo();
        this.cargoDao = new CargoDao();
        this.fecha = new Date();
    }
    
    public void registrar()
    {
        try
        {
            this.cargo.setFechaCreacion(this.fecha);
            this.cargo.setFechaModifica(this.fecha);
            this.cargo.setActivo(true);
            this.cargoDao.registrar(this.cargo);
            this.cargo = new Cargo();
            PrimeFaces.current().ajax().update("F01");    
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Registro de cargo exitoso"));
        }
        catch(Exception x)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"",x.toString())); 
        }
    }
    
    public void modificarDialog()
    {

        try
        {
            this.cargoDao.actualizarCargo(this.selectedCargo);
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('dialogoModificar').hide();");
            PrimeFaces.current().ajax().update("F01");
            this.selectedCargo = new Cargo();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Cargo modificado exitosamente"));
        }
        catch(Exception x)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"",x.toString())); 
        }
    }
    
    
    
    public Cargo getSelectedCargo() {
        return selectedCargo;
    }

    public void setSelectedCargo(Cargo selectedCargo) {
        this.selectedCargo = selectedCargo;
    }

    public List<Cargo> getLscargo() {
        return lscargo;
    }

    public void setLscargo(List<Cargo> lscargo) {
        this.lscargo = lscargo;
    }

    public void selectCargo(Cargo cargo) {
        this.selectedCargo = cargo;
    }

    public void modificar(Cargo cargo) {
        selectedCargo = cargo;
    }

    

    

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
 

    public List<Cargo> listCargos() throws Exception {
        return cargoDao.obtenerCargo();
    }

    
        
    //Este metodo controla el flujo del wizard de registro
    public String flujoResgistrar(FlowEvent event) throws Exception 
    {
        String cadena_validadora = "";
        if(event.getOldStep().equals("general") && event.getNewStep().equals("confirmar"))
        {
            if(this.cargoDao.validarExistCargo(this.cargo.getCargo().trim()) != null)
            {
                cadena_validadora = cadena_validadora + "A";
            }
        }
        
        if(cadena_validadora.length() > 0)
        {
            if(cadena_validadora.contains("A"))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","El cargo " + this.cargo.getCargo() + " ya existe")); 
            }
            return event.getOldStep();
        }
        else
        {
            return event.getNewStep();
        }
    }
    
    //Este metodo controla el flujo del wizard de modificar
    public String flujomodificar(FlowEvent event) throws Exception 
    {
        String cadena_validadora = "";
        if(event.getOldStep().equals("general") && event.getNewStep().equals("confirmar"))
        {
            if(this.cargoDao.validarExistCargoModificar(this.selectedCargo.getCargo().trim(),this.selectedCargo.getIdCargo()) != null)
            {
                cadena_validadora = cadena_validadora + "A";
            }
        }
        
        if(cadena_validadora.length() > 0)
        {
            if(cadena_validadora.contains("A"))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","El cargo " + this.selectedCargo.getCargo() + " ya existe")); 
            }
            return event.getOldStep();
        }
        else
        {
            return event.getNewStep();
        }
    }

}