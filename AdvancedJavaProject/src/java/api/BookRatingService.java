
package api;

import beans.BookBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.xml.bind.annotation.XmlElement;
import model.Book;
import utility.EntityUtil;
import utility.FilterUtil;

@WebService(serviceName = "BookRatingService")
public class BookRatingService {
    

    @WebMethod(operationName = "BooksParamters")
    public List<Book> BooksParameters(
            @WebParam( name = "title") @XmlElement(required = false) String title,
            @WebParam( name = "author") @XmlElement(required = false) String author,
            @WebParam( name = "category") @XmlElement(required = false) String category,
            @WebParam( name = "ratingMin") @XmlElement(required = false) String ratingMin,
            @WebParam( name = "ratingMax") @XmlElement(required = false) String ratingMax) {
        
        
        List<Book> books = new ArrayList<>();
        EntityManager em = EntityUtil.entityManager();
        
        try {
           books = em.createNamedQuery("Book.findAll", Book.class)
                    .getResultList();
           
           FilterUtil filter = new FilterUtil(books, "author", author);
                   
           if(author != null) {
           {
                filter.setFilterType("author");
                filter.setFilterValue(author);
                books = filter.filter();}
           }
           
           if(title != null) {
                filter.setFilterType("title");
                filter.setFilterValue(title);
                books = filter.filter();}
           
            if(category != null){
                filter.setFilterType("genre");
                filter.setFilterValue(category);
                books = filter.filter();
            }
            
            if(ratingMin != null){
                
                try{
                    
                    double ratingDouble = Double.parseDouble(ratingMin);
                    books = books.stream()
                        .filter(b -> getAverageScore(b) >= ratingDouble )
                        .collect(Collectors.toList());
                }catch (NumberFormatException e){
                    System.err.println("Non parseable ratingMin");
                }

               
            }
            
            if(ratingMax != null){
                try{
                    
                    double ratingDouble = Double.parseDouble(ratingMax);
                    
                    books = books.stream()
                        .filter(b -> getAverageScore(b) <= ratingDouble )
                        .collect(Collectors.toList());
                    
                }catch (NumberFormatException e){
                    System.err.println("Non parseable ratingMax");
                }
            }
           
        } catch (NoResultException e) {
            System.err.println("No Results!");
        }
        
        return books;
    }
    
    
    
    private double getAverageScore(Book book){
          return book.getReviewList().stream()
                .mapToInt(r -> r.getRating())
                .average()
                .orElse(0);
    }
}