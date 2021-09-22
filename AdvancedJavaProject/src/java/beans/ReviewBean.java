
package beans;

import dao.DaoReview;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Book;
import model.Review;
import model.ReviewPK;
import source.PagesController;

@Named("reviewBean")
@RequestScoped
public class ReviewBean implements Serializable {
    @Inject
    private AuthenticationBean authentication;
    @Inject
    private BookBean book;
    
    private Review review = new Review();
    private DaoReview daoReview = new DaoReview();

    public ReviewBean() {
    }

    public AuthenticationBean getAuthentication() {
        return authentication;
    }

    public void setAuthentication(AuthenticationBean authentication) {
        this.authentication = authentication;
    }

    public BookBean getBook() {
        return book;
    }

    public void setBook(BookBean book) {
        this.book = book;
    }
    
    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
    
    public void delete(Review review){
        daoReview.delete(review);
    }
    
     public String deleteAndReload(Review review) {
        delete(review);
        
        review.getBook().getReviewList().remove(review);
        authentication.getCurrentUser().getReviewList().remove(review);
   
        return PagesController.getInstance().redirectToReviewView();
    }
    
    public boolean hasAlreadyReviewd() {
        return authentication.getCurrentUser().getReviewList().stream()
                .map(r -> r.getBook())
                .anyMatch(r -> Objects.equals(r.getBookID(), book.getSelectedBook().getBookID()));
    }
    
    
    public String reviewRedirect(Book book) {
        
        if (!authentication.hasCurrentUser() || book == null) {
            return PagesController.getInstance().redirectToLogin();
        } else if(hasReviewThisBook(book)){
            return null;
        }

        this.book.setSelectedBook(book);
        return PagesController.getInstance().redirectToReviewCreate();
    }
    
    
    private boolean hasReviewThisBook(Book book) {
        
        ReviewPK pk = new ReviewPK(authentication.getCurrentUser().getEmail(), book.getBookID());
        
        return daoReview.find(pk) != null;
    }
    
    public String create() {
        ReviewPK pk = new ReviewPK();
                
        pk.setEmail(authentication.getCurrentUser().getEmail());
        pk.setBookID(book.getSelectedBook().getBookID());
        
        review.setReviewPK(pk);
        review.setUser(authentication.getCurrentUser());
        review.setBook(book.getSelectedBook());
        
        authentication.getCurrentUser().getReviewList().add(review);
        
        try{
            daoReview.create(review);
            book.getSelectedBook().getReviewList().add(review);
        }catch(Exception e){
            e.printStackTrace();
            return PagesController.getInstance().redirectToIndex();
            
        }
        return book.update();
    }    
}
