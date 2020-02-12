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
import sv.ues.dao.CaserioDao;
import sv.ues.dao.ColoniasDao;
import sv.ues.dao.DepartamentoDao;
import sv.ues.dao.LotesDao;
import sv.ues.dao.MuestrasDao;
import sv.ues.dao.MunicipioDao;
import sv.ues.dao.TipoMuestraDao;
import sv.ues.dao.VectorDao;
import sv.ues.dominio.BitacoraCampo;
import sv.ues.dominio.Cacerio;
import sv.ues.dominio.ColoniaCanton;
import sv.ues.dominio.Departamento;
import sv.ues.dominio.Familia;
import sv.ues.dominio.Lote;
import sv.ues.dominio.Muestra;
import sv.ues.dominio.Municipio;
import sv.ues.dominio.Orden;
import sv.ues.dominio.TipoMuestra;
import sv.ues.dominio.Vector;

/**
 *
 * @author Flor Diaz
 */
@ManagedBean(name = "mbMuestrasLotes")
@ViewScoped
public class MbMuestrasLotes implements Serializable {

    private Muestra muestraLote;
    private Integer cod_lote;
    private Integer cod_lote2;
    private Integer cod_tipomuestra;
    private Integer cod_estadio;
    private Integer codOrden, codFamilia;
    private Vector vector;
    private Cacerio caserio;
    private String coddepto, codmuni;//solo para validar
    private Integer cCanton;
    private Integer nMuestrasLote;//para el correlativo de muestras
    private Muestra muestraSeleccionada;
    private Cacerio ubicacionCaserio;
    private ColoniaCanton ubicacionColonia;
    private Municipio ubicacionMunicipio;
    private Departamento ubicacionDepto;
    private Muestra modMuestra;
    private Integer modTipomuestra;
    private List<SelectItem> items_municipio = new ArrayList();
    private List<SelectItem> items_departamento;
    private List<SelectItem> items_colonias = new ArrayList();
    private String codigo_muestra="";

    /**
     * Creates a new instance of MbMuestrasLotes
     */
    public MbMuestrasLotes() {
        muestraLote = new Muestra();
        vector = new Vector();
        caserio = new Cacerio();
        ubicacionCaserio = new Cacerio();
        ubicacionColonia = new ColoniaCanton();
        ubicacionMunicipio = new Municipio();
        ubicacionDepto = new Departamento();
    }

    public List<SelectItem> getItems_municipio() {
        if(ubicacionDepto.getCodDepto()==null){
            items_municipio.clear();
            return items_municipio;
        }else{
       this.items_municipio = new ArrayList();
        MunicipioDao municipios = new MunicipioDao();
        Departamento departamento_seleccionado = new Departamento();
        departamento_seleccionado.setCodDepto(ubicacionDepto.getCodDepto());
        List<Municipio> lista_municipios_por_dpto = municipios.obtener_municipios_por_id_del_departamento(departamento_seleccionado);
        this.items_municipio.clear();
        for(Municipio muni:lista_municipios_por_dpto)
        {
            SelectItem item = new SelectItem(muni.getCodMunicipio(),muni.getNomMunicipio());
            this.items_municipio.add(item);
        }
        return items_municipio;
        }
    }

    public String getCodigo_muestra() {
        return codigo_muestra;
    }

    public void setCodigo_muestra(String codigo_muestra) {
        this.codigo_muestra = codigo_muestra;
    }
    

    public void setItems_municipio(List<SelectItem> items_municipio) {
        this.items_municipio = items_municipio;
    }

    public List<SelectItem> getItems_departamento() {
        try {
            this.items_departamento = new ArrayList();
            DepartamentoDao departamentos = new DepartamentoDao();
            List<Departamento> lista_departamentos = departamentos.obtener_todos_los_departamentos();
            this.items_departamento.clear();
            for(Departamento dep:lista_departamentos)
            {
                SelectItem item = new SelectItem(dep.getCodDepto(),dep.getNomDepto());
                this.items_departamento.add(item);
            }
            return items_departamento;
        } catch (Exception ex) {
            //Logger.getLogger(MbMuestrasLotes.class.getName()).log(Level.SEVERE, null, ex);
            return items_departamento;
        }
    }

