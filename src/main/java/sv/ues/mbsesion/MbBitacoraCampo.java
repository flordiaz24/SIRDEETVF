/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.mbsesion;

/**
 *
 * @author Daniel
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;

import sv.ues.dao.BitacoraCampoDao;
import sv.ues.dao.ColoniaCantonDao;
import sv.ues.dao.DepartamentoDao;

import sv.ues.dao.InvVectorDao;
import sv.ues.dao.MunicipioDao;

import sv.ues.dao.TipoMuestraDao;
import sv.ues.dominio.BitacoraCampo;
import sv.ues.dominio.ColoniaCanton;
import sv.ues.dominio.Departamento;

import sv.ues.dominio.InvVector;

import sv.ues.dominio.Muestra;
import sv.ues.dominio.Municipio;
import sv.ues.dominio.TipoMuestra;
import sv.ues.dominio.Ubicacion;

@ManagedBean
@ViewScoped
public class MbBitacoraCampo implements Serializable {

    private BitacoraCampo bitacoraCampo = new BitacoraCampo();
    private List<Muestra> lsMuestras ;
    private List<Ubicacion> lsBitacora = new ArrayList<Ubicacion>();
    private List<InvVector> lsInvVector = new ArrayList<InvVector>();
    private Ubicacion ubicacion = new Ubicacion();
    private Muestra muestra = new Muestra();
    private List<TipoMuestra> lsTipoMuestras = new ArrayList<TipoMuestra>();
    private List<SelectItem> items_municipio;
    private List<SelectItem> items_departamento;
    private List<SelectItem> items_colonia;
    private List<ColoniaCanton> lsColoniaCanton;
    private List<BitacoraCampo> lsBitacoraCampo;
    private String codigodepartamento = "";
    private String codigomunicipio = "";
    private String codigocolonia = "";
    private Municipio municipio;
    private MunicipioDao municipioDao;
    private ColoniaCantonDao coloniaCantonDao;
    private ColoniaCanton coloniaCanton;
    private int secuencia = 1;
    private Set ubications;

    public List<ColoniaCanton> getLsColoniaCanton() {
        return lsColoniaCanton;
    }

    public void setLsColoniaCanton(List<ColoniaCanton> lsColoniaCanton) {
        this.lsColoniaCanton = lsColoniaCanton;
    }

    public MbBitacoraCampo() {
        this.ubications=new HashSet(0);
        bitacoraCampo = new BitacoraCampo();
        this.municipioDao = new MunicipioDao();
        this.coloniaCantonDao = new ColoniaCantonDao();
        municipio = new Municipio();
        ubicacion = new Ubicacion();
        coloniaCanton = new ColoniaCanton();
        this.items_departamento = new ArrayList();
        this.items_colonia = new ArrayList<SelectItem>();
        this.items_municipio = new ArrayList();
        this.lsMuestras= new ArrayList<Muestra>();
        codigodepartamento = "";
        codigomunicipio = "";
        this.codigocolonia = "";
        this.lsBitacoraCampo=new ArrayList();
    }

    public BitacoraCampo getBitacoraCampo() {
        return bitacoraCampo;
    }

    public void setBitacoraCampo(BitacoraCampo bitacoraCampo) {
        this.bitacoraCampo = bitacoraCampo;
    }

    public List<Muestra> getLsMuestras() {
        return lsMuestras;
    }

    public void setLsMuestras(List<Muestra> lsMuestras) {
        this.lsMuestras = lsMuestras;
    }

    public Muestra getMuestra() {
        return muestra;
    }

    public void setMuestra(Muestra muestra) {
        this.muestra = muestra;
    }

    public List<TipoMuestra> getLsTipoMuestras() {
        TipoMuestraDao tipoMuestraDao = new TipoMuestraDao();
        return lsTipoMuestras = tipoMuestraDao.getTiposMuestras();
    }

    public void setLsTipoMuestras(List<TipoMuestra> lsTipoMuestras) {
        this.lsTipoMuestras = lsTipoMuestras;
    }

    public List<InvVector> getLsInvVector() {
        InvVectorDao invDao = new InvVectorDao();
        return lsInvVector = invDao.getListInvVector();
    }

    public void setLsInvVector(List<InvVector> lsInvVector) {
        this.lsInvVector = lsInvVector;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<SelectItem> getItems_colonia() {
        this.items_colonia = new ArrayList();
        ColoniaCantonDao coloniaDao = new ColoniaCantonDao();
        Municipio municipio_seleccionado = new Municipio();
        municipio_seleccionado.setCodMunicipio(codigomunicipio);
        List<ColoniaCanton> lista_colonia_por_muni = coloniaDao.obtenerColoniaByMunicipio(municipio_seleccionado);
        this.items_colonia.clear();
        for (ColoniaCanton col : lista_colonia_por_muni) {
            SelectItem item = new SelectItem(col.getIdColCan(), col.getNomUbicacion());
            this.items_colonia.add(item);
        }

        return items_colonia;
    }

    public void setItems_colonia(List<SelectItem> items_colonia) {
        this.items_colonia = items_colonia;
    }

    public List<SelectItem> getItems_municipio() throws Exception {
        this.items_municipio = new ArrayList();
        MunicipioDao municipios = new MunicipioDao();
        Departamento departamento_seleccionado = new Departamento();
        departamento_seleccionado.setCodDepto(codigodepartamento);
        List<Municipio> lista_municipios_por_dpto = municipios.obtener_municipios_por_id_del_departamento(departamento_seleccionado);
        this.items_municipio.clear();
        for (Municipio muni : lista_municipios_por_dpto) {
            SelectItem item = new SelectItem(muni.getCodMunicipio(), muni.getNomMunicipio());
            this.items_municipio.add(item);
        }
        return items_municipio;
    }

    public void setItems_municipio(List<SelectItem> items_municipio) {
        this.items_municipio = items_municipio;
    }

    public List<SelectItem> getItems_departamento() throws Exception {
        this.items_departamento = new ArrayList();
        DepartamentoDao departamentos = new DepartamentoDao();
        List<Departamento> lista_departamentos = departamentos.obtener_todos_los_departamentos();
        this.items_departamento.clear();
        for (Departamento dep : lista_departamentos) {
            SelectItem item = new SelectItem(dep.getCodDepto(), dep.getNomDepto());
            this.items_departamento.add(item);
        }
        return items_departamento;
    }

    public void setItems_departamento(List<SelectItem> items_departamento) {
        this.items_departamento = items_departamento;
    }

    public String getCodigodepartamento() {
        return codigodepartamento;
    }

    public void setCodigodepartamento(String codigodepartamento) {
        this.codigodepartamento = codigodepartamento;
    }

    public String getCodigomunicipio() {
        return codigomunicipio;
    }

    public void setCodigomunicipio(String codigomunicipio) {
        this.codigomunicipio = codigomunicipio;
    }

    public void resetCodigoMunicipio() {
        codigomunicipio = "";
        codigocolonia = "";
    }

    public void asignarMunicipio() throws Exception {
        this.municipio = this.municipioDao.obtener_municipio(this.codigomunicipio);
        if (this.municipio != null) {
            this.codigomunicipio = this.municipio.getCodMunicipio();
        } else {
            this.codigomunicipio = "";
        }
    }

    public void asignarColoniaCanton() throws Exception {
        this.coloniaCanton = this.coloniaCantonDao.obtenerCantones(this.codigocolonia);
        if (this.coloniaCanton != null) {
            this.codigocolonia = String.valueOf(this.coloniaCanton.getIdColCan());
        } else {
            this.codigocolonia = "";
        }
    }

    public String getCodigocolonia() {
        return codigocolonia;
    }

    public void setCodigocolonia(String codigocolonia) {
        this.codigocolonia = codigocolonia;
    }

    public void agregarMuestra() {
        try {
            //validamos campos
            if (muestra.getCodigoMuestra().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ingrese codigo de muestra"));
            } else {
                if (muestra.getEdadMuestra() == 0) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ingrese edad de la persona"));
                } else {
                    if (muestra.getFechaMuestra() == null) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ingrese fecha de muestra"));
                    } else {
                        if (muestra.getCodigoMuestra().isEmpty()) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ingrese codigo de muestra"));
                        } else {
                            if (muestra.getTelefonoMuestra().isEmpty()) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ingrese telefono familia"));
                            } else {
                                if (muestra.getNomJefeFam().isEmpty()) {
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ingrese nombre jefe de familia"));
                                } else {
                                    if (muestra.getTipoMuestra() == null) {
                                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Seleccione tipo de muestra"));
                                    } else {
                                        muestra.setBitacoraCampo(bitacoraCampo);
                                        muestra.setFechaTrabajo(new Date());
                                        if(lsMuestras.contains(muestra)){
                                            System.out.println("duplicado no se ingresara");
                                        }else{
                                            if(lsMuestras.isEmpty()){
                                                 secuencia = 1;
                                            muestra.setSecuencia(secuencia);
                                            lsMuestras.add(muestra);
                                            String secuencuaAnt=muestra.getCodigoMuestra();
                                            muestra=new Muestra();
                                            muestra.setCodigoMuestra(secuencuaAnt);
                                            }else{
                                                int year=Calendar.getInstance().get(Calendar.YEAR);
                                             muestra.setCodigoMuestra(ubicacion.getCodDepto()+ubicacion.getCodMun()+ubicacion.getCodCanton()+secuencia+year);
                                            secuencia = secuencia + 1;
                                            muestra.setSecuencia(secuencia);
                                             String secuencuaAnt=muestra.getCodigoMuestra();
                                            muestra=new Muestra();
                                            muestra.setCodigoMuestra(secuencuaAnt);
                                            }
                                        }
                                        
                                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Datos ingresados correctamente"));
                                    }

                                }
                            }
                        }
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al agregar elemento en bean mbbitacoracampo");
        }

    }

    public void registrar() throws Exception {
        //  try {
        bitacoraCampo.setMuestras(lsMuestras);
        BitacoraCampoDao bitacoraCampoDao = new BitacoraCampoDao();
        bitacoraCampo.setFechaCampo(new Date());
        ubicacion.setBitacoraCampo(bitacoraCampo);
        ubicacion.setCodDepto(codigodepartamento);
        ubicacion.setCodMun(codigomunicipio);
        ubicacion.setCodCanton(codigocolonia);
        this.ubications.add(this.ubicacion);
        bitacoraCampo.setUbicacions(ubications);
        bitacoraCampoDao.registrar(bitacoraCampo);

        bitacoraCampo = new BitacoraCampo();
        lsMuestras.clear();
        muestra = new Muestra();
        PrimeFaces.current().ajax().update("F01");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Infomracion", "Datos registrados correctamente"));
      
    }

    public String flujoResgistrar(FlowEvent event) throws Exception {
        String cadena_validadora = "";
        //VALIDACION AVANZAR PERSONAL -> DOMICILIO
        if (event.getOldStep().equals("principal") && event.getNewStep().equals("muestra")) {
              //verificamos el codigo municipio departamento y colonia para llenar los datos de la muestra
              //el codigo sera ddepart, muni, colonia y año
              ubicacion.setCodDepto(codigodepartamento);
              ubicacion.setCodMun(codigomunicipio);
              ubicacion.setCodCanton(codigocolonia);
              ubicacion.setBitacoraCampo(bitacoraCampo);
              int year=Calendar.getInstance().get(Calendar.YEAR);
              muestra.setCodigoMuestra(ubicacion.getCodDepto()+ubicacion.getCodMun()+ubicacion.getCodCanton()+secuencia+year);
              
        }
        
        return event.getNewStep();
    }

    
    
    
    
}
