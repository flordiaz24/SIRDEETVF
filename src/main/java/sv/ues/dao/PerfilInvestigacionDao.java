/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.dao;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import sv.ues.dominio.PerfilInv;

import sv.ues.utils.HibernateUtil;

/**
 *
 * @author PC
 */
public class PerfilInvestigacionDao {
     private HibernateUtil hibernateUtil = new HibernateUtil();
    private SessionFactory sessionFactory = hibernateUtil. getSessionFactory();
    private Session sesion;
    private Transaction tx;
    private Logger LOGGER;
    
    
    private void iniciaOperacion() throws HibernateException 
    {
	sesion = sessionFactory.openSession() ;
	tx = sesion.beginTransaction() ;
    }
    
    //excepcion
    private void manejaExcepcion(HibernateException he) throws HibernateException 
    {
	tx.rollback();
	throw new HibernateException( "Ocurrió un error en la capa DAO" , he);
    }
    
     //insertar una nueva investacion
    public void registrar(PerfilInv perfilInv) throws Exception {
      
        try {
            iniciaOperacion();
            sesion.save(perfilInv);
            sesion.flush();
            tx.commit();

        } catch (HibernateException he) {
            tx.rollback();
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }
    //obtener todas las investigaciones
    public List<PerfilInv> obtenerPerfilesInv() throws Exception {
        try{
        iniciaOperacion();
        CriteriaQuery criteria = sesion.getCriteriaBuilder().createQuery(PerfilInv.class);
        criteria.from(PerfilInv.class);
        List<PerfilInv> perfilInves = sesion.createQuery(criteria).getResultList();
        return perfilInves;
        }catch(HibernateException e){
            throw e;
        }finally{
        sesion.close();
        
        }
    }
    
    
  
    
    
    //modificar una investifacion
    
     public void actualizarPerfilInv(PerfilInv perfilInv) throws Exception {
        
        try {
            iniciaOperacion();
            sesion.update(perfilInv);
            sesion.flush();
            tx.commit();

        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }
    
        
    
    //obtener perfil de investigacion de acuerdo a id
    public PerfilInv getPerfilInvById(int id){
        try{
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            
            CriteriaQuery<PerfilInv> query = builder.createQuery(PerfilInv.class);
            Root<PerfilInv> root = query.from(PerfilInv.class);
             query.select(root).where(builder.equal(root.get("idPerfil"), id));
            Query<PerfilInv> q =sesion.createQuery(query);
            return q.getSingleResult();
        }catch(Exception e){
            LOGGER.log(Level.WARNING,"exception en investigacion Dao getInvestigacionById",e);
            return null;
        }finally{
            sesion.close();
        }
    }
    
    
    
    
}
