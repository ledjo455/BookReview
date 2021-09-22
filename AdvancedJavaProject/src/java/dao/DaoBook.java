
package dao;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.faces.validator.Validator;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import model.Book;
import utility.EntityUtil;

public class DaoBook implements IDao<Book> {
    private EntityManager em = EntityUtil.entityManager();

    public DaoBook() {
    }
    
    @Override
    public List<Book> findAll() {
        return em.createNamedQuery("Book.findAll", Book.class).getResultList();
    }

    @Override
    public Book find(Object id) {
        return em.find(Book.class, id);
    }

    @Override
    public void create(Book entity) {
        em.getTransaction().begin();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();
        
        Set<ConstraintViolation<Book>> constraintViolations = validator.validate(entity);
    
        if(constraintViolations.size() > 0){
            Iterator<ConstraintViolation<Book>> iterator = constraintViolations.iterator();
            while(iterator.hasNext()){
                ConstraintViolation<Book> cv = iterator.next();
                System.err.println(cv.getRootBeanClass().getName()+"."+cv.getPropertyPath() + " " +cv.getMessage());
            }
        }else{
            em.persist(entity);
        }
          em.getTransaction().commit();
   
    }

    @Override
    public void update(Book entity) {
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Book entity) {
        em.getTransaction().begin();
        em.merge(entity);
        em.remove(entity);
        em.getTransaction().commit();
    }    
}