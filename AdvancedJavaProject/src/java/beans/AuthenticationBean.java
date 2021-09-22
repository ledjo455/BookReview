
package beans;

import dao.DaoUser;
import dao.DaoReview;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import model.User;
import model.Review;
import source.PagesController;

@Named("authenticationBean")
@SessionScoped
public class AuthenticationBean implements Serializable {
    private User currentUser = null;
    
    private DaoUser daoUser = new DaoUser();
    private DaoReview daoReview = new DaoReview();

    @Inject
    private UserBean userBean;
    
        
    private String userName = "";
    private String userSurname = "";
    private String userEmail = "";
    private String userPassword = "";
    private String confirmUserPassword = "";
    
    public AuthenticationBean() {
    }

    public User getCurrentUser() {
        return currentUser;
    }


    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
        
    public boolean hasCurrentUser() {        
        return currentUser != null;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getConfirmUserPassword() {
        return confirmUserPassword;
    }
    
    public String getCurrentUserName(){
        return currentUser.getName();
    }
    
    public boolean isAdmin(){     
        return currentUser != null && currentUser.getCredential() == User.EnumCredentials.ADMIN;
    }
    
    public boolean isStandard(){     
        return currentUser != null && currentUser.getCredential() == User.EnumCredentials.STANDARD;
    }
    
    public String getCurrentSurName(){
        return currentUser.getSurname();
    }
    
    public String redirectToUpdateDataUser(){
        userBean.setToUpdate(currentUser);
        return PagesController.getInstance().redirectToUpdateDataUser();
    }

    public List<Review> UpdateAndGetListOfReviews() {
        
        List<Review> findAll = daoReview.findAll(currentUser.getEmail());
        currentUser.setReviewList(findAll);
        
        return currentUser.getReviewList();
    }
    
    public void setConfirmUserPassword(String confirmUserPassword) {
        this.confirmUserPassword = confirmUserPassword;
    }
    
    
   
        
    public String register() {
        if (userPassword.equals(confirmUserPassword)) {
            
            if(daoUser.find(userEmail) != null){
                cleanFields();
                return PagesController.getInstance().redirectToLogin();
            }
            
            User newUser = new User();
            newUser.setName(userName);
            newUser.setSurname(userSurname);
            newUser.setEmail(userEmail);
            newUser.setPassword(userPassword);
            newUser.setCredential(User.EnumCredentials.STANDARD);
            daoUser.create(newUser);
        
            if(currentUser != null)
                return PagesController.getInstance().redirectToUsersData();
            currentUser = newUser;
        }
        return logIn();
    }
    
    public String logIn() {
        
        try {
            
            currentUser = daoUser.find(userEmail, userPassword);
                    
        } catch (NoResultException e) {
        
            return null;
        }     

        cleanFields();
        return PagesController.getInstance().redirectToIndex();
    }
    
    
    
    public String logOut() {
        clearAllFields();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return PagesController.getInstance().redirectToIndex();
    }
        
    private void clearAllFields() {
        currentUser = null;
        cleanFields();
    }
    
    private void cleanFields(){
        userName = "";
        userSurname = "";
        userEmail = "";
        userPassword = "";
        confirmUserPassword = "";
    }
}
