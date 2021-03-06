package sv.ues.dominio;
// Generated 01-24-2020 08:44:22 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * ActividadEncabezado generated by hbm2java
 */
public class ActividadEncabezado  implements java.io.Serializable {


     private int idEncabezado;
     private String hash;
     private Boolean buena;
     private Boolean centinela;
     private String cepa;
     private String concentracionDeDosis;
     private Boolean control;
     private String control1;
     private String descripcion;
     private String edad;
     private String edadInicial;
     private String especie;
     private Date fecha;
     private Date fechaDeNecropcia;
     private Date fechaFinal;
     private Date fechaInicio;
     private String grupo;
     private String lapsoDeTiempo;
     private Boolean mala;
     private String metodoEutanasico;
     private String NDelLosAnimales;
     private String NJaula;
     private Boolean no;
     private Boolean noProgramada;
     private String observacionesEnc;
     private String observacionesPie;
     private Boolean patron;
     private String patron2;
     private String periodo;
     private String peso;
     private Boolean programada;
     private String protocolo;
     private Boolean regular;
     private String responsableDePrueba;
     private String semanaN;
     private String sexo;
     private Boolean si;
     private String sustanciaDeEnsayo;
     private String tiempoDeExposicion;
     private String tiempoDeObservacion;
     private Boolean tratamiento;
     private String tratamiento3;
     private String tratamiento4;
     private String tratamiento5;
     private String tratamiento6;
     private String viaDeAdministracion;
     private String nombreInvestigacion;
     private String codigoInvestigacion;
     private Date fechaRegistro;
     private Set actividad01s = new HashSet(0);
     private String usuario;

    public ActividadEncabezado() {
    }

	
    public ActividadEncabezado(int idEncabezado) {
        this.idEncabezado = idEncabezado;
    }
    public ActividadEncabezado(int idEncabezado, String hash, Boolean buena, Boolean centinela, String cepa, String concentracionDeDosis, Boolean control, String control1, String descripcion, String edad, String edadInicial, String especie, Date fecha, Date fechaDeNecropcia, Date fechaFinal, Date fechaInicio, String grupo, String lapsoDeTiempo, Boolean mala, String metodoEutanasico, String NDelLosAnimales, String NJaula, Boolean no, Boolean noProgramada, String observacionesEnc, String observacionesPie, Boolean patron, String patron2, String periodo, String peso, Boolean programada, String protocolo, Boolean regular, String responsableDePrueba, String semanaN, String sexo, Boolean si, String sustanciaDeEnsayo, String tiempoDeExposicion, String tiempoDeObservacion, Boolean tratamiento, String tratamiento3, String tratamiento4, String tratamiento5, String tratamiento6, String viaDeAdministracion, Date fechaRegistro, Set actividad01s) {
       this.idEncabezado = idEncabezado;
       this.hash = hash;
       this.buena = buena;
       this.centinela = centinela;
       this.cepa = cepa;
       this.concentracionDeDosis = concentracionDeDosis;
       this.control = control;
       this.control1 = control1;
       this.descripcion = descripcion;
       this.edad = edad;
       this.edadInicial = edadInicial;
       this.especie = especie;
       this.fecha = fecha;
       this.fechaDeNecropcia = fechaDeNecropcia;
       this.fechaFinal = fechaFinal;
       this.fechaInicio = fechaInicio;
       this.grupo = grupo;
       this.lapsoDeTiempo = lapsoDeTiempo;
       this.mala = mala;
       this.metodoEutanasico = metodoEutanasico;
       this.NDelLosAnimales = NDelLosAnimales;
       this.NJaula = NJaula;
       this.no = no;
       this.noProgramada = noProgramada;
       this.observacionesEnc = observacionesEnc;
       this.observacionesPie = observacionesPie;
       this.patron = patron;
       this.patron2 = patron2;
       this.periodo = periodo;
       this.peso = peso;
       this.programada = programada;
       this.protocolo = protocolo;
       this.regular = regular;
       this.responsableDePrueba = responsableDePrueba;
       this.semanaN = semanaN;
       this.sexo = sexo;
       this.si = si;
       this.sustanciaDeEnsayo = sustanciaDeEnsayo;
       this.tiempoDeExposicion = tiempoDeExposicion;
       this.tiempoDeObservacion = tiempoDeObservacion;
       this.tratamiento = tratamiento;
       this.tratamiento3 = tratamiento3;
       this.tratamiento4 = tratamiento4;
       this.tratamiento5 = tratamiento5;
       this.tratamiento6 = tratamiento6;
       this.viaDeAdministracion = viaDeAdministracion;
       this.fechaRegistro = fechaRegistro;
       this.actividad01s = actividad01s;
    }
   
