package sv.ues.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import sv.ues.dominio.Persona;
import sv.ues.dominio.Rol;
import sv.ues.dominio.Usuario;
import sv.ues.utils.HibernateUtil;

/**
 *
 * @author Miguel Martinez
 */
public class UsuarioDao 
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

    public Usuario obtener_usuario_hash(String hash) throws Exception 
    {
        try
        {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            
            CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
            Root<Usuario> root = query.from(Usuario.class);
            query.select(root).where(builder.equal(root.get("hash"), hash));
            Query<Usuario> q =sesion.createQuery(query);
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
    
    public Usuario obtener_usuario_id(int id) throws Exception 
    {
        try
        {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            
            CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
            Root<Usuario> root = query.from(Usuario.class);
            query.select(root).where(builder.equal(root.get("idUsuario"), id));
            Query<Usuario> q =sesion.createQuery(query);
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
    
    public Usuario obtener_usuario_nombre(String nombre) throws Exception 
    {
        try
        {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            
            CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
            Root<Usuario> root = query.from(Usuario.class);
            query.select(root).where(builder.equal(root.get("nomUsuario"), nombre));
            Query<Usuario> q =sesion.createQuery(query);
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
    
    public void modificar(Usuario usuario) throws Exception 
    {
        try 
        {
            iniciaOperacion();
            sesion.update(usuario);
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
