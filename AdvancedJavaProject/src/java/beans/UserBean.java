/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.DaoUser;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.User;
import source.PagesController;


@Named("userBean")
@SessionScoped
public class UserBean implements Serializable {

    @Inject
    private AuthenticationBean authenticationBean;

    private DaoUser daoUser = new DaoUser();
    
    private String confirmUserPassword = "";
    
    private List<User> standardUsers;
    
    private User toUpdate;    
    
    public UserBean() {
        standardUsers = new ArrayList<>();
    }
    
    public void loadStandarUsers(){
        standardUsers = daoUser.findAllStandard();
    }
    
    public List<User> getStandardUsers() {
        return standardUsers;
    }    
    
    
    public String delete(User user){
        daoUser.delete(user);
        return PagesController.getInstance().redirectToUsersData();
    }
    
    public String loadToUpdate(User user){
        this.toUpdate = user;   
        return PagesController.getInstance().redirectToUpdateDataUser();
    }

    public User getToUpdate() {
        return toUpdate;
    }

    public void setToUpdate(User toUpdate) {
        this.toUpdate = toUpdate;
    }
     
    public String updateData(){
        
        if (toUpdate.getPassword().equals(confirmUserPassword)){
            daoUser.update(toUpdate);
            toUpdate = null;
            
        } else return null;
        
        if(authenticationBean.getCurrentUser().getCredential() == User.EnumCredentials.ADMIN)
            return  PagesController.getInstance().redirectToUsersData();
        
        return PagesController.getInstance().redirectToIndex();
        
    }
     
    public boolean readyToUpdate(){
        return toUpdate != null;
    }

    public String getConfirmUserPassword() {
        return confirmUserPassword;
    }

    public void setConfirmUserPassword(String confirmUserPassword) {
        this.confirmUserPassword = confirmUserPassword;
    }    
    
}
