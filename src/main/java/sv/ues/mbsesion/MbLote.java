/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.mbsesion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;
import sv.ues.dao.ColoniasDao;
import sv.ues.dao.DepartamentoDao;
import sv.ues.dao.LotesDao;
import sv.ues.dao.MantoLoteDao;
import sv.ues.dao.MuestrasDao;
import sv.ues.dao.MunicipioDao;
import sv.ues.dao.VectorDao;
import sv.ues.dominio.BitacoraLab;
import sv.ues.dominio.ColoniaCanton;
import sv.ues.dominio.Departamento;
import sv.ues.dominio.Lote;
import sv.ues.dominio.Mantenimiento;
import sv.ues.dominio.Muestra;
import sv.ues.dominio.Municipio;
import sv.ues.dominio.Preservante;
import sv.ues.dominio.Vector;

/**
 *
 * @author Flor Rivas
 */
@ManagedBean(name = "MbLote")
@ViewScoped
public class MbLote implements Serializable {

    private Lote lote;
    private Mantenimiento manto;
    private Muestra muestra;
    private Integer cod_preservante;//
    private Integer cod_vector;
    private Integer correlativo=0;//Solo para mostrar al usuario
    private Lote loteSeleccionado;//Para ser usado en consultar lotes
    private Integer correlativo_muestra;
    private Lote modLote;
    private Mantenimiento modMantoLote;
    private Integer modCodPreservante;
    private Vector vector=new Vector();
    private Vector vectorAux=new Vector();
    
    private String cod_depto;
    private String cod_munic;
    private Integer cod_colon;
    
    private Departamento con_depto;
    private Municipio con_munic;
    private ColoniaCanton con_colca;   

    public MbLote() {
        lote = new Lote();
        manto = new Mantenimiento();
        muestra = new Muestra();
        loteSeleccionado = new Lote();
        correlativo_muestra=0;
        modLote = new Lote();
        modMantoLote = new Mantenimiento();
        
        cod_depto = null;
        cod_munic = null;
        cod_colon = null;
    }

    public Departamento getCon_depto() {
        return con_depto;
    }

    public void setCon_depto(Departamento con_depto) {
        this.con_depto = con_depto;
    }

    public Municipio getCon_munic() {
        return con_munic;
    }

    public void setCon_munic(Municipio con_munic) {
        this.con_munic = con_munic;
    }

    public ColoniaCanton getCon_colca() {
        return con_colca;
    }

    public void setCon_colca(ColoniaCanton con_colca) {
        this.con_colca = con_colca;
    }

    public String getCod_depto() {
        return cod_depto;
    }

    public void setCod_depto(String cod_depto) {
        this.cod_depto = cod_depto;
    }

    public String getCod_munic() {
        return cod_munic;
    }

    public void setCod_munic(String cod_munic) {
        this.cod_munic = cod_munic;
    }

    public Integer getCod_colon() {
        return cod_colon;
    }

    public void setCod_colon(Integer cod_colon) {
        this.cod_colon = cod_colon;
    }

