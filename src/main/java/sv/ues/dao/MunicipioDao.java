/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.dao;

import java.util.HashSet;
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
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import sv.ues.dominio.Departamento;
import sv.ues.dominio.Municipio;

/**
 *
 * @author Miguel
 */
public class MunicipioDao 
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
    
    public List<Municipio> obtener_municipios_por_id_del_departamento(Departamento departamento)
    {
        
        iniciaOperacion();
        CriteriaBuilder builder = sesion.getCriteriaBuilder();
        CriteriaQuery<Municipio> query = builder.createQuery(Municipio.class);
        Root<Municipio> root = query.from(Municipio.class);
        query.select(root).where(builder.equal(root.get("departamento"), departamento));
        Query<Municipio> q = sesion.createQuery(query);
        List<Municipio> municipios = q.getResultList();
        sesion.close();
        return municipios;
    }
    
    public List<Municipio> obtener_municipios() throws Exception {
        try{
        iniciaOperacion();
        CriteriaQuery criteria = sesion.getCriteriaBuilder().createQuery(Municipio.class);//Roles  .class);
        criteria.from(Municipio.class);
        List<Municipio> municipios = sesion.createQuery(criteria).getResultList();
        return municipios;
        }catch(HibernateException e){
            throw e;
        }finally{
        sesion.close();
        }
    }
    
    
    public Municipio obtener_municipio(String codigo) throws Exception 
    {
        try
        {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            
            CriteriaQuery<Municipio> query = builder.createQuery(Municipio.class);
            Root<Municipio> root = query.from(Municipio.class);
            query.select(root).where(builder.equal(root.get("codMunicipio"), codigo));

            Query<Municipio> q =sesion.createQuery(query);
            Municipio municipio = q.getSingleResult();

            if(municipio!=null)
            {
                return municipio;
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
}
