/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.dao;


import javax.persistence.criteria.CriteriaBuilder;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import sv.ues.dominio.Vector;
import sv.ues.utils.HibernateUtil;

/**
 *
 * @author PC
 */

public class VectorDao  {
    private HibernateUtil hibernateUtil = new HibernateUtil();
    private SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
    private Session sesion;
    private Transaction tx;

    private void iniciaOperacion() throws HibernateException {
        sesion = null;
        tx=null;
        try {
            //verificaremos si la conexion no esta a
            sesion = sessionFactory.openSession();
            tx = sesion.beginTransaction();
        }catch(Throwable t){
            System.err.println("Exception while getting session.. ");
            t.printStackTrace();
        }

    }
    
    
    public Vector findByVectorById(int id){
        try{
             iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            
            CriteriaQuery<Vector> query = builder.createQuery(Vector.class);
            Root<Vector> root = query.from(Vector.class);
             query.select(root).where(builder.equal(root.get("codVector"), id));
            Query<Vector> q =sesion.createQuery(query);
            return q.getSingleResult();
        }catch(HibernateException e){
            throw e;
        }finally{
            sesion.close();
        }
    }
    
    //listar todos los vectores
    public List<Vector> getVectores()
    {
        
        iniciaOperacion();
        CriteriaQuery builder = sesion.getCriteriaBuilder().createQuery(Vector.class);
        builder.from(Vector.class);
        List<Vector> lsVector = sesion.createQuery(builder).getResultList();
        sesion.close();
        return lsVector;
    }
    
    
    public List<Vector> obtenerListado() {
        List<Vector> vect = null;
        Root<Vector> from;
        CriteriaQuery<Object> select;
        CriteriaQuery criteria;
        try {
            this.iniciaOperacion();
            criteria = sesion.getCriteriaBuilder().createQuery(Vector.class);
            from = criteria.from(Vector.class);
            //Obtenemos los valores
            select = criteria.select(from);

            //Devolviendo resultados
            vect = sesion.createQuery(criteria).getResultList();
            vect.size();
        } catch (HibernateException e) {
            System.out.println("Error en BitacoraLaboratorioDao: " + e);
            //throw e;
        } 
        
        return vect;
    }
    /**
     * Devuelve una lista de los vectores que pertenecen a las investigaciones
     * activas
     *
     * @return Lista de Vector
     */
    public List<Vector> listar_vectores_invest_activas() {
        try {
            iniciaOperacion();
            List<Vector> lista = null;

            //ESTAS TRES LINEAS DEBERAN SER REEMPLAZADAS PARA QUE FILTRE SOLO LOS
            //VECTORES QUE PERTENECEN A INVESTIGACIONES ACTIVAS, POR EL MOMENTO
            //DEVUELVE TODOS LOS VECTORES
            CriteriaQuery criteria = sesion.getCriteriaBuilder().createQuery(Vector.class);
            criteria.from(Vector.class);
            lista = sesion.createQuery(criteria).getResultList();

            return lista;
        } catch (HibernateException e) {
            throw e;
        } finally {
            //Confirma transacción
            tx.commit();
            //siempre cierra la sesion
            sesion.close();
        }
    }


}
