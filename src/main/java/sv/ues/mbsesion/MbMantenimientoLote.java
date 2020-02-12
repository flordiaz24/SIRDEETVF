/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.mbsesion;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;
import sv.ues.dao.MantenimientoDao;
import sv.ues.dao.MantoLoteDao;
import sv.ues.dominio.Lote;
import sv.ues.dominio.Mantenimiento;
import sv.ues.dominio.Preservante;

/**
 *
 * @author PC
 */
@ManagedBean(name = "mbMantenimientoLote")
@ViewScoped
public class MbMantenimientoLote implements Serializable {

    private Integer nManto;//Solo es un correlativo a mostrar en la vista
    private Integer cod_lote, cod_preservante;//para los combo
    private Mantenimiento manto;
    private int idPreservante = 0;

    /**
     * Creates a new instance of MbMantenimientoLote
     */
    public MbMantenimientoLote() {
        manto = new Mantenimiento();
    }

    public int getIdPreservante() {
        return idPreservante;
    }

    public void setIdPreservante(int idPreservante) {
        this.idPreservante = idPreservante;
    }
    
    public Integer getnManto() {
       return numero_manto(cod_lote)+1;
    }

    public void setnManto(Integer nManto) {
        this.nManto = nManto;
    }

    public Mantenimiento getManto() {
        return manto;
    }

    public void setManto(Mantenimiento manto) {
        this.manto = manto;
    }

    public Integer getCod_lote() {
        return cod_lote;
    }

    public void setCod_lote(Integer cod_lote) {
        this.cod_lote = cod_lote;
    }

    public Integer getCod_preservante() {
        return cod_preservante;
    }

    public void setCod_preservante(Integer cod_preservante) {
        this.cod_preservante = cod_preservante;
    }

    public String flujoResgistrar(FlowEvent event) throws Exception {
        if (event.getOldStep().equals("general") && event.getNewStep().equals("confirmar")) {
            if (falla_validar_ingreso_manto())
            {
                //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "El lote ya existe"));
                return event.getOldStep();
            } else {
                return event.getNewStep();
            }
        }
        return event.getNewStep();
    }

    private boolean falla_validar_ingreso_manto() {
        MantoLoteDao mlDao = new MantoLoteDao();
        manto.setFechaManto(new Date());
        if (cod_lote == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", "Seleccione un lote"));
            return true;
        } else {
            if (cod_preservante == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", "Seleccione el preservante"));
                return true;
            } else {
                if (!manto.isCompletadoManto()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", "Selecione mantenimiento completado"));
                    return true;
                } else {
                    if (manto.getFechaManto() == null || manto.getFechaProxManto() == null) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", "Seleccione la fecha"));
                        return true;
                    } else {
                        if (mlDao.existe_fechamanto_lote(manto.getFechaProxManto(), cod_lote)) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", "Ya existe mantenimiento programado para esta fecha"));
                            return true;
                        } else {
                            return false;
                        }
                    }

                }
            }
        }
    }

    public void registrar_nuevo_manto() {
        MantoLoteDao mDao = new MantoLoteDao();

        Preservante prs = new Preservante();//Objeto para fk
        Lote lote = new Lote();//Objeto para fk

        lote.setIdLote(cod_lote);
        prs.setIdPreservante(cod_preservante);

        manto.setLote(lote);//Crea fk en Mantenimiento
        manto.setPreservante(prs);//Crea fk en Mantenimiento

        mDao.registrar_nuevo_manto(manto);//Guarda el nuevo mantenimiento

        manto = new Mantenimiento();//reinicia valores para no mostrar en vista
        cod_lote = null;
        cod_preservante = null;
        PrimeFaces.current().ajax().update("F01:registro");
        PrimeFaces.current().ajax().update("F01");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Lote registrado con éxito"));
    }

    private Integer numero_manto(Integer idLote) {
        MantoLoteDao mlDao = new MantoLoteDao();
        try {
            return mlDao.numero_manto_lote(idLote);
        } catch (Exception x) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, x.toString(), x.toString()));
            return null;
        }
    }
    
    public List<Mantenimiento> lista_manto_activos_inactivos(boolean estado) {
        MantoLoteDao lotesDao = new MantoLoteDao();
        try {
            return lotesDao.obtener_manto_activos_inactivos(estado);
        } catch (Exception x) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, x.toString(), x.toString()));
            return null;
        }
    }
    
    public List<Mantenimiento> lista_manto_de_hoy(boolean estado) {
        MantoLoteDao lotesDao = new MantoLoteDao();
        try {
            return lotesDao.lista_manto_de_hoy(estado);
        } catch (Exception x) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, x.toString(), x.toString()));
            return null;
        }
    }

    public List<Mantenimiento> lista_mantenimientos_por_lote(Integer idLote){
        MantoLoteDao mantoDao = new MantoLoteDao();
        try {
            return mantoDao.lista_mantenimientos_por_lote(idLote);
        } catch (Exception x) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, x.toString(), x.toString()));
            return null;
        }
    }
    //Metodo para modificar el mantenimiento
    public void modificarMantenimiento(){
      MantenimientoDao mantenimiento = new MantenimientoDao();
      //MantoLoteDao mantenimiento=new MantoLoteDao();
        MbPreservante pre = new MbPreservante();
        List<Preservante> listaPre = pre.getLsPreservante();
        for (int i = 0; i < listaPre.size(); i++) {
            if (listaPre.get(i).getIdPreservante() == idPreservante) {
                manto.setPreservante(listaPre.get(i));
            }
        }
        mantenimiento.actualizar_manto(manto);
        //mantenimiento.modificar_manto(manto);
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dialogoModificar').hide();");
        manto = new Mantenimiento();
        System.out.println(""+idPreservante);
        idPreservante = 0;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informacion","Mantenimiento modificado exitosamente"));
    }
}
