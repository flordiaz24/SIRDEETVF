/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.dao;

import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sv.ues.dominio.Laboratorio;
import sv.ues.utils.HibernateUtil;

/**
 *
 * @author PC
 */
public class LaboratorioDao {
    private HibernateUtil hibernateUtil = new HibernateUtil();
    private SessionFactory sessionFactory = hibernateUtil. getSessionFactory();
    private Session sesion;
    private Transaction tx;
    
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
    
    public List<Laboratorio> getLaboratorio()
    {
        
      try{
        iniciaOperacion();
        CriteriaQuery criteria = sesion.getCriteriaBuilder().createQuery(Laboratorio.class);//Roles  .class);
        criteria.from(Laboratorio.class);
        List<Laboratorio> laboratorio = sesion.createQuery(criteria).getResultList();
        //sesion.close();
        return laboratorio;
        }catch(HibernateException e){
            throw e;
        }finally{
        sesion.close();
        
        }
    }
    
}
