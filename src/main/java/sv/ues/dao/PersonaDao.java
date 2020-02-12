package sv.ues.dao;

import java.util.List;
import java.util.Set;
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
public class PersonaDao 
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

    public void registrar(Persona persona, Usuario usuario) throws Exception 
    {
        try 
        {
            iniciaOperacion();
            
            sesion.save(usuario);
            
            persona.setUsuario(usuario);
            sesion.save(persona);
            
            
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
    
    public void modificar(Persona persona) throws Exception 
    {
        try 
        {
            iniciaOperacion();
            sesion.update(persona);
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
    
    public boolean validar_persona_modificar_dui(String dui,long id) throws Exception 
    {
        try
        {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            CriteriaQuery<Persona> query = builder.createQuery(Persona.class);
            Root<Persona> root = query.from(Persona.class);
            //query.select(root).where(builder.equal(root.get("nombre"), nombre));
            query.select(root).where(builder.equal(root.get("dui"), dui),builder.notEqual(root.get("idPersona"), id));

            Query<Persona> q=sesion.createQuery(query);
            List<Persona> lsroles = q.list();
            sesion.close();
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
            sesion.close();
            return false;
        }
    }
    
    
    public boolean validar_persona_modificar_nit(String nit,long id) throws Exception 
    {
        try
        {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            CriteriaQuery<Persona> query = builder.createQuery(Persona.class);
            Root<Persona> root = query.from(Persona.class);
            //query.select(root).where(builder.equal(root.get("nombre"), nombre));
            query.select(root).where(builder.equal(root.get("nit"), nit),builder.notEqual(root.get("idPersona"), id));

            Query<Persona> q=sesion.createQuery(query);
            List<Persona> lsroles = q.list();
            sesion.close();
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
            sesion.close();
            return false;
        }
    }
    
    
    public Persona obtener_persona_usuario(Usuario usuario) throws Exception 
    {
        try
        {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            CriteriaQuery<Persona> query = builder.createQuery(Persona.class);
            Root<Persona> root = query.from(Persona.class);
            query.select(root).where(builder.equal(root.get("usuario"), usuario));
            Query<Persona> q =sesion.createQuery(query);
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
    
    
    public Persona obtener_persona_dui(String dui) throws Exception 
    {
        try
        {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            CriteriaQuery<Persona> query = builder.createQuery(Persona.class);
            Root<Persona> root = query.from(Persona.class);
            query.select(root).where(builder.equal(root.get("dui"), dui));
            Query<Persona> q =sesion.createQuery(query);
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
    
    public Persona obtener_persona_nit(String nit) throws Exception 
    {
        try
        {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            
            CriteriaQuery<Persona> query = builder.createQuery(Persona.class);
            Root<Persona> root = query.from(Persona.class);
            query.select(root).where(builder.equal(root.get("nit"), nit));
            Query<Persona> q =sesion.createQuery(query);
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
    
    public Persona obtener_persona_email(String email) throws Exception 
    {
        try
        {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            
            CriteriaQuery<Persona> query = builder.createQuery(Persona.class);
            Root<Persona> root = query.from(Persona.class);
            query.select(root).where(builder.equal(root.get("correoPersona"), email));
            Query<Persona> q =sesion.createQuery(query);
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
    
    
    public boolean validar_persona_modificar_email(String email,long id) throws Exception 
    {
        try
        {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            CriteriaQuery<Persona> query = builder.createQuery(Persona.class);
            Root<Persona> root = query.from(Persona.class);
            //query.select(root).where(builder.equal(root.get("nombre"), nombre));
            query.select(root).where(builder.equal(root.get("correoPersona"), email),builder.notEqual(root.get("idPersona"), id));

            Query<Persona> q=sesion.createQuery(query);
            List<Persona> lsroles = q.list();
            sesion.close();
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
            sesion.close();
            return false;
        }
    }
    
    public List<Persona> obtener_personas() throws Exception {
        try{
        iniciaOperacion();
        CriteriaQuery criteria = sesion.getCriteriaBuilder().createQuery(Persona.class);//Roles  .class);
        criteria.from(Persona.class);
        List<Persona> personas = sesion.createQuery(criteria).getResultList();
        //sesion.close();
        return personas;
        }catch(HibernateException e){
            throw e;
        }finally{
        sesion.close();
        
        }
    }
    

}
