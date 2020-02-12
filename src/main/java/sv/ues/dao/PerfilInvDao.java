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
import sv.ues.dominio.PerfilInv;
import sv.ues.utils.HibernateUtil;

/**
 *
 * @author PC
 */
public class PerfilInvDao {
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
    
    
    public PerfilInv getInvestigacionById(int id){
       
        try{
             iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            
            CriteriaQuery<PerfilInv> query = builder.createQuery(PerfilInv.class);
            Root<PerfilInv> root = query.from(PerfilInv.class);
             query.select(root).where(builder.equal(root.get("idPerfil"), id));
            Query<PerfilInv> q =sesion.createQuery(query);
            return q.getSingleResult();
        }catch(HibernateException e){
            throw e;
        }finally{
            sesion.close();
        }
    
    }
    
    //por el momento que la liste todas luego se debe filtrar por estado de la investigacion
    //que debe haber 3 o 2 etaoas inicio,desarrollo y terminada
    public List<PerfilInv> getPerfilInvestigacionByActivo(){
         iniciaOperacion();
        CriteriaQuery builder = sesion.getCriteriaBuilder().createQuery(PerfilInv.class);
        builder.from(PerfilInv.class);
        List<PerfilInv> lsPerfil = sesion.createQuery(builder).getResultList();
        sesion.close();
        return lsPerfil;
    }
    
}
