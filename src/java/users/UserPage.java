package users;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import home.HomePage;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.controllers.ProfileJpaController;
import jpa.entities.Profile;
import utils.FilesHandler;
import utils.MessageHandler;

/**
 *
 * @author Alberto G. Lagos
 */
@ManagedBean
@ViewScoped
@URLMapping(id="viewUser", pattern="/user/#{ username : userPage.userName}", viewId="/faces/user/user-view.xhtml")
public class UserPage implements Serializable {
    
    private final EntityManagerFactory emf;
    private String userName;
    private Profile profile;
    
    @ManagedProperty("#{homePage}")
    private HomePage homePage;

    public UserPage() {
        emf = Persistence.createEntityManagerFactory("ChirperDbPU");
    }

   
    public String getUserName() {      
        return userName;        
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    
    public String getName() {
        return profile.getFullName() != null ? profile.getFullName() : userName;
    }

    public void setHomePage(HomePage homePage) {
        this.homePage = homePage;
    }
    
    @URLAction
    public String loadUserData() {        
        ProfileJpaController profileC = new ProfileJpaController(emf);
        profile = profileC.findProfileByUserName(userName);
        return null;
    }    
    
    public void followUser() {
        ProfileJpaController profileC = new ProfileJpaController(emf);
        // Follower is the logged user, this.profile is the user's profile we are visiting
        Profile follower = homePage.getProfile();        
        if(follower != null)
            profileC.followUser(follower.getId(), profile.getId());
        else 
            MessageHandler.addErrorMessage("Your session has expired.", null);

    }
    
    public void unfollowUser() {
        ProfileJpaController profileC = new ProfileJpaController(emf);
        // Follower is the logged user, this.profile is the user's profile we are visiting
        Profile follower = homePage.getProfile();        
        if(follower != null)
            profileC.unfollowUser(follower.getId(), profile.getId());
        else 
            MessageHandler.addErrorMessage("Your session has expired.", null);

    }
   
    public boolean isFollowed() {
          ProfileJpaController profileC = new ProfileJpaController(emf);
          return profileC.isFollowed(homePage.getProfile().getId(), profile.getId());
    }
    
    public String getAvatar() {
        if(FilesHandler.imageExists(profile.getPhoto())) 
            return profile.getPhoto();
        return "user-blue.png";
    }
}