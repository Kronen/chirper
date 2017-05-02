package users;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.controllers.ProfileJpaController;
import jpa.entities.Profile;

/**
 *
 * @author Alberto G. Lagos
 */
@ManagedBean
@ViewScoped
@URLMapping(id="viewUser", pattern="/user/#{ username : userPage.username}", viewId="/faces/user/user-view.xhtml")
public class UserPage implements Serializable {
    
    private final EntityManagerFactory emf;
    private String username;
    private Profile perfil;

    public UserPage() {
        emf = Persistence.createEntityManagerFactory("ProyectChirperPU");
    }

    public String getUsername() {      
        return username;        
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Profile getProfile() {
        return perfil;
    }

    public void setProfile(Profile perfil) {
        this.perfil = perfil;
    }
    
    @URLAction
    public String cargaDatosUsuario() {        
        ProfileJpaController pC = new ProfileJpaController(emf);
        perfil = pC.findProfileByUserName(username);
        return null;
    }
}
