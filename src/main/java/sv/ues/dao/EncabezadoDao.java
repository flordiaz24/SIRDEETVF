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
import sv.ues.dominio.ActividadEncabezado;
import sv.ues.dominio.Investigacion;
import sv.ues.dominio.Menu;
import sv.ues.dominio.Persona;
import sv.ues.dominio.Rol;
import sv.ues.dominio.Usuario;
import sv.ues.utils.HibernateUtil;

/**
 *
 * @author Miguel Martinez
 */
public class EncabezadoDao 
{

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
    
    public void registrar(ActividadEncabezado ae) throws Exception {
      
        try {
            iniciaOperacion();
            sesion.save(ae);
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

    public ActividadEncabezado obtener_actividad_hash(String hash) throws Exception 
    {
        try
        {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            
            CriteriaQuery<ActividadEncabezado> query = builder.createQuery(ActividadEncabezado.class);
            Root<ActividadEncabezado> root = query.from(ActividadEncabezado.class);
            query.select(root).where(builder.equal(root.get("hash"), hash));
            Query<ActividadEncabezado> q =sesion.createQuery(query);
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
    
    public ActividadEncabezado obtener_actividad_id(int id) throws Exception 
    {
        try
        {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            
            CriteriaQuery<ActividadEncabezado> query = builder.createQuery(ActividadEncabezado.class);
            Root<ActividadEncabezado> root = query.from(ActividadEncabezado.class);
            query.select(root).where(builder.equal(root.get("id_encabezado"), id));
            Query<ActividadEncabezado> q =sesion.createQuery(query);
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
    
    public void modificar(ActividadEncabezado ae) throws Exception 
    {
        try 
        {
            iniciaOperacion();
            sesion.update(ae);
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
    
    public List<ActividadEncabezado> obtener_menus() throws Exception {
        try{
        iniciaOperacion();
        CriteriaQuery criteria = sesion.getCriteriaBuilder().createQuery(ActividadEncabezado.class);//Roles  .class);
        criteria.from(ActividadEncabezado.class);
        List<ActividadEncabezado> menus = sesion.createQuery(criteria).getResultList();
        //sesion.close();
        return menus;
        }catch(HibernateException e){
            throw e;
        }finally{
        sesion.close();
        
        }
    }

    public List<Investigacion> obtener_investigaciones() throws Exception {
        try{
        iniciaOperacion();
        CriteriaQuery criteria = sesion.getCriteriaBuilder().createQuery(Investigacion.class);//Roles  .class);
        criteria.from(Investigacion.class);
        List<Investigacion> menus = sesion.createQuery(criteria).getResultList();
        //sesion.close();
        return menus;
        }catch(HibernateException e){
            throw e;
        }finally{
        sesion.close();
        
        }
    }
}
