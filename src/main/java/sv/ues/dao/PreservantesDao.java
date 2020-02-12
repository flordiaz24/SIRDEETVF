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
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import sv.ues.dominio.Preservante;
import sv.ues.utils.HibernateUtil;

/**
 *
 * @author Miguel
 */
public class PreservantesDao {

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

    public void registrar(Preservante preservante) throws Exception {
      
        try {
            iniciaOperacion();
            sesion.save(preservante);
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

    public List<Preservante> obtener_preservantes() throws Exception {
        try{
        iniciaOperacion();
        CriteriaQuery criteria = sesion.getCriteriaBuilder().createQuery(Preservante.class);
        criteria.from(Preservante.class);
        List<Preservante> preservantes = sesion.createQuery(criteria).getResultList();
        return preservantes;
        }catch(HibernateException e){
            throw e;
        }finally{
        sesion.close();
        
        }
    }
    public boolean validar_preservante(String nombre) throws Exception 
    {
        try
        {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            CriteriaQuery<Preservante> query = builder.createQuery(Preservante.class);
            Root<Preservante> root = query.from(Preservante.class);
            query.select(root).where(builder.equal(root.get("nomPreservante"), nombre));
            Query<Preservante> q=sesion.createQuery(query);
            Preservante preservante= q.getSingleResult();

            if(preservante!=null)
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
            System.out.println(x.toString());
            System.out.println("NO EXISTE");
            return false;
        }
    }
    
    public boolean validar_preservante_modificar(String nombre,long id) throws Exception 
    {
        try
        {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            CriteriaQuery<Preservante> query = builder.createQuery(Preservante.class);
            Root<Preservante> root = query.from(Preservante.class);
            query.select(root).where(builder.equal(root.get("nomPreservante"), nombre),builder.notEqual(root.get("idPreservante"), id));

            Query<Preservante> q=sesion.createQuery(query);
            List<Preservante> lspreservantes = q.list();

            if(lspreservantes.size() > 0)
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

    public void actualizar_preservante(Preservante preservante) throws Exception {
        
        try {
            iniciaOperacion();
            sesion.update(preservante);
            sesion.flush();
            tx.commit();

        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }

    public void eliminar_preservante(Preservante preservante) {
      
        try {
            iniciaOperacion();
            sesion.delete(preservante);
            //sesion.flush();
            tx.commit();

        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
           sesion.close();
        }
    
    }
    //nuevo cambio 

    /**
     * Devuelve el preservante correspondiente al ID
     * @param id
     * @return 
     */
    public Preservante findByPreservanteById(int id){
        try{
             iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            
            CriteriaQuery<Preservante> query = builder.createQuery(Preservante.class);
            Root<Preservante> root = query.from(Preservante.class);
             query.select(root).where(builder.equal(root.get("idPreservante"), id));
            Query<Preservante> q =sesion.createQuery(query);
            return q.getSingleResult();
        }catch(HibernateException e){
            throw e;
        }finally{
            sesion.close();
        }
    }

 
}