    public void setItems_departamento(List<SelectItem> items_departamento) {
        this.items_departamento = items_departamento;
    }

    public List<SelectItem> getItems_colonias() {
if(ubicacionMunicipio.getCodMunicipio()==null){
            items_colonias.clear();
            return items_colonias;
        }else{
            this.items_colonias = new ArrayList();
            ColoniasDao colonias = new ColoniasDao();

            Municipio muni_seleccionado = new Municipio();
            muni_seleccionado.setCodMunicipio(ubicacionMunicipio.getCodMunicipio());

            List<ColoniaCanton> lista_colonias_por_muni = colonias.obtenerCantonesByMunicipio(muni_seleccionado);//lista_cantones_por_muni(muni_seleccionado);
            this.items_colonias.clear();
            for (ColoniaCanton col : lista_colonias_por_muni) {
                SelectItem item = new SelectItem(col.getIdColCan(), col.getNomUbicacion());
                this.items_colonias.add(item);
            }
            return items_colonias;
        }
    }

    public void setItems_colonias(List<SelectItem> items_colonias) {
        this.items_colonias = items_colonias;
    }
    

    public Integer getModTipomuestra() {
        return modTipomuestra;
    }

    public void setModTipomuestra(Integer modTipomuestra) {
        this.modTipomuestra = modTipomuestra;
    }
    

    public Muestra getModMuestra() {
        return modMuestra;
    }

    public void setModMuestra(Muestra modMuestra) {
        this.modMuestra = modMuestra;
        this.modTipomuestra=modMuestra.getTipoMuestra().getIdTipoMues();
        //asignarUbicacionMuestra_desde_caserio(modMuestra.getCacerio().getIdCacerio());
    }
    

    public Cacerio getUbicacionCaserio() {
        return ubicacionCaserio;
    }

    public void setUbicacionCaserio(Cacerio ubicacionCaserio) {
        this.ubicacionCaserio = ubicacionCaserio;
    }

    public ColoniaCanton getUbicacionColonia() {
        return ubicacionColonia;
    }

    public void setUbicacionColonia(ColoniaCanton ubicacionColonia) {
        this.ubicacionColonia = ubicacionColonia;
    }

    public Municipio getUbicacionMunicipio() {
        return ubicacionMunicipio;
    }

    public void setUbicacionMunicipio(Municipio ubicacionMunicipio) {
        this.ubicacionMunicipio = ubicacionMunicipio;
    }

    public Departamento getUbicacionDepto() {
        return ubicacionDepto;
    }

    public void setUbicacionDepto(Departamento ubicacionDepto) {
        this.ubicacionDepto = ubicacionDepto;
    }
    

    public Muestra getMuestraSeleccionada() {
        return muestraSeleccionada;
    }

    public void setMuestraSeleccionada(Muestra muestraSeleccionada) {
        this.muestraSeleccionada = muestraSeleccionada;
        //asignarUbicacionMuestra_desde_caserio(muestraSeleccionada.getCacerio().getIdCacerio());
    }

    public Integer getCod_lote2() {
        return cod_lote2;
    }

    public void setCod_lote2(Integer cod_lote2) {
        this.cod_lote2 = cod_lote2;
    }

    public Integer getnMuestrasLote() {
        setnMuestrasLote(numero_muestras_ingresadas_en_lote(cod_lote) + 1);
        return nMuestrasLote;
    }

    public void setnMuestrasLote(Integer nMuestrasLote) {
        this.nMuestrasLote = nMuestrasLote;
    }

    public Integer getcCanton() {
        return cCanton;
    }

    public void setcCanton(Integer cCanton) {
        this.cCanton = cCanton;
    }

    public String getCoddepto() {
        return coddepto;
    }

    public void setCoddepto(String coddepto) {
        this.coddepto = coddepto;
    }

    public String getCodmuni() {
        return codmuni;
    }

    public void setCodmuni(String codmuni) {
        this.codmuni = codmuni;
    }

    public Cacerio getCaserio() {
        return caserio;
    }

    public void setCaserio(Cacerio caserio) {
        this.caserio = caserio;
    }

    public Integer getCodOrden() {
        return codOrden;
    }

