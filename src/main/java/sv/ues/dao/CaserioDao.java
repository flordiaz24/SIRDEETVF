/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import sv.ues.dominio.Cacerio;
import sv.ues.utils.HibernateUtil;

/**
 *
 * @author Flor Diaz
 */
public class CaserioDao {

    private HibernateUtil hibernateUtil = new HibernateUtil();
    private SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
    private Session sesion;
    private Transaction tx;

    private void iniciaOperacion() throws HibernateException {
        //sesion = sessionFactory.openSession();
        //tx = sesion.beginTransaction();
        sesion = null;
        tx = null;
        /*try {
            if (sessionFactory.getCurrentSession()== null) {
                sesion = sessionFactory.openSession();
            }else{
                sesion = sessionFactory.getCurrentSession();
            }
            tx = sesion.beginTransaction();
        } catch (Throwable t) {
            System.err.println("Error al obtener sesion caserioDAO.");
            t.printStackTrace();
        }
        De esta forma hay que agregar thread en context hibernate.cfg
        */
        try 
        {
            sesion = sessionFactory.openSession();
            tx = sesion.beginTransaction();
        }
        catch(Throwable t) {
            System.err.println("Error en obtener sesión DAO_Caserio");
        }
    }

    private void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("OcurriÃ³ un error en la capa DAO", he);
    }
    public void nuevo_caserio(Cacerio caserio) {
            try {
            iniciaOperacion();
            sesion.save(caserio);
            sesion.flush();
            tx.commit();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }
    public Cacerio caserio_por_id(Integer idcaserio) {
        try {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            CriteriaQuery<Cacerio> query = builder.createQuery(Cacerio.class);
            Root<Cacerio> root = query.from(Cacerio.class);
            query.select(root).where(builder.equal(root.get("idCacerio"), idcaserio));
            Query<Cacerio> q =sesion.createQuery(query);
            return q.getSingleResult();
        }
        catch(HibernateException x) {
            return null;
        }
        finally {
            tx.commit();
            sesion.close();
        }
    }
    public void modificar_caserio(Cacerio c){
            try 
        {
            iniciaOperacion();
            sesion.update(c);
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
}