    public int getIdEncabezado() {
        return this.idEncabezado;
    }
    
    public void setIdEncabezado(int idEncabezado) {
        this.idEncabezado = idEncabezado;
    }
    public String getHash() {
        return this.hash;
    }
    
    public void setHash(String hash) {
        this.hash = hash;
    }
    public Boolean getBuena() {
        return this.buena;
    }
    
    public void setBuena(Boolean buena) {
        this.buena = buena;
    }
    public Boolean getCentinela() {
        return this.centinela;
    }
    
    public void setCentinela(Boolean centinela) {
        this.centinela = centinela;
    }
    public String getCepa() {
        return this.cepa;
    }
    
    public void setCepa(String cepa) {
        this.cepa = cepa;
    }
    public String getConcentracionDeDosis() {
        return this.concentracionDeDosis;
    }
    
    public void setConcentracionDeDosis(String concentracionDeDosis) {
        this.concentracionDeDosis = concentracionDeDosis;
    }
    public Boolean getControl() {
        return this.control;
    }
    
    public void setControl(Boolean control) {
        this.control = control;
    }
    public String getControl1() {
        return this.control1;
    }
    
    public void setControl1(String control1) {
        this.control1 = control1;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getEdad() {
        return this.edad;
    }
    
    public void setEdad(String edad) {
        this.edad = edad;
    }
    public String getEdadInicial() {
        return this.edadInicial;
    }
    
    public void setEdadInicial(String edadInicial) {
        this.edadInicial = edadInicial;
    }
    public String getEspecie() {
        return this.especie;
    }
    
    public void setEspecie(String especie) {
        this.especie = especie;
    }
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public Date getFechaDeNecropcia() {
        return this.fechaDeNecropcia;
    }
    
    public void setFechaDeNecropcia(Date fechaDeNecropcia) {
        this.fechaDeNecropcia = fechaDeNecropcia;
    }
    public Date getFechaFinal() {
        return this.fechaFinal;
    }
    
    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }
    public Date getFechaInicio() {
        return this.fechaInicio;
    }
    
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public String getGrupo() {
        return this.grupo;
    }
    
    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }
    public String getLapsoDeTiempo() {
        return this.lapsoDeTiempo;
    }
    
    public void setLapsoDeTiempo(String lapsoDeTiempo) {
        this.lapsoDeTiempo = lapsoDeTiempo;
    }
    public Boolean getMala() {
        return this.mala;
    }
    
    public void setMala(Boolean mala) {
        this.mala = mala;
    }
    public String getMetodoEutanasico() {
        return this.metodoEutanasico;
    }
    
    public void setMetodoEutanasico(String metodoEutanasico) {
        this.metodoEutanasico = metodoEutanasico;
    }
    public String getNDelLosAnimales() {
        return this.NDelLosAnimales;
    }
    
    public void setNDelLosAnimales(String NDelLosAnimales) {
        this.NDelLosAnimales = NDelLosAnimales;
    }
    public String getNJaula() {
        return this.NJaula;
    }
    
    public void setNJaula(String NJaula) {
        this.NJaula = NJaula;
    }
    public Boolean getNo() {
        return this.no;
    }
    
    public void setNo(Boolean no) {
        this.no = no;
    }
    public Boolean getNoProgramada() {
        return this.noProgramada;
    }
    
    public void setNoProgramada(Boolean noProgramada) {
        this.noProgramada = noProgramada;
    }
    public String getObservacionesEnc() {
        return this.observacionesEnc;
    }
    
    public void setObservacionesEnc(String observacionesEnc) {
        this.observacionesEnc = observacionesEnc;
    }
    public String getObservacionesPie() {
        return this.observacionesPie;
    }
    
    public void setObservacionesPie(String observacionesPie) {
        this.observacionesPie = observacionesPie;
    }
    public Boolean getPatron() {
        return this.patron;
    }
    
    public void setPatron(Boolean patron) {
        this.patron = patron;
    }
    public String getPatron2() {
        return this.patron2;
    }
    
    public void setPatron2(String patron2) {
        this.patron2 = patron2;
    }
    public String getPeriodo() {
        return this.periodo;
    }
    
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    public String getPeso() {
        return this.peso;
    }
    
    public void setPeso(String peso) {
        this.peso = peso;
    }
    public Boolean getProgramada() {
        return this.programada;
    }
    
    public void setProgramada(Boolean programada) {
        this.programada = programada;
    }
    public String getProtocolo() {
        return this.protocolo;
    }
    
    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }
    public Boolean getRegular() {
        return this.regular;
    }
    
    public void setRegular(Boolean regular) {
        this.regular = regular;
    }
    public String getResponsableDePrueba() {
        return this.responsableDePrueba;
    }
    
    public void setResponsableDePrueba(String responsableDePrueba) {
        this.responsableDePrueba = responsableDePrueba;
    }
    public String getSemanaN() {
        return this.semanaN;
    }
    
    public void setSemanaN(String semanaN) {
        this.semanaN = semanaN;
    }
    public String getSexo() {
        return this.sexo;
    }
    
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    public Boolean getSi() {
        return this.si;
    }
    
    public void setSi(Boolean si) {
        this.si = si;
    }
    public String getSustanciaDeEnsayo() {
        return this.sustanciaDeEnsayo;
    }
    
    public void setSustanciaDeEnsayo(String sustanciaDeEnsayo) {
        this.sustanciaDeEnsayo = sustanciaDeEnsayo;
    }
    public String getTiempoDeExposicion() {
        return this.tiempoDeExposicion;
    }
    
    public void setTiempoDeExposicion(String tiempoDeExposicion) {
        this.tiempoDeExposicion = tiempoDeExposicion;
    }
    public String getTiempoDeObservacion() {
        return this.tiempoDeObservacion;
    }
    
    public void setTiempoDeObservacion(String tiempoDeObservacion) {
        this.tiempoDeObservacion = tiempoDeObservacion;
    }
    public Boolean getTratamiento() {
        return this.tratamiento;
    }
    
    public void setTratamiento(Boolean tratamiento) {
        this.tratamiento = tratamiento;
    }
    public String getTratamiento3() {
        return this.tratamiento3;
    }
    
    public void setTratamiento3(String tratamiento3) {
        this.tratamiento3 = tratamiento3;
    }
    public String getTratamiento4() {
        return this.tratamiento4;
    }
    
    public void setTratamiento4(String tratamiento4) {
        this.tratamiento4 = tratamiento4;
    }
    public String getTratamiento5() {
        return this.tratamiento5;
    }
    
    public void setTratamiento5(String tratamiento5) {
        this.tratamiento5 = tratamiento5;
    }
    public String getTratamiento6() {
        return this.tratamiento6;
    }
    
    public void setTratamiento6(String tratamiento6) {
        this.tratamiento6 = tratamiento6;
    }
    public String getViaDeAdministracion() {
        return this.viaDeAdministracion;
    }
    
    public void setViaDeAdministracion(String viaDeAdministracion) {
        this.viaDeAdministracion = viaDeAdministracion;
    }
    public Date getFechaRegistro() {
        return this.fechaRegistro;
    }
    
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    public Set getActividad01s() {
        return this.actividad01s;
    }
    
    public void setActividad01s(Set actividad01s) {
        this.actividad01s = actividad01s;
    }

    public String getNombreInvestigacion() {
        return nombreInvestigacion;
    }

    public void setNombreInvestigacion(String nombreInvestigacion) {
        this.nombreInvestigacion = nombreInvestigacion;
    }

    public String getCodigoInvestigacion() {
        return codigoInvestigacion;
    }

    public void setCodigoInvestigacion(String codigoInvestigacion) {
        this.codigoInvestigacion = codigoInvestigacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    




}


