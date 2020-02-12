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
import sv.ues.dao.PreservantesDao;
import sv.ues.dao.RolesDao;
import sv.ues.dao.TipoPruebaDao;
import sv.ues.dominio.Rol;
import sv.ues.dominio.TipoPrueba;

/**
 *
 * @author Daniel
 */

@ManagedBean(name="MbTipoPrueba")
@ViewScoped
public class MbTipoPrueba implements Serializable{
    
    private TipoPrueba tipoPrueba;
    private List<TipoPrueba> lsTipoPrueba=new ArrayList();
    private TipoPrueba selectedTipoPrubea;
    
    
    public MbTipoPrueba()
    {
        tipoPrueba=new TipoPrueba();
        tipoPrueba.setEstadoDisp(true);
    }
    
    public void registrar() throws Exception
    {
      
            TipoPruebaDao tipoPruebaDao=new TipoPruebaDao(); 
            tipoPruebaDao.registrar(tipoPrueba);
            tipoPrueba=new TipoPrueba();
            tipoPrueba.setEstadoDisp(true);
            PrimeFaces.current().ajax().update("tipoPruebaCreacion");
            PrimeFaces.current().ajax().update("tipoPruebaResgistrados");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informacion","Tipo de prueba registrado con exito")); 
        
    }
    
     public void modificar(TipoPrueba tipoPrueba)
    {
        selectedTipoPrubea=tipoPrueba;
    }
     
     public void modificarDialog() throws Exception
    {
         if(validarCamposModificar() == true)
        {
            TipoPruebaDao tipoPruebaDao=new TipoPruebaDao();
            tipoPruebaDao.actualizarTipoPrueba(selectedTipoPrubea);
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('dialogoModificar').hide();");
            PrimeFaces.current().ajax().update("tipoPruebaResgistrados");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informacion","Tipo de prueba modificado exitosamente"));
        }
    }
     
      public boolean validarCamposModificar() throws Exception
    {   
            /*   if(selectedTipoPrubea.getNomPrueba().trim().length() == 0 || selectedTipoPrubea.getDescripcion().trim().length() == 0)
                {
                    if(selectedTipoPrubea.getNomPrueba().trim().length() == 0)//VALIDAR QUE SE DIGITE EL NOMBRE DEl tipo de prueba
                    {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","Debe digitar el nombre"));
                    }
                    if(selectedTipoPrubea.getDescripcion().trim().length() == 0)//VALIDAR QUE SE DIGITE LA DESCRIPCION DEL tipo prueba
                    {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","Debe digitar la descripcion"));
                    }
                     return false;
                }
                else
                {
                         TipoPruebaDao tipoPruebaDao =new TipoPruebaDao();
                        if(tipoPruebaDao.validarTipoPruebaModificar(selectedTipoPrubea.getNomPrueba().trim().toLowerCase(),selectedTipoPrubea.getIdTipoPrueba()) == true)
                        {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","El tipo de prueba ya existe")); 
                            return false;
                        }
                        else
                        {
                            return true;
                        }
                }*/
                
            return true;
       
    }
     
   public void setSelected(TipoPrueba tipoPrueba)
    {
        selectedTipoPrubea=tipoPrueba;
    }
   
   public void eliminarDialog()
    {
        if(selectedTipoPrubea!=null)
       {
            TipoPruebaDao tipoPruebaDao=new TipoPruebaDao();
            tipoPruebaDao.eliminarTipoPrueba(selectedTipoPrubea);
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('dialogoEliminar').hide();");
            PrimeFaces.current().ajax().update("tipoPruebaResgistrados");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informacion","Eliminacion exitosa"));
       }
    }
   
    public List<TipoPrueba> listaTipoPrueba(){
        TipoPruebaDao tipoPruebaDao =new TipoPruebaDao();
        try
        {
            return tipoPruebaDao.obtenerTipoPruebas();
        }
        catch(Exception x)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,x.toString(),x.toString()));
            return null;
        }
    }
    
   //metodos respectivos get y setter para la vista 

    public TipoPrueba getTipoPrueba() {
        return tipoPrueba;
    }

    public void setTipoPrueba(TipoPrueba tipoPrueba) {
        this.tipoPrueba = tipoPrueba;
    }

    public List<TipoPrueba> getLsTipoPrueba() {
        return lsTipoPrueba;
    }

    public void setLsTipoPrueba(List<TipoPrueba> lsTipoPrueba) {
        this.lsTipoPrueba = lsTipoPrueba;
    }

    public TipoPrueba getSelectedTipoPrubea() {
        return selectedTipoPrubea;
    }

    public void setSelectedTipoPrubea(TipoPrueba selectedTipoPrubea) {
        this.selectedTipoPrubea = selectedTipoPrubea;
    }
  
    
    
}
