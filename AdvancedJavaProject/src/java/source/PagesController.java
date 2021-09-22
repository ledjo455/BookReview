
package source;

public class PagesController {
    private static final PagesController INSTANCE = new PagesController();
    
    private PagesController() {        
    }
    
    public static PagesController getInstance() {
        return INSTANCE;
    }
    
    public String redirectToIndex() {
        return "/index";
    }
    
    public String redirectToBookView() {
        return "/book/view";
    }
    
    public String redirectToBookCreate() {
        return "/book/create";
    }
    
    public String redirectToBookEdit() {
        return "/book/edit";
    }
    
    public String redirectToLogin() {
        return "/authentication/login";
    }
    
    public String redirectToReviewCreate() {
        return "/review/create";
    }
    
    public String redirectToReviewView() {
        return "/review/MyReview";
    }
    
    public String redirectToUpdateDataUser() {
        return "/authentication/updateDataUser";
    }
    
    public String redirectToUsersData() {
        return "/authentication/usersData";
    }
}
