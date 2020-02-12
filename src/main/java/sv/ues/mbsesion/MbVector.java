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
import sv.ues.dao.VectorDao;
import sv.ues.dominio.Vector;
/**
 *
 * @author PC
 */
@ManagedBean
@ViewScoped
public class MbVector implements Serializable{
    
    /**
     * Creates a new instance of MbVector
     */
    public MbVector() {
    }
    
    public List<Vector> lista_vector()
    {
        List<Vector> listaVector = null;
        VectorDao vector = new VectorDao();
        try
        {
            listaVector = vector.obtenerListado();
            //System.out.println("\nLista.actividad: "+lista.get(0).getActividad());
        }
        catch(Exception e)
        {
            System.out.println("\nBitacora excepcion... "+e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,e.toString(),e.toString()));
        }
        return listaVector;
    }
    
    /////////////////////////////INICIO_PARA_LOTES//////////////////////////////
    /**
     * Metodo que lista los vectores que estan presentes en investigaciones
     * activas
     *
     * @return lista de objetos de tipo Vector
     */
    public List<Vector> lista_vectores_invest_activas() {
        VectorDao vec_dao = new VectorDao();
        try {
            return vec_dao.listar_vectores_invest_activas();
        } catch (Exception x) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, x.toString(), x.toString()));
            return null;
        }
    }

    /**
     * Devuelve el nombre de un vector segun ID
     *
     * @param id Integer ID del vector
     * @return String Nombre del vector
     */
    public String muestra_vector_por_ID(Integer id) {
        VectorDao vec_dao = new VectorDao();
        try {
            Vector x=vec_dao.findByVectorById(id);
            String xz=x.getNomVector();
            return xz;
        } catch (Exception x) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, x.toString(), x.toString()));
            return null;
        }
    }
    /////////////////////////////////FIN_PARA_LOTES/////////////////////////////
}
