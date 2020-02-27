/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.mbsesion;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.PrimeFaces;
import sv.ues.dao.InvestigacionDao;
import sv.ues.dominio.Investigacion;
import sv.ues.dominio.Laboratorio;
import sv.ues.dominio.PerfilInv;

/**
 *
 * @author PC
 */
@ManagedBean
@ViewScoped
public class MbInvestigacion implements Serializable {

    private Investigacion investigacion;
    private List<Laboratorio> itemLaboratorio;
    private List<Investigacion> lsInvestigacions;
   private Investigacion investigacionSeleccionada;

    private Laboratorio lab;
    private Date fechaIni=new Date(), fechaFin=new Date();
    
       
    public Laboratorio getLab() {
        return lab;
    }

    public void setLab(Laboratorio lab) {
        this.lab = lab;
    }

    public Investigacion getInvestigacionSeleccionada() {
        return investigacionSeleccionada;
    }
   
    

    public List<Investigacion> getLsInvestigacions() {
        return lsInvestigacions;
    }

    public void setLsInvestigacions(List<Investigacion> lsInvestigacions) {
        this.lsInvestigacions = lsInvestigacions;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    

    
    
    public MbInvestigacion() throws Exception {
        InvestigacionDao investigacionDao=new InvestigacionDao();
        investigacion = new Investigacion();
        investigacion.setPerfilInvs(new PerfilInv());
        lsInvestigacions=investigacionDao.obtenerInvestigaciones();
    }

    public Investigacion getInvestigacion() {
        return investigacion;
    }

    public void setInvestigacion(Investigacion investigacion) {
        this.investigacion = investigacion;
    }

    public List<Laboratorio> getItemLaboratorio() {
        InvestigacionDao investigacionDao = new InvestigacionDao();

        this.itemLaboratorio = new ArrayList();
        itemLaboratorio = investigacionDao.getLaboratorio();

        return itemLaboratorio;
    }

    public void setItemLaboratorio(List<Laboratorio> itemLaboratorio) {
        this.itemLaboratorio = itemLaboratorio;
    }

    public void registrar() {
        try {
            //al registrar por defecto tendra el campo activo true -activo false  completo
            investigacion.setEstadoInvest(Boolean.TRUE);
          //  investigacion.setFechaRegistro(new Date());
            Date fechaCreacion=new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String fechaAux=sdf.format(fechaCreacion);
            fechaCreacion=sdf.parse(fechaAux);
            investigacion.setFechaCreacion(fechaCreacion);
            String [] aux=fechaAux.split("-");
            investigacion.setAnio(Integer.parseInt(aux[0]));
            investigacion.setMes(Integer.parseInt(aux[1]));
            investigacion.setCodInvest(Integer.parseInt(aux[0]+aux[1]));
            
           
            InvestigacionDao investigacionDao = new InvestigacionDao();
            investigacionDao.registrar(investigacion);
            investigacion=new Investigacion();
            investigacion.setPerfilInvs(new PerfilInv());
            
            PrimeFaces.current().ajax().update("F01");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Infomracion","Datos registrados correctamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informacion", "Error al guardar"));
        }
    }
    
     public void setInvestigacionSeleccionada(Investigacion investigacionSeleccionada) 
    {
        this.investigacionSeleccionada = investigacionSeleccionada;
                
    }
     
     public void modificar()
    {
        try
        {
            InvestigacionDao investigacionDao=new InvestigacionDao();
            investigacionDao.modificar(this.investigacionSeleccionada);
            
            
            PrimeFaces.current().ajax().update("F01");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Información","Investigación modificada con exito")); 
            PrimeFaces.current().executeScript("PF('dialogoModificar').hide();");
        }
        catch(Exception x)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"",x.toString())); 
        }
        
    }
     /**
     * Carga lista de investigaciones entre dos fechas dadas.
     * @throws Exception 
     */
     public void buscar_investigaciones_entre_fechas() throws Exception {
        if (fechaIni.after(fechaFin)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Fecha inicio debe menor o igual a la final"));
        } else {
            lsInvestigacions.clear();
            InvestigacionDao invDao = new InvestigacionDao();
            lsInvestigacions = invDao.obtener_investigaciones_entre_fechas(fechaIni, fechaFin);
            if (lsInvestigacions.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "No hay inventario de vectores en ese rango de fechas"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Inventario vectores cargado exitosamente"));
            }

        }
    }
     

}
