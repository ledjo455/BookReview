
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import model.User;
import utility.EntityUtil;

public class DaoUser implements IDao<User> {
    private EntityManager em = EntityUtil.entityManager();

    public DaoUser() {
    }
    
    @Override
    public List<User> findAll() {
        return em.createNamedQuery("User.findAll", User.class).getResultList();
    }
    
    public List<User> findAllStandard() {
        return em.createNamedQuery("User.findByCredential", User.class)
                .setParameter("credential", User.EnumCredentials.STANDARD)
                .getResultList();
    }

    @Override
    public User find(Object id) {
        return em.find(User.class, id);
    }

    public User find(String email, String password) {
        return em.createNamedQuery("User.findByEmailAndPassword", User.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getSingleResult();
    }
    
    
    @Override
    public void create(User entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    @Override
    public void update(User entity) {
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
    }
    

    @Override
    public void delete(User entity) {
        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();
    }    
}