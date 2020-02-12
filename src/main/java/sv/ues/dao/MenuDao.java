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
import sv.ues.dominio.Menu;
import sv.ues.dominio.Rol;
import sv.ues.utils.HibernateUtil;

/**
 *
 * @author Miguel
 */
public class MenuDao {

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

    public void registrar(Menu menu) throws Exception {
      
        try {
            iniciaOperacion();
            sesion.save(menu);
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

    public List<Menu> obtener_menus() throws Exception {
        try{
        iniciaOperacion();
        CriteriaQuery criteria = sesion.getCriteriaBuilder().createQuery(Menu.class);//Roles  .class);
        criteria.from(Menu.class);
        List<Menu> menus = sesion.createQuery(criteria).getResultList();
        //sesion.close();
        return menus;
        }catch(HibernateException e){
            throw e;
        }finally{
        sesion.close();
        
        }
    }
    
    
    public boolean validar_menu(String nombre) throws Exception 
    {
        try
        {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            
            CriteriaQuery<Menu> query = builder.createQuery(Menu.class);
            Root<Menu> root = query.from(Menu.class);
            query.select(root).where(builder.equal(root.get("nomMenu"), nombre));

            Query<Menu> q =sesion.createQuery(query);
            Menu menu= q.getSingleResult();
            sesion.close();
            if(menu!=null)
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
            sesion.close();
            return false;
        }
    }
    
    public boolean validar_menu_modificar(String nombre,long id) throws Exception 
    {
        try
        {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();
            CriteriaQuery<Menu> query = builder.createQuery(Menu.class);
            Root<Menu> root = query.from(Menu.class);
            //query.select(root).where(builder.equal(root.get("nombre"), nombre));
            query.select(root).where(builder.equal(root.get("nomMenu"), nombre),builder.notEqual(root.get("idMenu"), id));

            Query<Menu> q=sesion.createQuery(query);
            List<Menu> lsroles = q.list();
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

    public void actualizar_menu(Menu menu) throws Exception {
        
        try {
            iniciaOperacion();
            sesion.update(menu);
            sesion.flush();
            tx.commit();

        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }

    /*public void eliminar_rol(Rol rol) {
      
        try {
            iniciaOperacion();
            sesion.delete(rol);
            //sesion.flush();
            tx.commit();

        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
           sesion.close();
        }
    
    }*/

}
