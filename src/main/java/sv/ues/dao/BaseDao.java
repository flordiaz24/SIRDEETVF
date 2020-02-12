/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.dao;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sv.ues.utils.HibernateUtil;
/**
 *
 * @author PC
 */
public class BaseDao {
      protected static HibernateUtil hibernateUtil = new HibernateUtil();
    protected static SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
    protected Session sesion;
    protected Transaction tx;
    
    /**
     * Inicia la session para obtener la conexion
     * @throws HibernateException Captura un error en caso de ocurrir
     */
    protected void iniciar() throws HibernateException {
        sesion = null;
        tx = null;
        try {
            if (sessionFactory.getCurrentSession()== null) {
                sesion = sessionFactory.openSession();
            }else{
                sesion = sessionFactory.getCurrentSession();
            }
            tx = sesion.beginTransaction();
        } catch (Throwable t) {
            System.err.println("Error al obtener sesion. ");
            t.printStackTrace();
        }
    }
    
    /**
     * Cierra sesion y hace commit a los cambios
     */
    protected void completado(){
        sesion.getTransaction().commit();
        cerrar();
    }
    
    /**
     * Solamente cierra la session
     */
    protected void cerrar(){
        sesion.close();
    }
    
    private void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("Ocurrio un error en la capa DAO", he);
    }
}
