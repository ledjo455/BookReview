
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import model.Review;
import utility.EntityUtil;

public class DaoReview implements IDao<Review> {
    private EntityManager em = EntityUtil.entityManager();

    public DaoReview() {
    }
    
    @Override
    public List<Review> findAll() {
        return em.createNamedQuery("Review.findAll", Review.class).getResultList();
    }

    public List<Review> findAll(String email) {
        return em.createNamedQuery("Review.findByEmail", Review.class)
                .setParameter("email", email)
                .getResultList();
    }
    
    public List<Review> findAll(int book_id) {
        return em.createNamedQuery("Review.findByBookID", Review.class)
                .setParameter("bookID", book_id)
                .getResultList();
    }
    
    @Override
    public Review find(Object id) {
        return em.find(Review.class, id);
    }
    
    @Override
    public void create(Review entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    @Override
    public void update(Review entity) {
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Review entity) {
        em.getTransaction().begin();
        if(!em.contains(entity)) entity = em.merge(entity);
        em.remove(entity);
        em.getTransaction().commit();
    }    
}