package users;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.controllers.ProfileJpaController;
import jpa.entities.Profile;

/**
 *
 * @author Alberto G. Lagos
 */
@ManagedBean
@RequestScoped
@URLMapping(id="viewFollowers", pattern="/followers/#{ profileId : followersBean.profileId}", viewId="/faces/user/followers-view.xhtml")
public class FollowersBean {
    private final EntityManagerFactory emf;
    private List<Profile> followers;
    private int profileId;
    private Profile profile;
    
    public FollowersBean() {
        emf = Persistence.createEntityManagerFactory("ChirperDbPU");
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }
    
    public Profile getProfile() {
        return profile;
    }
    
    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    
    public List<Profile> getFollowers() {
        return new ArrayList(profile.getFollowers());
    }
    
    @URLAction
    public String loadProfile() {        
        ProfileJpaController profileC = new ProfileJpaController(emf);
        profile = profileC.findProfile(profileId);
        return null;
    }
}