package posts;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.controllers.ProfileJpaController;
import jpa.entities.Private;
import jpa.entities.Profile;
import jpa.entities.User;
import utils.ContextHandler;

/**
 *
 * @author Alberto G. Lagos
 */
@ManagedBean
@RequestScoped
@URLMapping(id="viewPrivateMessage", pattern="/privates", viewId="/faces/user/privates-view.xhtml")
public class PrivateMessageBean {
    private final EntityManagerFactory emf;
    private User user;
    private Profile profile;
    
    public PrivateMessageBean() {
        emf = Persistence.createEntityManagerFactory("ChirperDbPU");
    }
    
    @PostConstruct
    public void init() {       
        user = (User)ContextHandler.externalContext().getSessionMap().get("user");
        if(user != null)
            loadProfile(user.getUserName());        
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    /* Loads the profile for the current logged in user */
    public void loadProfile(String username) {        
        ProfileJpaController profileC = new ProfileJpaController(emf);
        profile = profileC.findProfileByUserName(username);
    }
    
    
    public List getSentPrivateMessages() {        
        return new ArrayList(profile.getPrivateCollection());
    }
    
    public List getReceivedPrivateMessages() {        
        return new ArrayList(profile.getPrivateCollection1());
    }
}
