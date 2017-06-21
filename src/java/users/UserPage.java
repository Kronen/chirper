package users;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import home.HomePage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.controllers.PostJpaController;
import jpa.controllers.PrivateJpaController;
import jpa.controllers.ProfileJpaController;
import jpa.entities.Post;
import jpa.entities.Private;
import jpa.entities.Profile;
import org.primefaces.context.RequestContext;
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
    private Private newPrivate;
    private List<Post> userPosts;
    
    @ManagedProperty("#{homePage}")
    private HomePage homePage;

    public UserPage() {
        emf = Persistence.createEntityManagerFactory("ChirperDbPU");
        newPrivate = new Private();
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

    public List<Post> getUserPosts() {
        PostJpaController postC = new PostJpaController(emf);
        userPosts = postC.findPostsByAuthor(profile.getId());
        return userPosts;
    }
    
    public Private getNewPrivate() {
        return newPrivate;
    }

    public void setNewPrivate(Private newPrivate) {
        this.newPrivate = newPrivate;
    }
    
    
    @URLAction
    public String loadUserData() {        
        ProfileJpaController profileC = new ProfileJpaController(emf);
        profile = profileC.findProfileByUserName(userName);
        PostJpaController postC = new PostJpaController(emf);
        userPosts = postC.findPostsByAuthor(profile.getId());
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
    
    public void showPrivateMessageDialog() {
        Map<String,Object> options = new HashMap<>();
        options.put("resizable", false);
        Map<String,List<String>> params = new HashMap<>();
        ArrayList<String> values = new ArrayList<>();
        values.add(userName);
        params.put("username", values);
        RequestContext.getCurrentInstance().openDialog("/user/privateDialog", options, params);
    }
    
    public void sendPrivate() {
        ProfileJpaController profileC = new ProfileJpaController(emf);
        Profile pr = profileC.findProfileByUserName(userName);
        newPrivate.setSender(homePage.getProfile());
        newPrivate.setReceiver(pr);
        newPrivate.setMarkedRead(false);
        PrivateJpaController privateC = new PrivateJpaController(emf);
        privateC.create(newPrivate);
        newPrivate = new Private();
    }
}