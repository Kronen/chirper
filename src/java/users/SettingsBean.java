package users;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import home.HomePage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.controllers.ProfileJpaController;
import jpa.controllers.UserJpaController;
import jpa.controllers.exceptions.NonexistentEntityException;
import jpa.entities.Profile;
import jpa.entities.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import utils.FilesHandler;
import utils.MessageHandler;

/**
 *
 * @author Alberto G. Lagos
 */
@ManagedBean
@ViewScoped
@URLMapping(id="viewSettings", pattern="/settings", viewId="/faces/user/settings-view.xhtml")
public class SettingsBean {
    private EntityManagerFactory emf;
    private String password;
    
    @ManagedProperty("#{homePage}")
    private HomePage homePage;

    public SettingsBean() {
    }
    
    @PostConstruct
    public void init() {
        emf = Persistence.createEntityManagerFactory("ChirperDbPU");
    }

    public HomePage getHomePage() {
        return homePage;
    }

    public void setHomePage(HomePage homePage) {
        this.homePage = homePage;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String updateProfile() {
        try {
            ProfileJpaController profileC = new ProfileJpaController(emf);
            Profile editedProfile = homePage.getProfile();
            profileC.edit(editedProfile);
            
            UserJpaController userC = new UserJpaController(emf);
            User editedUser = editedProfile.getUser();
            if(password != null && !password.isEmpty())
                editedUser.setPassword(DigestUtils.sha256Hex(password));
            userC.edit(editedUser);
            MessageHandler.addErrorMessage("Profile has been updated", null);
            
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(SettingsBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageHandler.addErrorMessage("Error updating user, the user doesn't exist. Get in contact with admin.", null);
        } catch (Exception ex) {
            Logger.getLogger(SettingsBean.class.getName()).log(Level.SEVERE, null, ex);
            MessageHandler.addErrorMessage("Fatal error. Get in contact with admin.", null);
        }
        return null;
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile avatar = event.getFile();
        try {
            ProfileJpaController profileC = new ProfileJpaController(emf);
            Profile editedProfile = homePage.getProfile();
            String filename = FilesHandler.saveImageToDisk(avatar);
            editedProfile.setPhoto(filename);
            profileC.edit(editedProfile);
            avatar = null;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(SettingsBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SettingsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
