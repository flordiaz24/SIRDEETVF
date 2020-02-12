package sv.ues.mbsesion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;
import sv.ues.dao.Actividad01Dao;
import sv.ues.dao.EncabezadoDao;
import sv.ues.dao.RolesDao;
import sv.ues.dominio.Actividad01;
import sv.ues.dominio.ActividadEncabezado;
import sv.ues.dominio.Investigacion;
import sv.ues.dominio.Rol;

/**
 *
 * @author daniel
 */
@ManagedBean(name="MbActividad01")
//@ViewScoped
@SessionScoped
public class MbActividad01  implements Serializable{
    
    /*private Rol roles;
    private List<Rol> lsroles = new ArrayList();
    private Rol selectedRol;*/
    
    private List<ActividadEncabezado> lsencabezados;
    private List<Actividad01> lsactividad01; //= new ArrayList();
    private List<Actividad01> lsactividad01Consultar;
    private ActividadEncabezado actividadEncabezado;
    private ActividadEncabezado actividadEncabezadoConsultar;
    private Actividad01 actividad01;
    private String hashenc;
    private Investigacion investigacion;
    
    public MbActividad01()
    {
        lsactividad01 = new ArrayList();
        lsactividad01Consultar = new ArrayList();
        actividadEncabezado = new ActividadEncabezado();
        actividad01 = new Actividad01();
        actividadEncabezado.setControl(false);
        actividadEncabezado.setPatron(false);
        actividadEncabezado.setTratamiento(false);
        investigacion = null;
    }
    
    public String flujoResgistrar(FlowEvent event) throws Exception 
    {
        if(event.getOldStep().equals("investigacion") && event.getNewStep().equals("datos_generales")&&investigacion == null)
        { 
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","Seleccione una investigacion")); 
                return event.getOldStep();
        }
        
        if(event.getOldStep().equals("datos_actividad") && event.getNewStep().equals("observaciones")&&lsactividad01.size()<=0)
        { 
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Advertencia","Ingrese al menos un registro")); 
                return event.getOldStep();
        }
        return event.getNewStep();
    }
    
    
    public List<ActividadEncabezado> lista_encabezados() throws Exception
    {
        EncabezadoDao encabezadoDao = new EncabezadoDao();
        return encabezadoDao.obtener_menus();
    }
    
    public List<Investigacion> lista_investigaciones() throws Exception
    {
        EncabezadoDao encabezadoDao = new EncabezadoDao();
        return encabezadoDao.obtener_investigaciones();
    }
    
    
    /*public void registrar() throws Exception
    {
        EncabezadoDao encabezadoDao=new EncabezadoDao();
        hashenc = generarString(10)+System.nanoTime();
        actividadEncabezado.setHash(hashenc);
        
        
        
        Set setactividades = new HashSet(0);
        setactividades.addAll(lsactividad01);
        actividadEncabezado.setActividad01s(setactividades);
        
        
        System.out.println("CANTIDAD ACTIVIDADES: "+actividadEncabezado.getActividad01s().size());
        
        encabezadoDao.registrar(actividadEncabezado);
        
        
        
        //actividadEncabezado = encabezadoDao.obtener_actividad_hash(hashenc);
        
        
        //actividadEncabezado.setActividad01s(setactividades);
        //encabezadoDao.modificar(actividadEncabezado);
        
        
        actividadEncabezado = new ActividadEncabezado();
        actividad01 = new Actividad01();
        lsactividad01.clear();
        actividadEncabezado.setControl(false);
        actividadEncabezado.setPatron(false);
        actividadEncabezado.setTratamiento(false);
        PrimeFaces.current().ajax().update("F01");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informacion","Actividad registrada con exito"));
    }*/
    
    
    public void registrar2() throws Exception
    {
      
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        LoginBean neededBean = (LoginBean)facesContext.getApplication().createValueBinding("#{LoginBean}").getValue(facesContext);

        EncabezadoDao encabezadoDao=new EncabezadoDao();
        
        actividadEncabezado.setUsuario(neededBean.getUsuario().getNomUsuario());
        hashenc = generarString(10)+System.nanoTime();
        actividadEncabezado.setHash(hashenc);
        
        
        
        /*Set setactividades = new HashSet(0);
        setactividades.addAll(lsactividad01);
        actividadEncabezado.setActividades(setactividades);*/
        
        actividadEncabezado.setCodigoInvestigacion(""+investigacion.getCodInvest());
        actividadEncabezado.setNombreInvestigacion(investigacion.getNomInvest());
        
        encabezadoDao.registrar(actividadEncabezado);
        
        actividadEncabezado = encabezadoDao.obtener_actividad_hash(hashenc);
        //actividad01.setEncabezado_obj(actividadEncabezado);
        
        Actividad01Dao actividad01Dao = new Actividad01Dao();
        //actividad01Dao.registrar(actividad01);
        
        for(int i = 0; i<lsactividad01.size();i++)
        {
            lsactividad01.get(i).setActividadEncabezado(actividadEncabezado);
            actividad01Dao.registrar(lsactividad01.get(i));
        }
        
        actividadEncabezado = new ActividadEncabezado();
        actividad01 = new Actividad01();
        lsactividad01.clear();
        actividadEncabezado.setControl(false);
        actividadEncabezado.setPatron(false);
        actividadEncabezado.setTratamiento(false);
        
        PrimeFaces.current().ajax().update("F01");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Informacion","Actividad registrada con exito"));
    }