    //Este metodo controla el flujo del wizard de registro
    public String flujoResgistrar(FlowEvent event) throws Exception {
        if (event.getOldStep().equals("general") && event.getNewStep().equals("confirmar")) {
            if (falla_validar_ingreso_lote())
            {
                //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "El lote ya existe"));
                return event.getOldStep();
            } else {
                return event.getNewStep();
            }
        }
        return event.getNewStep();
    }
    
public String flujoModificar(FlowEvent event) throws Exception {
        if (event.getOldStep().equals("general") && event.getNewStep().equals("confirmar")) {
            if (falla_validar_Modificar_lote()) {
                VectorDao vectorDao=new VectorDao();
                vectorAux=vectorDao.findByVectorById(modLote.getIdVector());
                return event.getOldStep();
            } else {
                VectorDao vectorDao=new VectorDao();
                vectorAux=vectorDao.findByVectorById(modLote.getIdVector());
                return event.getNewStep();
            }
        }
        return event.getNewStep();
    }
    private boolean falla_validar_Modificar_lote() {
        MuestrasDao md = new MuestrasDao();
        modLote.setFechaModificacion(new Date());
            if (existe_otro_lote_asi(modLote)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", "Lote ya existe"));
                return true;
            } else {
                if (modLote.getNumMuestras() == null || modLote.getNumMuestras() == 0) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", "Numero de muestras no puede ser cero o vacio"));
                    return true;
                } else {
                    if (modCodPreservante == null) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", "Seleccione un preservante"));
                        return true;
                    } else {
                        if (modLote.getIdVector() == null) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", "Seleccione un vector para la muestra"));
                            return true;
                        } else {
                            if (modMantoLote.getFechaProxManto()==null) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", "Seleccione la fecha"));
                                return true;
                            } else {
                               if(md.numero_muestras_lote(modLote.getIdLote())>modLote.getNumMuestras()){
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", "Numero de muestras no puede ser menor a las muestras ya registradas"));
                                    return true;
                                }else{
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
    }
    public void modificar_lote(){
        LotesDao lDao = new LotesDao();
        MantoLoteDao mDao = new MantoLoteDao();
        
        Preservante prs = new Preservante();
        ColoniaCanton cc = new ColoniaCanton();
        cc.setIdColCan(cod_colon);
        modLote.setColoniaCanton(cc);

        prs.setIdPreservante(modCodPreservante);
        modMantoLote.setPreservante(prs);
        modLote.setNombreLote(modLote.getNombreLote().toUpperCase());
        lDao.modificar_lote(modLote);//modifica lote
        manto.setLote(modLote);//Crea fk en Mantenimiento
        mDao.modificar_manto(modMantoLote);
        
        setModLote(null);
        setModMantoLote(new Mantenimiento());
        setModCodPreservante(0); 
        setCorrelativo_muestra(0);
        
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dialogoModificar').hide();");
        PrimeFaces.current().ajax().update("lotesRegistrados");
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Lote modificado con Exito"));
    }
    

    public Lote getModLote() {
        return modLote;
    }

    public void setModLote(Lote modLote) {
        this.modLote = modLote;
        
        if(modLote!=null){
            /* se recupera el manto ultimo dado al lote*/
            MantoLoteDao mlDao = new MantoLoteDao();
            Mantenimiento m = new Mantenimiento();
            m = mlDao.ultimo_manto_de_lote(modLote.getIdLote());
            if (m != null) {
                setModMantoLote(m);
                setModCodPreservante(getModMantoLote().getPreservante().getIdPreservante());
            }
            try {
                asignar_ubicacion_lote_mod_desde_colcan(modLote.getColoniaCanton().getIdColCan());
            } catch (Exception e) {
            }
            
        }
    }

    public Mantenimiento getModMantoLote() {
        return modMantoLote;
    }

    public void setModMantoLote(Mantenimiento modMantoLote) {
        this.modMantoLote = modMantoLote;
    }

    public Integer getModCodPreservante() {
        return modCodPreservante;
    }

    public void setModCodPreservante(Integer modCodPreservante) {
        this.modCodPreservante = modCodPreservante;
    }
    

    public Integer getCorrelativo_muestra() {
        return correlativo_muestra = correlativo_muestra +1;
    }

    public void setCorrelativo_muestra(Integer correlativo_muestra) {
        this.correlativo_muestra = correlativo_muestra;
    }
    

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public Mantenimiento getManto() {
        return manto;
    }

    public void setManto(Mantenimiento manto) {
        this.manto = manto;
    }

    public Muestra getMuestra() {
        return muestra;
    }

    public void setMuestra(Muestra muestra) {
        this.muestra = muestra;
    }

    public Integer getCod_preservante() {
        return cod_preservante;
    }

    public void setCod_preservante(Integer cod_preservante) {
        this.cod_preservante = cod_preservante;
    }

    public Integer getCod_vector() {
        return cod_vector;
    }

    public void setCod_vector(Integer cod_vector) {
        this.cod_vector = cod_vector;
    }

    public Integer getCorrelativo() {
        return correlativo=correlativo+1;
    }

    public void setCorrelativo(Integer correlativo) {
        this.correlativo = correlativo;
    }

    public Lote getLoteSeleccionado() {
        return loteSeleccionado;
    }

    public void setLoteSeleccionado(Lote loteSeleccionado) {
        this.loteSeleccionado = loteSeleccionado;
        VectorDao vectorDao=new VectorDao();
        this.vector=vectorDao.findByVectorById(loteSeleccionado.getIdVector());
        //correlativo_muestra=0;
        setCorrelativo_muestra(0);//pone correlativo a 0,
        asignar_ubicacion_lote_mod_desde_colcan(loteSeleccionado.getColoniaCanton().getIdColCan());
    }

    public Vector getVector() {
        return vector;
    }

    public void setVector(Vector vector) {
        this.vector = vector;
    }

    
    
    public void registrar_lote() {
        LotesDao lDao = new LotesDao();
        MantoLoteDao mDao = new MantoLoteDao();
        lote.setIdVector(cod_vector);
        lote.setNombreLote(lote.getNombreLote().toUpperCase());
        Preservante prs = new Preservante();
        ColoniaCanton clc = new ColoniaCanton();

        prs.setIdPreservante(cod_preservante);
        manto.setPreservante(prs);
        System.out.println("ACA:"+cod_colon);
        clc.setIdColCan(cod_colon);
        lote.setColoniaCanton(clc);
        
        manto.setCompletadoManto(true);
        manto.setFechaManto(lote.getFechaCreacion());
        manto.setFechaProxManto(lote.getFechaModificacion());
        
        Lote nlote = lDao.registrar_nuevo_lote(lote);//Se registra lote y se obtiene dicho objeto
        manto.setLote(nlote);//Crea fk en Mantenimiento
        mDao.registrar_nuevo_manto(manto);//Guarda el nuevo mantenimiento
        
        resetVariables();
        PrimeFaces.current().ajax().update("F01:registro");
        PrimeFaces.current().ajax().update("F01");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Lote registrado con Exito"));
    }

    private boolean falla_validar_ingreso_lote() {
        lote.setFechaCreacion(new Date());
            if (existe_este_lote()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", "Lote ya existe"));
                return true;
            } else {
                if (lote.getNumMuestras() == null || lote.getNumMuestras() == 0) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", "Numero de muestras no puede ser cero o vacio"));
                    return true;
                } else {
                    if (cod_preservante == null) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", "Seleccione un preservante"));
                        return true;
                    } else {
                        if (cod_vector == null) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", "Seleccione un vector para la muestra"));
                            return true;
                        } else {
                            if (lote.getFechaCreacion() == null || lote.getFechaModificacion() == null) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", "Seleccione la fecha"));
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                }
            }
        
    }
    
    private boolean existe_este_lote() {
        LotesDao lotesDao = new LotesDao();
        try {
            return lotesDao.existe_lote(lote.getNombreLote().toUpperCase());
        } catch (Exception x) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, x.toString(), x.toString()));
            return true;
        }
    }

    public List<Lote> lista_lote() {
        LotesDao lotesDao = new LotesDao();
        try {
            return lotesDao.obtener_lotes();
        } catch (Exception x) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, x.toString(), x.toString()));
            return null;
        }
    }
    
    public List<Lote> lista_lote_activos_inactivos(Integer estado) {
        LotesDao lotesDao = new LotesDao();
        try {
            return lotesDao.obtener_lotes_activos_inactivos(estado);
        } catch (Exception x) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, x.toString(), x.toString()));
            return null;
        }
    }
    
    public Lote lote_por_id(Integer idLote){
         LotesDao lotesDao = new LotesDao();
        try {
            return lotesDao.lote_por_id(idLote);
        } catch (Exception x) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, x.toString(), x.toString()));
            return null;
        }
    }
    
    public void resetVariables(){
        lote = new Lote();
        manto = new Mantenimiento();
        setCod_preservante(null);
        setCod_vector(null);
        setCod_colon(null);
        setCod_munic(null);
        setCod_depto(null);
    }
    public List<Lote> lista_lote_activos_falta_muestras(Integer estado) {
        LotesDao lotesDao = new LotesDao();
        try {
            return lotesDao.obtener_lotes_activos_falta_muestra(estado);
        } catch (Exception x) {
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, x.toString(), x.toString()));
            return null;
        }
    }
    private boolean existe_otro_lote_asi(Lote l){
    LotesDao lDao = new LotesDao();
    return lDao.existe_otro_lote_asi(l);
}
    
    public void reset_cod_munic_colon() {
        cod_munic = null;
        cod_colon = null;
    }
    public void reset_cod_colon(){
        cod_colon = null;
    }
    public List<SelectItem> lista_departamentos(){
        List<SelectItem> items_departamento = new ArrayList();
        try {
            items_departamento = new ArrayList();
            DepartamentoDao departamentos = new DepartamentoDao();
            List<Departamento> lista_departamentos = departamentos.obtener_todos_los_departamentos();
            items_departamento.clear();
            for(Departamento dep:lista_departamentos)
            {
                SelectItem item = new SelectItem(dep.getCodDepto(),dep.getNomDepto());
                items_departamento.add(item);
            }
            return items_departamento;
        } catch (Exception ex) {
            //Logger.getLogger(MbMuestrasLotes.class.getName()).log(Level.SEVERE, null, ex);
            return items_departamento;
        }
    }
    
    public List<SelectItem> lista_municipios(String idDepto){
        List<SelectItem> items_municipio = new ArrayList();
        if (idDepto == null) {
            items_municipio.clear();
            return items_municipio;
        } else {
            items_municipio = new ArrayList();
            MunicipioDao municipios = new MunicipioDao();
            Departamento departamento_seleccionado = new Departamento();
            departamento_seleccionado.setCodDepto(idDepto);
            List<Municipio> lista_municipios_por_dpto = municipios.obtener_municipios_por_id_del_departamento(departamento_seleccionado);
            items_municipio.clear();
            for (Municipio muni : lista_municipios_por_dpto) {
                SelectItem item = new SelectItem(muni.getCodMunicipio(), muni.getNomMunicipio());
                items_municipio.add(item);
            }
            return items_municipio;
        }
    }
    
    public List<SelectItem> lista_colonias(String idMunic){
        List<SelectItem> items_colonias = new ArrayList();
        if(idMunic==null){
            items_colonias.clear();
            return items_colonias;
        }else{
            items_colonias = new ArrayList();
            ColoniasDao colonias = new ColoniasDao();

            Municipio muni_seleccionado = new Municipio();
            muni_seleccionado.setCodMunicipio(idMunic);

            List<ColoniaCanton> lista_colonias_por_muni = colonias.obtenerCantonesByMunicipio(muni_seleccionado);//lista_cantones_por_muni(muni_seleccionado);
            items_colonias.clear();
            for (ColoniaCanton col : lista_colonias_por_muni) {
                SelectItem item = new SelectItem(col.getIdColCan(), col.getNomUbicacion());
                items_colonias.add(item);
            }
            return items_colonias;
        }
        
    }
    
    public ColoniaCanton colonia_por_id(Integer cod) {
        ColoniasDao cDao = new ColoniasDao();
        try {
            return cDao.obtenerColoniaCanton_por_id(cod);
        } catch (Exception x) {
            return new ColoniaCanton();
        }
    }
    
    public Departamento depto_por_id(String cod) {
        DepartamentoDao dDao = new DepartamentoDao();
        try {
            return dDao.departamento_por_id(cod);
        } catch (Exception x) {
            return new Departamento();
        }
    }
    
    public Municipio municipio_por_id(String cod) {
        MunicipioDao mDao = new MunicipioDao();
        try {
            return mDao.obtener_municipio(cod);
        } catch (Exception x) {
            return new Municipio();
        }
    }
    
    public String mostrar_codigo_nombre_depto_desde_idColon(Integer idColon){
        try {
            ColoniaCanton cc = colonia_por_id(idColon);
            Municipio mm = municipio_por_id(cc.getMunicipio().getCodMunicipio());
            Departamento dd = depto_por_id(mm.getDepartamento().getCodDepto());
            return dd.getCodDepto()+" - "+dd.getNomDepto();
        } catch (Exception x) {
            return "Sin especificar";
        }
    }

    private void asignar_ubicacion_lote_mod_desde_colcan(int idColCan) {
        try {
            ColoniaCanton cc = colonia_por_id(idColCan);
            setCon_colca(cc);//para consultar
            setCod_colon(idColCan);//para modificar
            setCod_munic(cc.getMunicipio().getCodMunicipio());//para modifcar
            
            Municipio mm = municipio_por_id(cc.getMunicipio().getCodMunicipio());
            setCon_munic(mm);//para consultar
            setCod_depto(mm.getDepartamento().getCodDepto());
            
            Departamento dd = depto_por_id(mm.getDepartamento().getCodDepto());
            setCon_depto(dd);
        } catch (Exception x) {
        
        }
    }

    public Vector getVectorAux() {
        return vectorAux;
    }

    public void setVectorAux(Vector vectorAux) {
        this.vectorAux = vectorAux;
    }
    
    
    
}
