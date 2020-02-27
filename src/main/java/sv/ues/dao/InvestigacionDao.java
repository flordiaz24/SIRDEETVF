/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.dao;


import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import sv.ues.dominio.Investigacion;
import sv.ues.dominio.Laboratorio;
import sv.ues.dominio.PerfilInv;


import sv.ues.utils.HibernateUtil;

/**
 *
 * @author PC
 */
public class InvestigacionDao {
    private HibernateUtil hibernateUtil = new HibernateUtil();
    private SessionFactory sessionFactory = hibernateUtil. getSessionFactory();
    private Session sesion;
    private Transaction tx;
    
     private static final Logger LOGGER = Logger.getLogger(InvestigacionDao.class.getName());
    
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
    
    public List<Investigacion> getInvestiagacionByActivo()
    {
        
        iniciaOperacion();
        CriteriaBuilder builder = sesion.getCriteriaBuilder();
        CriteriaQuery<Investigacion> query = builder.createQuery(Investigacion.class);
        Root<Investigacion> root = query.from(Investigacion.class);
        query.select(root).where(builder.equal(root.get("estadoInvest"), true));
        Query<Investigacion> q = sesion.createQuery(query);
        List<Investigacion> investigacion = q.getResultList();
        sesion.close();
        return investigacion;
    }
    
    public List<Laboratorio> getLaboratorio()
    {
        
      try{
        iniciaOperacion();
        CriteriaQuery criteria = sesion.getCriteriaBuilder().createQuery(Laboratorio.class);//Roles  .class);
        criteria.from(Laboratorio.class);
        List<Laboratorio> laboratorio = sesion.createQuery(criteria).getResultList();
        //sesion.close();
        return laboratorio;
        }catch(HibernateException e){
            throw e;
        }finally{
        sesion.close();
        
        }
    }
    
    public Investigacion getInvestigacionById(int id){
        try{
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            
            CriteriaQuery<Investigacion> query = builder.createQuery(Investigacion.class);
            Root<Investigacion> root = query.from(Investigacion.class);
             query.select(root).where(builder.equal(root.get("codInvest"), id));
            Query<Investigacion> q =sesion.createQuery(query);
            return q.getSingleResult();
        }catch(Exception e){
            LOGGER.log(Level.WARNING,"exception en investigacion Dao getInvestigacionById",e);
            return null;
        }finally{
            sesion.close();
        }
    }
    
    public Laboratorio getLaboratorioById(int id){
        try
        {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            
            CriteriaQuery<Laboratorio> query = builder.createQuery(Laboratorio.class);
            Root<Laboratorio> root = query.from(Laboratorio.class);
            query.select(root).where(builder.equal(root.get("codLaboratorio"), id));
            Query<Laboratorio> q =sesion.createQuery(query);
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
    
    public void getInvestigaciones(int key){
        iniciaOperacion();
        CriteriaBuilder builder = sesion.getCriteriaBuilder();
       
        sesion.find(Investigacion.class,key);
    }
    
    
     public void registrar(Investigacion investigacion) throws Exception {
      
        try {
            iniciaOperacion();
            sesion.save(investigacion);
            PerfilInv perfil=investigacion.getPerfilInvs();
            perfil.setInvestigacion(investigacion);
            sesion.save(perfil);
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
     
     public void modificar(Investigacion investigacion) throws Exception 
    {
        try 
        {
            iniciaOperacion();
            sesion.update(investigacion);
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
     
     
     //obtener todas las investigaciones
    public List<Investigacion> obtenerInvestigaciones() throws Exception {
        try{
        iniciaOperacion();
        CriteriaQuery criteria = sesion.getCriteriaBuilder().createQuery(Investigacion.class);
        criteria.from(Investigacion.class);
        List<Investigacion> lsinvestigaciones = sesion.createQuery(criteria).getResultList();
        return lsinvestigaciones;
        }catch(HibernateException e){
            throw e;
        }finally{
        sesion.close();
        
        }
    }
    /**
     * Devuelte lista de investigaciones registrados entre dos fechas
     * @param fechaIni
     * @param fechaFin
     * @return Lista de investigaciones
     * @throws Exception 
     */
    public List<Investigacion> obtener_investigaciones_entre_fechas(Date fechaIni, Date fechaFin) throws Exception {
        try{
            iniciaOperacion();
            String query = "Select inv FROM Investigacion inv WHERE inv.fechaCreacion BETWEEN :fechaIni AND :fechaFin ";
            Query x = sesion.createQuery(query);
            x.setParameter("fechaIni", fechaIni);
            x.setParameter("fechaFin", fechaFin);

            List<Investigacion> ls_inv = x.getResultList();
            //sesion.close();
            return ls_inv;
        } catch (HibernateException e) {
            throw e;
        } finally {
            sesion.close();
        }
    }
    
   
}
