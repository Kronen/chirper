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
@URLMapping(id="viewFollowees", pattern="/followees/#{ profileId : followeesBean.profileId}", viewId="/faces/user/followees-view.xhtml")
public class FolloweesBean {
    private final EntityManagerFactory emf;
    private List<Profile> followees;
    private int profileId;
    private Profile profile;
    
    public FolloweesBean() {
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
        
    public List<Profile> getFollowees() {
        return new ArrayList(profile.getFollowees());
    }
    
    @URLAction
    public String loadProfile() {        
        ProfileJpaController profileC = new ProfileJpaController(emf);
        profile = profileC.findProfile(profileId);
        return null;
    }
    
    

}
