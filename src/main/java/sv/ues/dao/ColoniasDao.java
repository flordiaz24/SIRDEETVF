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
import sv.ues.dominio.ColoniaCanton;

import sv.ues.dominio.Municipio;
import sv.ues.utils.HibernateUtil;

/**
 *
 * @author Daniel
 */
public class ColoniasDao {
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
    
    public List<ColoniaCanton> obtenerCantonesByMunicipio(Municipio municipio)
    {
        
        iniciaOperacion();
        CriteriaBuilder builder = sesion.getCriteriaBuilder();
        CriteriaQuery<ColoniaCanton> query = builder.createQuery(ColoniaCanton.class);
        Root<ColoniaCanton> root = query.from(ColoniaCanton.class);
        query.select(root).where(builder.equal(root.get("municipio"), municipio));
        Query<ColoniaCanton> q = sesion.createQuery(query);
        List<ColoniaCanton> lsColonia  = q.getResultList();
        sesion.close();
        return lsColonia;
    }
    
        
    
    public ColoniaCanton obtenerColoniaCanton(String codigo) throws Exception 
    {
        try
        {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            
            CriteriaQuery<ColoniaCanton> query = builder.createQuery(ColoniaCanton.class);
            Root<ColoniaCanton> root = query.from(ColoniaCanton.class);
            query.select(root).where(builder.equal(root.get("codMunicipio"), codigo));

            Query<ColoniaCanton> q =sesion.createQuery(query);
            ColoniaCanton coloniaCanton = q.getSingleResult();

            if(coloniaCanton!=null)
            {
                return coloniaCanton;
            }
            else
            {
                return null;
            }
        }
        catch(Exception x)
        {
            System.out.println(x.toString());
            return null;
        }
        finally 
        {
            sesion.close();
        }
    }

public ColoniaCanton obtenerColoniaCanton_por_id(Integer id) throws Exception 
    {
        try
        {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            
            CriteriaQuery<ColoniaCanton> query = builder.createQuery(ColoniaCanton.class);
            Root<ColoniaCanton> root = query.from(ColoniaCanton.class);
            query.select(root).where(builder.equal(root.get("idColCan"), id));

            Query<ColoniaCanton> q =sesion.createQuery(query);
            ColoniaCanton coloniacanton = q.getSingleResult();

            return coloniacanton;
        }
        catch(HibernateException x)
        {
            System.out.println(x.toString());
            return null;
        }
        finally 
        {
            sesion.close();
        }
    }
}
