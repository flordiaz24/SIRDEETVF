/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.dao;


import java.util.List;
import java.util.logging.Logger;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import sv.ues.dominio.BitacoraCampo;

import sv.ues.utils.HibernateUtil;

/**
 *
 * @author Daniel
 */
public class BitacoraCampoDao {
    private HibernateUtil hibernateUtil = new HibernateUtil();
    private SessionFactory sessionFactory = hibernateUtil. getSessionFactory();
    private Session sesion;
    private Transaction tx;
    
     private static final Logger LOGGER = Logger.getLogger(InvestigacionDao.class.getName());
    
    private void iniciaOperacion() throws HibernateException 
    {
	sesion = sessionFactory.openSession() ;
	tx = sesion.beginTransaction() ;
    }
    
    private void manejaExcepcion(HibernateException he) throws HibernateException 
    {
	tx.rollback();
	throw new HibernateException( "Ocurrió un error en la capa DAO" , he);
    }
    
   
    
     public void registrar(BitacoraCampo bitacoraCampo) throws Exception {
      
        try {
            iniciaOperacion();
            sesion.save(bitacoraCampo);
            //sesion.flush();
            tx.commit();

        } catch (HibernateException he) {
            tx.rollback();
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }
     
     
       public void modificar(BitacoraCampo bitacoraCampo) throws Exception 
    {
        try 
        {
            iniciaOperacion();
            sesion.update(bitacoraCampo);
            sesion.flush();
            tx.commit();
        } 
        catch (HibernateException he) 
        {
            tx.rollback();
            manejaExcepcion(he);
            throw he;
        } 
        finally 
        {
            sesion.close();
        }
    }
       
       public List<BitacoraCampo> obtenerBitacora() throws Exception {
        try{
        iniciaOperacion();
        CriteriaQuery criteria = sesion.getCriteriaBuilder().createQuery(BitacoraCampo.class);//Roles  .class);
        criteria.from(BitacoraCampo.class);
        List<BitacoraCampo> lsBitacoraCampo = sesion.createQuery(criteria).getResultList();
        //sesion.close();
        return lsBitacoraCampo;
        }catch(HibernateException e){
            throw e;
        }finally{
        sesion.close();
        
        }
    }
}
