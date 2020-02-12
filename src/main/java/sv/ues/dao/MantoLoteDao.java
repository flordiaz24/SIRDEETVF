/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import sv.ues.dominio.Mantenimiento;
import sv.ues.utils.HibernateUtil;

/**
 *
 * @author PC
 */
public class MantoLoteDao {

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

    public void registrar_nuevo_manto(Mantenimiento m) {
        try {
            iniciaOperacion();
            sesion.save(m);
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
    
    /**
     * Devuelve cuantos mantenimientos ha tenido un lote ID
     *
     * @param idLote
     * @return
     */
    public Integer numero_manto_lote(Integer idLote) {
        try {
            iniciaOperacion();
            CriteriaBuilder cb = sesion.getCriteriaBuilder();

            CriteriaQuery<Mantenimiento> query = cb.createQuery(Mantenimiento.class);
            Root<Mantenimiento> root = query.from(Mantenimiento.class);
            query.select(root).where(cb.equal(root.get("lote"), idLote));
            Query<Mantenimiento> q = sesion.createQuery(query);
            Integer i = q.getResultList().size();

            return i;
        } catch (HibernateException e) {
            throw e;
        } finally {
            tx.commit();
            sesion.close();
        }
    }

    public List<Mantenimiento> obtener_manto_activos_inactivos(boolean estado) {
        throw new UnsupportedOperationException("FALTA HACER EN MantoLoteDao."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Mantenimiento> lista_manto_de_hoy(boolean estado) {
        try {
            iniciaOperacion();
            CriteriaBuilder cb = sesion.getCriteriaBuilder();
            
            //Falta que devuelva solo los id_lote(lote) diferentes de este dia
            CriteriaQuery<Mantenimiento> query = cb.createQuery(Mantenimiento.class);
            Root<Mantenimiento> root = query.from(Mantenimiento.class);
            query.select(root).where(cb.equal(root.get("fechaProxManto"), new Date()),cb.equal(root.get("completadoManto"), estado));
            Query<Mantenimiento> q = sesion.createQuery(query);
            
            return q.getResultList();
        } catch (HibernateException e) {
            throw e;
        } finally {
            tx.commit();
            sesion.close();
        }
        //throw new UnsupportedOperationException("FALTA HACER EN MantoLoteDao."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean existe_fechamanto_lote(Date d,Integer idLote) {
        try {
            iniciaOperacion();
            CriteriaBuilder cb = sesion.getCriteriaBuilder();

            CriteriaQuery<Mantenimiento> query = cb.createQuery(Mantenimiento.class);
            Root<Mantenimiento> root = query.from(Mantenimiento.class);
            query.select(root).where(
                    cb.equal(root.get("fechaProxManto"), d),
                    cb.equal(root.get("lote"), idLote)
                    );
            Query<Mantenimiento> q = sesion.createQuery(query);
            Integer i = q.getResultList().size();
            if (i == 1) {
                return true;//Ya hay un mantenimiento programado para esta fecha para este lote
            } else {
                return false;//No existe mantenimiento de este lote en esta fecha
            }
            
        } catch (HibernateException e) {
            throw e;
        } finally {
            tx.commit();
            sesion.close();
        }
    }

    public List<Mantenimiento> lista_mantenimientos_por_lote(Integer idLote) {
        try {
            iniciaOperacion();
            CriteriaBuilder cb = sesion.getCriteriaBuilder();
            
            //Falta que devuelva solo los id_lote(lote) diferentes de este dia
            CriteriaQuery<Mantenimiento> query = cb.createQuery(Mantenimiento.class);
            Root<Mantenimiento> root = query.from(Mantenimiento.class);
            query.select(root).where(cb.equal(root.get("lote"), idLote));
            Query<Mantenimiento> q = sesion.createQuery(query);
            
            return q.getResultList();
        } catch (HibernateException e) {
            throw e;
        } finally {
            tx.commit();
            sesion.close();
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public Mantenimiento ultimo_manto_de_lote(Integer idLote) {
        try {
            iniciaOperacion();
            CriteriaBuilder cb = sesion.getCriteriaBuilder();

            CriteriaQuery<Mantenimiento> query = cb.createQuery(Mantenimiento.class);
            Root<Mantenimiento> root = query.from(Mantenimiento.class);
            query.select(root).where(
                    cb.equal(root.get("lote"), idLote)
                    );
            //query.orderBy(cb.desc(root.get("fechaProxManto")));
            Query<Mantenimiento> q = sesion.createQuery(query);
            if(q.getResultList().size() != 0){
                return q.getResultList().get(0);
            }else{
                return null;
            }
        } catch (HibernateException e) {
            throw e;
        } finally {
           sesion.close();
        }
    }
    public void modificar_manto(Mantenimiento m){
        try 
        {
            iniciaOperacion();
            sesion.update(m);
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
