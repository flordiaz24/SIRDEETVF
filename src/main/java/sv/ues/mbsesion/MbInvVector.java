/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.mbsesion;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import sv.ues.dao.InvVectorDao;
import sv.ues.dao.InvestigacionDao;
import sv.ues.dao.PerfilInvDao;
import sv.ues.dao.VectorDao;
import sv.ues.dominio.InvVector;
import sv.ues.dominio.Investigacion;
import sv.ues.dominio.PerfilInv;
import sv.ues.dominio.Vector;

/**
 *
 * @author PC
 */
@ManagedBean
@ViewScoped
public class MbInvVector implements Serializable{
    
    private InvVector invVector;
    private List<Investigacion> lsInvestigacion;
    private List<Vector> lsVector;
    private List<InvVector> lsInvVector;
    private InvVector invVectorSeleccionado;
    private Date fechaIni=new Date(), fechaFin=new Date();
    private Date hoy= new Date();
   
    
    public MbInvVector(){
        InvVectorDao invVectorDao=new InvVectorDao();
        lsInvVector=invVectorDao.getListInvVector();
        invVector=new InvVector();
        invVectorSeleccionado=new InvVector();
       
    }    
    public List<Investigacion> getLsInvestigacion() {
        InvestigacionDao investigacionDao=new InvestigacionDao();
        this.lsInvestigacion=  investigacionDao.getInvestiagacionByActivo();
       return lsInvestigacion;
    }

    public void setLsInvestigacion(List<Investigacion> lsInvestigacion) {
        this.lsInvestigacion = lsInvestigacion;
    }

    public List<Vector> getLsVector() {
        VectorDao vectorDao=new VectorDao();
       
        return lsVector= vectorDao.getVectores();
    }

    public void setLsVector(List<Vector> lsVector) {
        this.lsVector = lsVector;
    }
    
    
    
    public InvVector getInvVector() {
        return invVector;
    }

    public void setInvVector(InvVector invVector) {
        this.invVector = invVector;
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

    public Date getHoy() {
        return hoy;
    }

    public void setHoy(Date hoy) {
        this.hoy = hoy;
    }
    
    
       
    public void registrarVector() throws ParseException{
        //llamaremos el dao para guardar el vector
        InvVectorDao invVectorDao=new InvVectorDao();
        Date fechaReg=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String fechaAux=sdf.format(fechaReg);
        fechaReg=sdf.parse(fechaAux);
        invVector.setFechaRegistro(fechaReg);
        invVectorDao.guardar(invVector);
        invVector=new InvVector();
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "info","datos guardados"));
    }

    

    public List<InvVector> getLsInvVector() {
        return lsInvVector;
    }

    public InvVector getInvVectorSeleccionado() {
        return invVectorSeleccionado;
    }
    
    
    public void setInvestigacionSeleccionada(InvVector invVector) 
    {
        this.invVectorSeleccionado = invVector;
                
    }
    
    
    public void modificar()
    {
        try
        {
            InvVectorDao invVectorDao=new InvVectorDao();
            invVectorDao.modificar(invVectorSeleccionado);
            
            
            
            PrimeFaces.current().ajax().update("F01");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Información","Investigación de vector modificada con exito")); 
            PrimeFaces.current().executeScript("PF('dialogoModificar').hide();");
        }
        catch(Exception x)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"",x.toString())); 
        }
        
    }
    /**
     * Carga lista de inventario de vectores entre dos fechas dadas.
     * @throws Exception 
     */
     public void buscar_inv_vectores_entre_fechas() throws Exception {
        if (fechaIni.after(fechaFin)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Fecha inicio debe menor o igual a la final"));
        } else {
            lsInvVector.clear();
            InvVectorDao ivDao = new InvVectorDao();
            lsInvVector = ivDao.obtener_inv_vectores_entre_fechas(fechaIni, fechaFin);
            if (lsInvVector.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "No hay inventario de vectores en ese rango de fechas"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Inventario vectores cargado exitosamente"));
            }

        }
    }
    
}
