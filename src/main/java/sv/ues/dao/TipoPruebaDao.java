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
import sv.ues.dominio.Rol;
import sv.ues.dominio.TipoPrueba;
import sv.ues.utils.HibernateUtil;

/**
 *
 * @author Daniel
 */
public class TipoPruebaDao {
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
     
     private void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("Ocurrió un error en la capa DAO", he);
    } 
     
     
      public void registrar(TipoPrueba tipoPrueba) throws Exception {
      
        try {
            iniciaOperacion();
            sesion.save(tipoPrueba);
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
      
    public List<TipoPrueba> obtenerTipoPruebas() throws Exception {
        try{
        iniciaOperacion();
        CriteriaQuery criteria = sesion.getCriteriaBuilder().createQuery(TipoPrueba.class);
        criteria.from(TipoPrueba.class);
        List<TipoPrueba> lsTipoPrueba = sesion.createQuery(criteria).getResultList();
        return lsTipoPrueba;
        }catch(HibernateException e){
            throw e;
        }finally{
        sesion.close();
        
        }
    }   
    
    
    public boolean validarNombreTipoPrueba(String nombre) throws Exception 
    {
        try
        {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            CriteriaQuery<TipoPrueba> query = builder.createQuery(TipoPrueba.class);
            Root<TipoPrueba> root = query.from(TipoPrueba.class);
            query.select(root).where(builder.equal(root.get("nomPrueba"), nombre));
            Query<TipoPrueba> q=sesion.createQuery(query);
            TipoPrueba tipoPrueba= q.getSingleResult();
            if(tipoPrueba!=null)
            {
                //System.out.println("EXISTE");
                return true;
            }
            else
            {
                return false;
            }
        }
        catch(Exception x)
        {            
            return false;
        }
    }
    
    public boolean validarTipoPruebaModificar(String nombre,long id) throws Exception 
    {
        //private List<Rol> lsroles=new ArrayList();
        try
        {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            CriteriaQuery<TipoPrueba> query = builder.createQuery(TipoPrueba.class);
            Root<TipoPrueba> root = query.from(TipoPrueba.class);
            //query.select(root).where(builder.equal(root.get("nombre"), nombre));
            query.select(root).where(builder.equal(root.get("nomPrueba"), nombre),builder.notEqual(root.get("idTipoPrueba"), id));

            Query<TipoPrueba> q=sesion.createQuery(query);
            List<TipoPrueba> lsroles = q.list();

            if(lsroles.size() > 0)
            {
                System.out.println("EXISTE");
                return true;
            }
            else
            {
                return false;
            }
        }
        catch(Exception x)
        {
            System.out.println(x.toString());
            System.out.println("NO EXISTE");
            return false;
        }
    }

    public void actualizarTipoPrueba(TipoPrueba tipoPrueba) throws Exception {
        
        try {
            iniciaOperacion();
            sesion.update(tipoPrueba);
            sesion.flush();
            tx.commit();

        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }

    public void eliminarTipoPrueba(TipoPrueba tipoPrueba) {
      
        try {
            iniciaOperacion();
            sesion.delete(tipoPrueba);
            //sesion.flush();
            tx.commit();

        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
           sesion.close();
        }
    
    }
    
}
