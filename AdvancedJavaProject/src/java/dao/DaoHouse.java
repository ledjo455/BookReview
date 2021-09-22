/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import model.House;
import utility.EntityUtil;


public class DaoHouse implements IDao<House> {
    private EntityManager em = EntityUtil.entityManager();

    public DaoHouse() { }

    
    @Override
    public List<House> findAll() {
        return em.createNamedQuery("House.findAll", House.class).getResultList();
    }
  
    @Override
    public House find(Object id) {
        return em.find(House.class, id);
    }
    
    
    public House find(String name) {
        try{
             return em.createNamedQuery("House.findByName", House.class)
                .setParameter("name", name)
                .getSingleResult(); 
        }catch ( NoResultException  e){
            return null;
        }
    }
    
    @Override
    public void create(House entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }
    

    @Override
    public void update(House entity) {
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
    }

    @Override
    public void delete(House entity) {
        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();
    }   
}
