package DAO;

import model.Service;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BaseDAO<T extends Serializable> {
    private Session session = null;

    public void save(T obj){
        try {
            session = HibernateUtil.getSessionAnnotationFactory().openSession();
            session.beginTransaction();
            session.save(obj);
            session.getTransaction().commit();
        }catch (Exception E){
            System.out.println(E);
            System.out.println("Error add");
        }finally {
            if(session != null) {
                if (session.isOpen()) {
                    session.close();
                }
            }
        }
    }
    public void update(T obj){
        Service s = null;
        T loadObj = null;
        try{
            session = HibernateUtil.getSessionAnnotationFactory().openSession();
            session.beginTransaction();
           // session.update(obj);
            session.merge(obj);
            session.getTransaction().commit();
        }catch (Exception E){
            System.out.println(E);
            System.out.println("Error add");
        }finally {
            if(session != null) {
                if (session.isOpen()) {
                    session.close();
                }
            }
        }
    }


    protected void remove(String deleteQuere){
        try {
            session = HibernateUtil.getSessionAnnotationFactory().openSession();
            session.beginTransaction();
            session.createQuery(deleteQuere).executeUpdate();
            session.getTransaction().commit();
        }catch (Exception E){
            System.out.println(E);
            System.out.println("Error add");
        }finally {
            if(session != null) {
                if (session.isOpen()) {
                    session.close();
                }
            }
        }
    }
    protected T get(String getQuery) {
        T obj = null;
        try {
            session = HibernateUtil.getSessionAnnotationFactory().openSession();
            session.beginTransaction();
            Query<T> query = session.createQuery(getQuery);
            List list = query.list();
            obj = query.list().get(0);
            session.getTransaction().commit();
        } catch (Exception E) {
            System.out.println("Error get by id:");
            System.out.println(E);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return obj;
    }

    protected int getLastId(Class classT, String propertyName) {

        Integer id = null;
        try {
            session = HibernateUtil.getSessionAnnotationFactory().openSession();
            session.beginTransaction();
            Criteria criteria = session
                    .createCriteria(classT)
                    .setProjection(Projections.max(propertyName));
                        session.getTransaction().commit();
            id = (Integer) criteria.uniqueResult();
        } catch (Exception E) {
            System.out.println(E);
            System.out.println("Error add");
        } finally {
            if (session != null) {
                if (session.isOpen()) {
                    session.close();
                }
            }
        }
        return id;
    }

    protected ArrayList<T> getList(String query, Class classT){
        ArrayList<T> patientList = new ArrayList<T>();
        try {
            session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
            session.beginTransaction();
            patientList =  (ArrayList<T>) session.createQuery(query, classT).list();
            session.getTransaction().commit();
        }catch (Exception E)
        {
            System.out.println(E);
            System.out.println("Error getList");
        }finally {
            if(session != null) {
                if (session.isOpen()) {
                    session.close();
                }
            }
        }
        return patientList;
    }

}
