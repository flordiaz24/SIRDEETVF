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
import sv.ues.dominio.Cargo;
import sv.ues.dominio.Persona;
import sv.ues.dominio.TipoPrueba;
import sv.ues.utils.HibernateUtil;

/**
 *
 * @author PC
 */
public class CargoDao {
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

    public void registrar(Cargo cargo) throws Exception 
    {
      
        try 
        {
            iniciaOperacion();
            sesion.save(cargo);
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
    
    public Cargo obtenerCargoByNombre(String cargo) throws Exception 
    {
        try
        {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            
            CriteriaQuery<Cargo> query = builder.createQuery(Cargo.class);
            Root<Cargo> root = query.from(Cargo.class);
            query.select(root).where(builder.equal(root.get("cargo"), cargo));
            Query<Cargo> q =sesion.createQuery(query);
            return q.getSingleResult();
        }
        catch(Exception x)
        {
            return null;
        }
        finally 
        {
            sesion.close();
        }
    }
    
    public List<Cargo> obtenerCargo() throws Exception {
        try{
        iniciaOperacion();
        CriteriaQuery criteria = sesion.getCriteriaBuilder().createQuery(Cargo.class);
        criteria.from(Cargo.class);
        List<Cargo> lsCargo = sesion.createQuery(criteria).getResultList();
        return lsCargo;
        }catch(HibernateException e){
            throw e;
        }finally{
        sesion.close();
        
        }
    }   
    
    //desactivar un cargo
    public void desactivarCargo(Cargo selectedCargo){
        try{
         
            iniciaOperacion();
            selectedCargo.setActivo(Boolean.FALSE);
            sesion.update(selectedCargo);
            sesion.flush();
            tx.commit();
           
        } catch (HibernateException he) {
            manejaExcepcion(he);
           
        } finally {
            sesion.close();
        }
             
                
    }
    
    //validar cargo registrado
    
    public Cargo validarExistCargo(String cargo) throws Exception 
    {
        try
        {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            
            CriteriaQuery<Cargo> query = builder.createQuery(Cargo.class);
            Root<Cargo> root = query.from(Cargo.class);
            query.select(root).where(builder.equal(root.get("cargo"), cargo));
            Query<Cargo> q =sesion.createQuery(query);
            return q.getSingleResult();
        }
        catch(Exception x)
        {
            return null;
        }
        finally 
        {
            sesion.close();
        }
    }
    
    public Cargo validarExistCargoModificar(String cargo,long id) throws Exception 
    {
        try
        {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            
            CriteriaQuery<Cargo> query = builder.createQuery(Cargo.class);
            Root<Cargo> root = query.from(Cargo.class);
            query.select(root).where(builder.equal(root.get("cargo"), cargo),builder.notEqual(root.get("idCargo"), id));
            Query<Cargo> q =sesion.createQuery(query);
            return q.getSingleResult();
        }
        catch(Exception x)
        {
            return null;
        }
        finally 
        {
            sesion.close();
        }
    }
    
   public void actualizarCargo(Cargo cargo) throws Exception {
        
        try {
            iniciaOperacion();
            sesion.update(cargo);
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