    public void setCodOrden(Integer codOrden) {
        this.codOrden = codOrden;
    }

    public Integer getCodFamilia() {
        return codFamilia;
    }

    public void setCodFamilia(Integer codFamilia) {
        this.codFamilia = codFamilia;
    }

    public Vector getVector() {
        return vector;
    }

    public void setVector(Vector vector) {
        this.vector = vector;
    }

    public Integer getCod_estadio() {
        return cod_estadio;
    }

    public void setCod_estadio(Integer cod_estadio) {
        this.cod_estadio = cod_estadio;
    }

    public Integer getCod_tipomuestra() {
        return cod_tipomuestra;
    }

    public void setCod_tipomuestra(Integer cod_tipomuestra) {
        this.cod_tipomuestra = cod_tipomuestra;
    }

    public Integer getCod_lote() {
        return cod_lote;
    }

    public void setCod_lote(Integer cod_lote) {
        this.cod_lote = cod_lote;
    }

    public Muestra getMuestraLote() {
        return muestraLote;
    }

    public void setMuestraLote(Muestra muestraLote) {
        this.muestraLote = muestraLote;
    }

    public String flujoResgistrar(FlowEvent event) throws Exception {
        if (event.getOldStep().equals("general") && event.getNewStep().equals("confirmar")) {
            if (falla_validar_ingreso_muestra()) {
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
            if (falla_validar_modificar_muestra()){
                //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "El lote ya existe"));
                return event.getOldStep();
            } else {
                return event.getNewStep();
            }
        }
        return event.getNewStep();
    }

    private boolean falla_validar_ingreso_muestra() {
         if (getCod_lote() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", "Seleccione el lote correspondiente"));
            return true;
        } else {
            if (getCod_tipomuestra() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", "Seleccione el tipo de muestra"));
                return true;
            } else {
                if (getCod_estadio() == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", "Seleccione estadio de la muestra"));
                    return true;
                } else {
                    if (getMuestraLote().getGeneroMuestra() == "") {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", "Seleccione el genero"));
                        return true;
                    } else {
                        if (getMuestraLote().getParasito() == null) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", "Seleccione si tiene o  no parasitos"));
                            return true;
                        } else {
                            return false;//Pasa todas las validaciones.
                        }
                    }
                }
            }
        }
    }
     private boolean falla_validar_modificar_muestra() {
         if (getModTipomuestra() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", "Seleccione el Tipo de muestra"));
            return true;
        } else {
            if (modMuestra.getGeneroMuestra() == "") {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", "Seleccione el genero"));
                return true;
            } else {
                if (modMuestra.getParasito() == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia", "Seleccione si tiene o  no parasitos"));
                    return true;
                } else {
                    return false;
                }
            }
        }
     }
        
    

