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
import javax.faces.model.SelectItem;
import sv.ues.dao.InvestigacionDao;
import sv.ues.dao.PerfilInvestigacionDao;
import sv.ues.dominio.Investigacion;


import sv.ues.dominio.PerfilInv;

/**
 *
 * @author PC
 */
@ManagedBean
@ViewScoped
public class MbPerfilInvestigacion implements Serializable{
    
    private PerfilInv perfilInv;
    private List<Investigacion> lsInvestigacion;
    private Investigacion investigacion;
   
    
    public PerfilInv getPerfilInv() {
        return perfilInv;
    }

    public void setPerfilInv(PerfilInv perfilInv) {
        this.perfilInv = perfilInv;
    }
    
    public MbPerfilInvestigacion(){
        perfilInv=new PerfilInv();
    }
    
    public List<Investigacion> getLsInvestigacion() throws Exception{
        this.lsInvestigacion=new ArrayList<Investigacion>();
        InvestigacionDao investigacionDao=new InvestigacionDao();
        List<Investigacion> listInvestigacion=investigacionDao.getInvestiagacionByActivo();
     
        return  listInvestigacion;
    }

    public Investigacion getInvestigacion() {
        return investigacion;
    }
    
    public void registrar() throws Exception{
        PerfilInvestigacionDao perfilDao=new PerfilInvestigacionDao();
        perfilInv=new PerfilInv();
        perfilInv.setInvestigacion(investigacion);
        perfilDao.registrar(perfilInv);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Info","Registro guardado"));
    }
    
    
    
}
