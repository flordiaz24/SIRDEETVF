/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.dao;

import sv.ues.utils.HibernateUtil;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import sv.ues.dominio.Departamento;

/**
 *
 * @author Miguel
 */
public class DepartamentoDao 
{
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
    
    public List<Departamento> obtener_todos_los_departamentos() throws Exception
    {
        try{
        iniciaOperacion();
        CriteriaQuery criteria = sesion.getCriteriaBuilder().createQuery(Departamento.class);
        criteria.from(Departamento.class);
        List<Departamento> departamentos = sesion.createQuery(criteria).getResultList();
        return departamentos;
        }catch(HibernateException e){
            throw e;
        }finally{
        sesion.close();
        
        }
        
        /*iniciaOperacion();
        Criteria criteria = sesion.createCriteria(Departamento.class);
        List<Departamento> departamentos =(List<Departamento>) criteria.list();
        sesion.close();
	return departamentos;*/
    }

public Departamento departamento_por_id(String idDepto) {
        try {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            CriteriaQuery<Departamento> query = builder.createQuery(Departamento.class);
            Root<Departamento> root = query.from(Departamento.class);
            query.select(root).where(builder.equal(root.get("codDepto"), idDepto));
            Query<Departamento> q =sesion.createQuery(query);
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
    
}
