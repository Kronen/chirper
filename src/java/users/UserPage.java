package users;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import home.HomePage;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.controllers.ProfileJpaController;
import jpa.entities.Profile;
import jpa.entities.User;
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
    private Profile profile, loggedProfile;
    private boolean followed;
    
    @ManagedProperty("#{homePage}")
    private HomePage homePage;

    public UserPage() {
        emf = Persistence.createEntityManagerFactory("ChirperDbPU");
    }

    @PostConstruct
    public void init() {
        ProfileJpaController profileC = new ProfileJpaController(emf);
        User loggedUser = (User)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        loggedProfile = profileC.findProfileByUserName(loggedUser.getUserName());
        followed = checkFollowed();
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

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
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
    
//    public List getChirpsFromUser() {
//        PostJpaController pfC = new PostJpaController(emf);
//        return pfC.findPostsByAuthor(profile.getId());
//    }
    
    
    public void followUser() {
        ProfileJpaController profileC = new ProfileJpaController(emf);
        // Follower is the logged user, this.profile is the user's profile we are visiting
        Profile follower = homePage.getProfile();        
        if(follower != null)
            profileC.followUser(follower.getId(), profile.getId());
        else 
            MessageHandler.addErrorMessage("Your session has expired.", null);
        
        followed = true;
    }
    
    public void unfollowUser() {
        ProfileJpaController profileC = new ProfileJpaController(emf);
        // Follower is the logged user, this.profile is the user's profile we are visiting
        Profile follower = homePage.getProfile();        
        if(follower != null)
            profileC.unfollowUser(follower.getId(), profile.getId());
        else 
            MessageHandler.addErrorMessage("Your session has expired.", null);
        
        followed = false;
    }
   
    public boolean checkFollowed() {
        List<Profile> followees = (List)loggedProfile.getFollowees();
        return followees.contains(profile);
    }
}
