package users;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.controllers.PostJpaController;
import jpa.controllers.ProfileJpaController;
import jpa.entities.Profile;

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
    
    @URLAction
    public String loadUserData() {        
        ProfileJpaController pC = new ProfileJpaController(emf);
        profile = pC.findProfileByUserName(userName);
        return null;
    }
    
//    public List getChirpsFromUser() {
//        PostJpaController pfC = new PostJpaController(emf);
//        return pfC.findPostsByAuthor(profile.getId());
//    }
}
