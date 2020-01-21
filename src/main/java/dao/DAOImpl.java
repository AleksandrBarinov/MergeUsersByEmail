package dao;

import dao.models.Email;
import dao.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

import javax.persistence.Query;
import java.util.List;

public class DAOImpl implements DAO{

    private SessionFactory sessionFactory;
    private Session session;

    private DAOImpl() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    private static final DAOImpl instance = new DAOImpl();

    public static DAOImpl getInstance() {
        return instance;
    }

    private void beginTransaction() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    private void endTransaction() {
        session.getTransaction().commit();
        session.close();
    }

    public void addNewUser(String name, List<Email> emails) {
        beginTransaction();
        User user = new User();
        user.setName(name);
        user.setEmails(emails);
        session.save(user);
        for (Email email: emails) session.save(email);
        endTransaction();
    }

    public User getUserByName(String name) {
        beginTransaction();
        Query query = session.createQuery("from User u where u.name = :name");
        query.setParameter("name", name);
        User user = (User) query.getSingleResult();
        endTransaction();
        return user;
    }

    public void editUser(int id, String name, List<Email> emails) {
        beginTransaction();
        Query query = session.createQuery("from User u where u.id = :id");
        query.setParameter("id", id);
        User user = (User) query.getSingleResult();
        user.setName(name);
        user.setEmails(emails);
        session.saveOrUpdate(user);
        for (Email email: emails) session.save(email);
        endTransaction();
    }

    public void removeUser(String name) {
        beginTransaction();
        Query query = session.createQuery("from User u where u.name = :name");
        query.setParameter("name", name);
        User user = (User) query.getSingleResult();
        List<Email> emailAddresses = user.getEmails();
        session.delete(user);
        for (Email email: emailAddresses) session.delete(email);
        endTransaction();
    }

    public List<User> getUsers() {
        beginTransaction();
        Query query = session.createQuery("from User u");
        List<User> users = query.getResultList();
        endTransaction();
        return users;
    }

    public List<Email> getEmailAddresses() {
        beginTransaction();
        Query query = session.createQuery("from Email e");
        List<Email> emailAddresses = query.getResultList();
        endTransaction();
        return emailAddresses;
    }
}
