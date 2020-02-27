/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.mbsesion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;
import sv.ues.dominio.BitacoraLab;

/**
 *
 * @author Flor Diaz
 */
@ManagedBean(name = "mbBitLabZancudo")
@ViewScoped
public class MbBitacoraLabZancudo implements Serializable{
    private List<BitacoraLab> lsBLabZancudo = new ArrayList<BitacoraLab>();//Pruebas mostrar
    private BitacoraLab bitLabZancudoSelec;//Pruebas consultar/modificar

    /**
     * Crea nueva instancia de MbBitacoraLabZancudo
     */
    public MbBitacoraLabZancudo() {
        inicializarVariables();//LLamada para inicailizar variables
    }
    
    /**
     * Inicia getter y setter
     */
    
    public BitacoraLab getBitLabZancudoSelec() {
        return bitLabZancudoSelec;
    }

    public void setBitLabZancudoSelec(BitacoraLab bitLabZancudoSelec) {
        this.bitLabZancudoSelec = bitLabZancudoSelec;
    }

    public List<BitacoraLab> getLsBLabZancudo() {
        return lsBLabZancudo;
    }

    public void setLsBLabZancudo(List<BitacoraLab> lsBLabZancudo) {
        this.lsBLabZancudo = lsBLabZancudo;
    }    
    /**
     * Termina getter y setter
     */

    /**
     * Incializa variables de prueba
     */
    private void inicializarVariables(){
        BitacoraLab bLab = new BitacoraLab(1, null, null, "Bitacora1");
        BitacoraLab bLab2 = new BitacoraLab(2, null, null, "Bitacora2");
        lsBLabZancudo.add(bLab);
        lsBLabZancudo.add(bLab2);
    }
    
    /**
     * Controla el flujo en la pantalla de registrar
     * @param event
     * @return
     * @throws Exception 
     */
    public String flujoResgistrar(FlowEvent event) throws Exception 
    {
        String validacion = "";
        
        //VALIDACION AVANZAR General --> Localizacion
        if(event.getOldStep().equals("general") && event.getNewStep().equals("localizacion"))
        {
            //validacion+="A";//Muestra primer validacion
        }
        
        //VALIDACION AVANZAR Domicilio --> Contacto
        if(event.getOldStep().equals("localizacion") && event.getNewStep().equals("confirmar"))
        {
            //validacion+="B";//Muestra segunda validacion
        }
        
        
        //MOSTRAR MENSAJES
        if(validacion.length() > 0)
        {
            if(validacion.contains("A"))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","Primer validacion")); 
            }
            if(validacion.contains("B"))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","Segunda validacion")); 
            }
            return event.getOldStep();
        }
        else
        {
            return event.getNewStep();
        }
    }
    
    /**
     * Registra nueva bitacora 
     */
    public void registrarBitacoraLabZancudo(){
        PrimeFaces.current().ajax().update("F01");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informacion","Bitacora registrada con exito")); 
    }
    
    /**
     * Controla el flujo en la pantalla de modificar bitacoras
     * @param event
     * @return
     * @throws Exception 
     */
    public String flujoModificar(FlowEvent event) throws Exception {
        String validacion = "";
        
        //VALIDACION AVANZAR General --> Localizacion
        if(event.getOldStep().equals("general") && event.getNewStep().equals("localizacion"))
        {
            //validacion+="A";//Muestra primer validacion
        }
        
        //VALIDACION AVANZAR Domicilio --> Contacto
        if(event.getOldStep().equals("localizacion") && event.getNewStep().equals("confirmar"))
        {
            //validacion+="B";//Muestra segunda validacion
        }
       
        //MOSTRAR MENSAJES
        if(validacion.length() > 0)
        {
            if(validacion.contains("A"))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","Primer validacion")); 
            }
            if(validacion.contains("B"))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","Segunda validacion")); 
            }
            return event.getOldStep();
        }
        else
        {
            return event.getNewStep();
        }        
    }
    
    /**
     * Modifica una bitacora
     */
    public void modificarBitacoraLabZancudo(){
        PrimeFaces.current().ajax().update("registros");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informacion","Bitácora modificada con éxito")); 
        PrimeFaces.current().executeScript("PF('dialogoModificar').hide();");
    }
}