    public List<Muestra> muestras_por_lote(Integer idLote) {
        MuestrasDao muDao = new MuestrasDao();
        try {
            return muDao.muestra_por_lote(idLote);
        } catch (Exception x) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, x.toString(), x.toString()));
            return null;
        }
    }

    //Metodo que permite obtener el listado de tipo de muestras
    public List<TipoMuestra> listaTipoMuestras() {
        TipoMuestraDao tipoMuestraDao = new TipoMuestraDao();
        return tipoMuestraDao.getTiposMuestras();
    }

    public String estadio_muestra(Integer estadio) {
        String est = "";
        try {
            switch (estadio) {
                case 1:
                    est = "Huevo";
                    break;
                case 2:
                    est = "1 Fase ninfal";
                    break;
                case 3:
                    est = "2 Fase ninfal";
                    break;
                case 4:
                    est = "3 Fase ninfal";
                    break;
                case 5:
                    est = "4 Fase ninfal";
                    break;
                case 6:
                    est = "5 Fase ninfal";
                    break;
                case 7:
                    est = "Fase adulta";
                    break;
                default:
                    est = "No determinada";
                // code block
            }
        } catch (Exception e) {
            est = "No determinada";
        }
        return est;
    }

    /**
     * Devuelve Tipo muestra segun el ID dado
     *
     * @param idTipoMuestra Integer, ID dado
     * @return TipoMuestra
     */
    public TipoMuestra tipo_muestra_por_id(Integer idTipoMuestra) {
        TipoMuestraDao tipoMuestraDao = new TipoMuestraDao();
        TipoMuestra tm = new TipoMuestra();
        if (idTipoMuestra == null) {
            return tm;
        } else {
            return tipoMuestraDao.getTipoMuestraById(idTipoMuestra);
        }

    }

    public void registrar_nueva_muestra_lote() {
        MuestrasDao mDao = new MuestrasDao();
        
        LotesDao lDao = new LotesDao();
        /**
         * Creando instancias de objetos para FK en Muestras
         */
        Lote lo = new Lote();
        TipoMuestra tm = new TipoMuestra();
        //BitacoraCampo bc = new BitacoraCampo();
        muestraLote.setSecuencia(getnMuestrasLote());
        /**
         * "Setiando" los ID a cada instancia de objetos para ser usados como FK
         * en muestras
         */
        muestraLote.setEdadMuestra(cod_estadio);
        lo.setIdLote(cod_lote);
        tm.setIdTipoMues(cod_tipomuestra);
        //bc.setIdBitCampo(3);/*La tabla bitacora_campo, solo tiene un registro con ID 3; Debe modificarse para soportar nulos o buscar otra alternativa, por el momento es obligatorio*/

        /**
         * "Setiando" las FK del Muestras
         */
        muestraLote.setLote(lo);
        muestraLote.setTipoMuestra(tm);
        //muestraLote.setBitacoraCampo(bc);

        /**
         * LLenando con basura los campos obligatorios y que no estan en la
         * vista.
         */
        muestraLote.setNomJefeFam("JefeFamilia");
        muestraLote.setFechaTrabajo(new Date());
        muestraLote.setFechaMuestra(new Date());
        muestraLote.setNumeroMuestra("0");/*Este campo seria "innecesario" porque el numero de muestras esta indicado en Lote, a menos que este sea como un correlativo */

        /**
         * Guardando nuevo cacerio para esta muestra
         */
        
        //muestraLote.setCacerio(caserio);//Se pone el objeto/id del caserio ingresado(hibernate devuelve el ID)

        /**
         * Guardando nuevo registro
         */
        muestraLote.setCodigoMuestra(Integer.toString(mDao.obtenerNumMuestras() + 1));
        mDao.registrar_nueva_muestra(muestraLote);

        PrimeFaces.current().ajax().update("F01:registro");
        PrimeFaces.current().ajax().update("F01");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Muestra registrada con Exito"));

        if ((getnMuestrasLote() - 1) == lDao.lote_por_id(cod_lote).getNumMuestras()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Numero maximo de muestras alcanzado"));
        }
        limpiar_variables();
    }

    public void limpiar_variables() {
        setCod_lote(0);
        setVector(new Vector());
        setCodOrden(null);
        setCodFamilia(null);
        setCod_tipomuestra(null);
        setCod_estadio(0);
        setMuestraLote(new Muestra());
        setCodigo_muestra("");
    }

    public void obtener_cod_fam_vector(Integer idLote) {
        LotesDao ld = new LotesDao();
        VectorDao vec = new VectorDao();

        Lote lote = ld.lote_por_id(idLote);
        vector = vec.findByVectorById(lote.getIdVector());

        setCodFamilia(vector.getFamilia().getId());
    }

    public Familia obtener_familia() {
        MuestrasDao md = new MuestrasDao();
        Familia fa = new Familia();
        try {
            if (getCodFamilia() == null) {
                return fa;
            } else {
                fa = md.obtenerFamilia(getCodFamilia());
                setCodOrden(fa.getOrden().getId());
                return fa;
            }
        } catch (Exception x) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, x.toString(), x.toString()));
            return null;
        }
    }

    public Orden obtener_orden() {
        MuestrasDao md = new MuestrasDao();
        Orden o = new Orden();
        try {
            return (getCodOrden() == null) ? o : md.obtenerOrden(getCodOrden());
            //return md.obtenerOrden(getCodOrden());
        } catch (Exception x) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, x.toString(), x.toString()));
            return null;
        }
    }

    /**
     * Metodos solo para asignar valores a variables de este mb, ya que se usan
     * las variables y metodos de otro mb
     */
    public void asignarColoniaCanton(ColoniaCanton cc) {
        caserio.setColoniaCanton(cc);
    }

    public void asignarDepto(String d) {
        setCoddepto(d);
    }

    public void asiganarMuni(String m) {
        setCodmuni(m);
    }

    public void asignarcCanton(Integer cc) {
        setcCanton(cc);
    }

    /**
     * Finaliza asignacion de valores
     */
    private Integer numero_muestras_ingresadas_en_lote(Integer idLote) {
        MuestrasDao mDao = new MuestrasDao();
        try {
            return mDao.numero_muestras_lote(idLote);
        } catch (Exception x) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, x.toString(), x.toString()));
            return null;
        }
    }
    public void asignarUbicacionMuestra_desde_colca(Integer idColcan) {
        try {
            ColoniasDao coDao = new ColoniasDao();
            MunicipioDao mDao = new MunicipioDao();
            DepartamentoDao dDao = new DepartamentoDao();
            
            setUbicacionColonia(coDao.obtenerColoniaCanton_por_id(idColcan));
            setUbicacionMunicipio(mDao.obtener_municipio(getUbicacionColonia().getMunicipio().getCodMunicipio()));
            setUbicacionDepto(dDao.departamento_por_id(getUbicacionMunicipio().getDepartamento().getCodDepto()));
        } catch (Exception ex) {
            //Logger.getLogger(MbMuestrasLotes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void reset_muni_y_col(){
        setUbicacionMunicipio(new Municipio());
        setUbicacionColonia(new ColoniaCanton());
    }
    public void reset_col(){
        setUbicacionColonia(new ColoniaCanton());
    }
    public void modificar_muestra(){
        MuestrasDao mDao = new MuestrasDao();
               
        TipoMuestra tm = new TipoMuestra();
        //BitacoraCampo bc = new BitacoraCampo();
        muestraLote.setSecuencia(getnMuestrasLote());
       
                
        tm.setIdTipoMues(modTipomuestra);
        //bc.setIdBitCampo(3);/*La tabla bitacora_campo, solo tiene un registro con ID 3; Debe modificarse para soportar nulos o buscar otra alternativa, por el momento es obligatorio*/
       
        modMuestra.setTipoMuestra(tm);
       // modMuestra.setBitacoraCampo(bc);
       
        modMuestra.setNomJefeFam("JefeFamilia");
        modMuestra.setFechaTrabajo(new Date());
        modMuestra.setFechaMuestra(new Date());
        modMuestra.setNumeroMuestra("0");/*Este campo seria "innecesario" porque el numero de muestras esta indicado en Lote, a menos que este sea como un correlativo */
   
        
        //muestraLote.setCacerio(ubicacionCaserio);//Se pone el objeto/id del caserio ingresado(hibernate devuelve el ID)

        
        
        mDao.modificar_muestra(modMuestra);

        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dialogoModificar').hide();");
        PrimeFaces.current().ajax().update("muestrasRegistradas");
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Muestra actualizada con Exito"));
        
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
    public ColoniaCanton colonia_por_id(Integer cod) {
        ColoniasDao cDao = new ColoniasDao();
        try {
            return cDao.obtenerColoniaCanton_por_id(cod);
        } catch (Exception x) {
            return new ColoniaCanton();
        }
    }
    public void hacer_codigo_muestra(){
        Lote l = lote_por_id(cod_lote);
        Orden o = obtener_orden();
        asignarUbicacionMuestra_desde_colca(l.getColoniaCanton().getIdColCan());
        String orden="";
        if(o.getId()<10){
            orden = "0"+o.getId();
        }else{
            orden += o.getId();
        }        
        codigo_muestra=""+orden+"-"+ubicacionDepto.getCodDepto()+"-"+ubicacionMunicipio.getCodMunicipio()+"-"+ubicacionColonia.getIdColCan()+"-"+getnMuestrasLote();
    }
    public Lote lote_por_id(Integer idLote){
         LotesDao lotesDao = new LotesDao();
        try {
            return lotesDao.lote_por_id(idLote);
        } catch (Exception x) {
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, x.toString(), x.toString()));
            return new Lote();
        }
    }
}
