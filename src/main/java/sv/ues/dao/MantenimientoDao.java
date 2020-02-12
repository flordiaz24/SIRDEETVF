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
import sv.ues.dominio.Lote;
import sv.ues.dominio.Mantenimiento;
import sv.ues.utils.HibernateUtil;

/**
 *
 * @author omni
 */
public class MantenimientoDao {
    
       private HibernateUtil hibernateUtil = new HibernateUtil();
    private SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
    private Session sesion;
    private Transaction tx;

    private void iniciaOperacion() throws HibernateException {
        sesion = null;
        tx = null;
        try {
            //verificaremos si la conexion no esta a
            sesion = sessionFactory.openSession();
            tx = sesion.beginTransaction();
        } catch (Throwable t) {
            System.err.println("Exception while getting session.. ");
            t.printStackTrace();
        }

    }

    private void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("Ocurrió un error en la capa DAO", he);
    }

    public List<Mantenimiento> obtener_mantenimiento() throws Exception {
        try {
            iniciaOperacion();
            CriteriaQuery criteria = sesion.getCriteriaBuilder().createQuery(Mantenimiento.class);
            criteria.from(Mantenimiento.class);
            List<Mantenimiento> man = sesion.createQuery(criteria).getResultList();
            return man;
        } catch (HibernateException e) {
            throw e;
        } finally {
            sesion.close();

        }
    }
    public void actualizar_manto(Mantenimiento manto){
        try {
            iniciaOperacion();
            sesion.update(manto);
            sesion.flush();
            tx.commit();

        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }
    
}
