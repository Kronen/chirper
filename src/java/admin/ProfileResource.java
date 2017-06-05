package admin;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.controllers.ProfileJpaController;
import jpa.controllers.exceptions.IllegalOrphanException;
import jpa.controllers.exceptions.NonexistentEntityException;
import jpa.entities.Profile;
import org.primefaces.event.RowEditEvent;
import utils.MessageHandler;

/**
 *
 * @author Alberto G. Lagos
 */
@ManagedBean
@ViewScoped
public class ProfileResource {
    
    private EntityManagerFactory emf;
    private List<Profile> profiles;
    private Profile newProfile;

    public ProfileResource() {
    }
    
    @PostConstruct
    public void init() {
        emf = Persistence.createEntityManagerFactory("ChirperDbPU");
        ProfileJpaController profileC = new ProfileJpaController(emf);
        profiles = profileC.findProfileEntities();
        newProfile = new Profile();
    }
    
    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public Profile getNewProfile() {
        return newProfile;
    }

    public void setNewProfile(Profile newProfile) {
        this.newProfile = newProfile;
    }
    
    public void create() {
        ProfileJpaController profileC = new ProfileJpaController(emf);
        try {
            profileC.create(newProfile);
            profiles = profileC.findProfileEntities();
        } catch (Exception ex) {
            MessageHandler.addErrorMessage("Profile already exists or can't be created.", null);
        }
    }
    
    public void remove(Profile profile) {
        try {            
            ProfileJpaController profileC = new ProfileJpaController(emf);
            profileC.destroy(profile.getId());
            profiles = profileC.findProfileEntities();
            MessageHandler.addCustomMessage("Profile Deleted", profile.getId().toString(), null);
        } catch (IllegalOrphanException ex) {
            MessageHandler.addErrorMessage("Profile can't be deleted.", null);
        } catch (NonexistentEntityException ex) {
            MessageHandler.addErrorMessage("Profile doesn't exist", null);
        }
    }
        
    public void onRowEdit(RowEditEvent event) {
        try {
            Profile profile = ((Profile) event.getObject());
            ProfileJpaController profileC = new ProfileJpaController(emf);
            profileC.edit(profile);
            MessageHandler.addCustomMessage("Profile Edited", profile.getId().toString(), null);
        } catch (NonexistentEntityException ex) {
            MessageHandler.addErrorMessage("Profile doesn't exist in database.", null);
        } catch (Exception ex) {
            MessageHandler.addErrorMessage(
                "An unexpected error occurred while processing your request. Contact with administrator.", 
                null
            );
        }
    }
}
