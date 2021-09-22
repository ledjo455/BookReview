
package utility;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityUtil {
    public static EntityManager entityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("AdvancedJavaProjectPU");
        EntityManager em = factory.createEntityManager();
        
        return em;
    }
}
