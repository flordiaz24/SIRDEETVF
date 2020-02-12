/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.dao;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import sv.ues.dominio.Familia;
import sv.ues.dominio.Muestra;
import sv.ues.dominio.Orden;
import sv.ues.utils.HibernateUtil;



/**
 *
 * @author Flor Diaz
 */
public class MuestrasDao {
    private HibernateUtil hibernateUtil = new HibernateUtil();
    private SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
    private Session sesion;
    private Transaction tx;

    private void iniciaOperacion() throws HibernateException 
    {
        sesion = null;
        tx=null;
        try 
        {
            sesion = sessionFactory.openSession();
            tx = sesion.beginTransaction();
        }
        catch(Throwable t)
        {
            System.err.println("Exception while getting session.. ");
            t.printStackTrace();
        }

    }

    private void manejaExcepcion(HibernateException he) throws HibernateException
    {
        tx.rollback();
        throw new HibernateException("Ocurrio un error en la capa DAO", he);
    }    
    
    /**
     * Devuelve una lista de las muestras pertenecientes a ID de lote
     * @param idLote
     * @return Lista de Muestras
     */
    public List<Muestra> muestra_por_lote(Integer idLote) {
         try {
            iniciaOperacion();
            CriteriaBuilder cb = sesion.getCriteriaBuilder();
            CriteriaQuery<Muestra> query = cb.createQuery(Muestra.class);
            Root<Muestra> root = query.from(Muestra.class);
            query.select(root).where(cb.equal(root.get("lote"), idLote));
            query.orderBy(cb.asc(root.get("secuencia")));
            Query<Muestra> q = sesion.createQuery(query);
            return q.getResultList();
        } catch (HibernateException e) {
            throw e;
        } finally {
            tx.commit();
            sesion.close();
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void registrar_nueva_muestra(Muestra mu) {
        try {
            iniciaOperacion();
            sesion.save(mu);
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
    
    public Orden obtenerOrden(int id){
        try{
             iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            
            CriteriaQuery<Orden> query = builder.createQuery(Orden.class);
            Root<Orden> root = query.from(Orden.class);
             query.select(root).where(builder.equal(root.get("id"), id));
            Query<Orden> q =sesion.createQuery(query);
            return q.getSingleResult();
        }catch(HibernateException e){
            throw e;
        }finally{
            sesion.close();
        }
    }
    public Familia obtenerFamilia(int id){
        try{
             iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            
            CriteriaQuery<Familia> query = builder.createQuery(Familia.class);
            Root<Familia> root = query.from(Familia.class);
             query.select(root).where(builder.equal(root.get("id"), id));
            Query<Familia> q =sesion.createQuery(query);
            return q.getSingleResult();
        }catch(HibernateException e){
            throw e;
        }finally{
            sesion.close();
        }
    }
      public Integer obtenerNumMuestras()
    {
        
        iniciaOperacion();
        CriteriaQuery builder = sesion.getCriteriaBuilder().createQuery(Muestra.class);
        builder.from(Muestra.class);
        List<Muestra> lsVector = sesion.createQuery(builder).getResultList();
        sesion.close();
        return lsVector.size();
    }

    public Integer numero_muestras_lote(Integer idLote) {
        try {
            iniciaOperacion();
            CriteriaBuilder cb = sesion.getCriteriaBuilder();

            CriteriaQuery<Muestra> query = cb.createQuery(Muestra.class);
            Root<Muestra> root = query.from(Muestra.class);
            query.select(root).where(cb.equal(root.get("lote"), idLote));
            Query<Muestra> q = sesion.createQuery(query);
            Integer i = q.getResultList().size();
            return i;
        } catch (HibernateException e) {
            throw e;
        } finally {
            tx.commit();
            sesion.close();
        }
    }
    public void modificar_muestra(Muestra m){
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