    public void agregar() throws Exception
    {
        try
        {
            actividad01.setPromedio(""+(float)((actividad01.getTomaGlucemia30()+actividad01.getTomaGlucemia60()+actividad01.getTomaGlucemia120()+actividad01.getTomaGlucemia180()))/4);
            actividad01.setIdentAnimal("A"+(lsactividad01.size()+1));
            lsactividad01.add(actividad01);
            actividad01 = new Actividad01();
        }
        catch(Exception x)
        {
        
        }
    }
    
    static String generarString(int n) 
    { 
        String AlphaNumericString = "ABCDEFGHJKMNPRSTUVWXYZ2356789abcdefghjkmnprstuvxyz$#%*+-&.-_"; 

        StringBuilder sb = new StringBuilder(n); 
  
        for (int i = 0; i < n; i++)
        { 

            int index = (int)(AlphaNumericString.length()* Math.random()); 
  
            sb.append(AlphaNumericString.charAt(index)); 
        } 
        return sb.toString(); 
    }
    
    
    
    
    public void remover(Actividad01 r)
    {
        lsactividad01.remove(r);
    }
    
    public ActividadEncabezado getActividadEncabezado() {
        return actividadEncabezado;
    }

    public void setActividadEncabezado(ActividadEncabezado actividadEncabezado) {
        this.actividadEncabezado = actividadEncabezado;
    }

    public List<Actividad01> getLsactividad01() {
        return lsactividad01;
    }

    public void setLsactividad01(List<Actividad01> lsactividad01) {
        this.lsactividad01 = lsactividad01;
    }

    public Actividad01 getActividad01() {
        return actividad01;
    }

    public void setActividad01(Actividad01 actividad01) {
        this.actividad01 = actividad01;
    }

    public List<ActividadEncabezado> getLsencabezados() {
        return lsencabezados;
    }

    public void setLsencabezados(List<ActividadEncabezado> lsencabezados) {
        this.lsencabezados = lsencabezados;
    }

    public String getHashenc() {
        return hashenc;
    }

    public void setHashenc(String hashenc) {
        this.hashenc = hashenc;
    }

    public Investigacion getInvestigacion() {
        return investigacion;
    }

    public void setInvestigacion(Investigacion investigacion) {
        this.investigacion = investigacion;
    }

    public ActividadEncabezado getActividadEncabezadoConsultar() {
        return actividadEncabezadoConsultar;
    }

    public void setActividadEncabezadoConsultar(ActividadEncabezado actividadEncabezadoConsultar) {
        this.actividadEncabezadoConsultar = actividadEncabezadoConsultar;
        
        
        lsactividad01Consultar = new ArrayList();
        Iterator iterator =  this.actividadEncabezadoConsultar.getActividad01s().iterator();
            while(iterator.hasNext()){
              lsactividad01Consultar.add((Actividad01) iterator.next());// = (String) ;
            }
            System.out.println("PASA!!!!!!!!!!!!!!");
    }

    public List<Actividad01> getLsactividad01Consultar() {
        return lsactividad01Consultar;
    }

    public void setLsactividad01Consultar(List<Actividad01> lsactividad01Consultar) {
        this.lsactividad01Consultar = lsactividad01Consultar;
    }

}
