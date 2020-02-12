/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import sv.ues.dominio.Lote;
import sv.ues.dominio.Mantenimiento;
import sv.ues.utils.HibernateUtil;

/**
 *
 * @author Flor Rivas
 */
public class LotesDao {

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

    public List<Lote> obtener_lotes() throws Exception {
        try {
            iniciaOperacion();
            CriteriaQuery criteria = sesion.getCriteriaBuilder().createQuery(Lote.class);
            criteria.from(Lote.class);
            List<Lote> lotes = sesion.createQuery(criteria).getResultList();
            return lotes;
        } catch (HibernateException e) {
            throw e;
        } finally {
            sesion.close();

        }
    }

    public Lote registrar_nuevo_lote(Lote l) {
        try {
            iniciaOperacion();
            sesion.save(l);
            sesion.flush();
            tx.commit();
            return l;
        } catch (HibernateException he) {
            tx.rollback();
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }

    public boolean existe_lote(String nombreLote) {
        try {
            iniciaOperacion();
            CriteriaBuilder cb = sesion.getCriteriaBuilder();
            CriteriaQuery<Lote> query = cb.createQuery(Lote.class);
            Root<Lote> root = query.from(Lote.class);
            query.select(root).where(cb.equal(root.get("nombreLote"), nombreLote));
            Query<Lote> q = sesion.createQuery(query);
            if (q.getResultList().isEmpty()) {
                return false;
            } else {
                return true;
            }
        } catch (HibernateException e) {
            throw e;
        } finally {
            tx.commit();
            sesion.close();
        }
    }

    public List<Lote> obtener_lotes_activos_inactivos(Integer estado) {
        try {
            iniciaOperacion();
            CriteriaBuilder cb = sesion.getCriteriaBuilder();
            CriteriaQuery<Lote> query = cb.createQuery(Lote.class);
            Root<Lote> root = query.from(Lote.class);
            query.select(root).where(cb.equal(root.get("estatus"), estado));
            Query<Lote> q = sesion.createQuery(query);
            return q.getResultList();
        } catch (HibernateException e) {
            throw e;
        } finally {
            tx.commit();
            sesion.close();
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Lote lote_por_id(Integer idLote) {
        try {
            iniciaOperacion();
            CriteriaBuilder cb = sesion.getCriteriaBuilder();
            CriteriaQuery<Lote> query = cb.createQuery(Lote.class);
            Root<Lote> root = query.from(Lote.class);
            query.select(root).where(cb.equal(root.get("idLote"), idLote));
            Query<Lote> q = sesion.createQuery(query);
            return q.getSingleResult();
        } catch (HibernateException e) {
            throw e;
        } finally {
            tx.commit();
            sesion.close();
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public List<Lote> obtener_lotes_activos_falta_muestra(Integer estado) {
        MuestrasDao mDao = new MuestrasDao();
        try {
            iniciaOperacion();
            CriteriaBuilder cb = sesion.getCriteriaBuilder();
            CriteriaQuery<Lote> query = cb.createQuery(Lote.class);
            Root<Lote> root = query.from(Lote.class);
            query.select(root).where(cb.equal(root.get("estatus"), estado));
            Query<Lote> q = sesion.createQuery(query);
            List<Lote> tl = q.getResultList();
            List<Lote> tlotes= new ArrayList();
            for (Lote lote : tl) {
                if(lote.getNumMuestras()>mDao.numero_muestras_lote(lote.getIdLote())){
                    tlotes.add(lote);
                }
            }
            return tlotes;
        } catch (HibernateException e) {
            throw e;
        } finally {
            tx.commit();
            sesion.close();
        }
    }
    public void modificar_lote(Lote lote){
            try 
        {
            iniciaOperacion();
            sesion.update(lote);
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
    public boolean existe_otro_lote_asi(Lote l){
        try {
            iniciaOperacion();
            CriteriaBuilder cb = sesion.getCriteriaBuilder();
            CriteriaQuery<Lote> query = cb.createQuery(Lote.class);
            Root<Lote> root = query.from(Lote.class);
            query.select(root).where(cb.equal(root.get("nombreLote"), l.getNombreLote()),cb.notEqual(root.get("idLote"), l.getIdLote()));
            Query<Lote> q = sesion.createQuery(query);
            if (q.getResultList().isEmpty()) {
                return false;
            } else {
                return true;
            }
        } catch (HibernateException e) {
            throw e;
        } finally {
            tx.commit();
            sesion.close();
        }
    }
}
    
