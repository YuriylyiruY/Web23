package web.dao;

import org.hibernate.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import web.models.User;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author Neil Alishev
 */
@Component
public class PersonDAO  {

    private final EntityManager entityManager;

    @Autowired
    public PersonDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Transactional(readOnly = true)
    public List<User> index() {
        //Session session = sessionFactory.getCurrentSession();
        try (Session session = entityManager.unwrap(Session.class)) {
            return session.createQuery("select p from User p", User.class)
                    .getResultList();
        }
    }

    @Transactional(readOnly = true)
    public User show(int id) {
       // Session session = sessionFactory.getCurrentSession();
        try (Session session = entityManager.unwrap(Session.class)) {
            return session.get(User.class, id);
        }
    }

    @Transactional
    public void save(User person) {
        try (Session session = entityManager.unwrap(Session.class)) {

            session.save(person);
        }
    }

    @Transactional
    public void update(int id, User updatedPerson) {
        User personToBeUpdated;
        try (Session session = entityManager.unwrap(Session.class)) {

            personToBeUpdated = session.get(User.class, id);
        }

        personToBeUpdated.setName(updatedPerson.getName());
        personToBeUpdated.setAge(updatedPerson.getAge());
        personToBeUpdated.setEmail(updatedPerson.getEmail());
    }

    @Transactional
    public void delete(int id) {
        try (Session session = entityManager.unwrap(Session.class)) {

            session.remove(session.get(User.class, id));
        }
    }
}
