/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.dao;

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

import sv.ues.dominio.TipoMuestra;
import sv.ues.utils.HibernateUtil;

/**
 *
 * @author Daniel
 */
public class TipoMuestraDao {

    private HibernateUtil hibernateUtil = new HibernateUtil();
    private SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
    private Session sesion;
    private Transaction tx;

    private static final Logger LOGGER = Logger.getLogger(InvestigacionDao.class.getName());

    private void iniciaOperacion() throws HibernateException {
        sesion = sessionFactory.openSession();
        tx = sesion.beginTransaction();
    }

    private void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("Ocurrió un error en la capa DAO", he);
    }

    public List<TipoMuestra> getTiposMuestras() {

        try {
            iniciaOperacion();
            CriteriaQuery criteria = sesion.getCriteriaBuilder().createQuery(TipoMuestra.class);//Roles  .class);
            criteria.from(TipoMuestra.class);
            List<TipoMuestra> lsTipo = sesion.createQuery(criteria).getResultList();
            //sesion.close();
            return lsTipo;
        } catch (HibernateException e) {
            throw e;
        } finally {
            sesion.close();

        }
    }

    public TipoMuestra getTipoMuestraById(int id) {
        try {
            iniciaOperacion();
            CriteriaBuilder builder = sesion.getCriteriaBuilder();

            CriteriaQuery<TipoMuestra> query = builder.createQuery(TipoMuestra.class);
            Root<TipoMuestra> root = query.from(TipoMuestra.class);
            query.select(root).where(builder.equal(root.get("idTipoMues"), id));
            Query<TipoMuestra> q = sesion.createQuery(query);
            return q.getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "exception en tipoMuestraDao getTipoMuestraById", e);
            return null;
        } finally {
            sesion.close();
        }
    }

}
