/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import model.Author;
import utility.EntityUtil;


public class DaoAuthor implements IDao<Author>{

       private EntityManager em = EntityUtil.entityManager();

    public DaoAuthor() {
    }
    
    @Override
    public List<Author> findAll() {
        return em.createNamedQuery("Book.findAll", Author.class).getResultList();
    }

     public Author find(String name) {
        try{
             return em.createNamedQuery("Author.findByName", Author.class)
                .setParameter("name", name)
                .getSingleResult(); 
        }catch ( NoResultException  e){
            return null;
        }
    }
     
    @Override
    public Author find(Object id) {
        return em.find(Author.class, id);
    }

    @Override
    public void create(Author entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    @Override
    public void update(Author entity) {
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Author entity) {
        em.getTransaction().begin();
        em.merge(entity);
        em.remove(entity);
        em.getTransaction().commit();
    }    
    
}
