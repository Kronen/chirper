package home;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.controllers.PostFolloweesJpaController;
import jpa.controllers.ProfileJpaController;
import jpa.entities.Profile;
import jpa.entities.User;

@ManagedBean
@ViewScoped
public class HomePage implements Serializable {
    
    private final EntityManagerFactory emf;
    private User user;
    private Profile profile;

    public HomePage() {
        emf = Persistence.createEntityManagerFactory("ProyectChirperPU");
    }
    
    @PostConstruct
    public void init() {
       ExternalContext externalContext = externalContext();
       user = (User)externalContext.getSessionMap().get("user");
       cargaPerfil(user.getUserName());
    }
    
    private ExternalContext externalContext() {
        return facesContext().getExternalContext();
    }

    private FacesContext facesContext() {
        return FacesContext.getCurrentInstance();
    }

    public User getUser() {      
        return user;        
    }

    public Profile getPerfil() {
        return profile;
    }

    public void setPerfil(Profile perfil) {
        this.profile = profile;
    }
    
    public void cargaPerfil(String username) {        
        ProfileJpaController profileC = new ProfileJpaController(emf);
        profile = profileC.findProfileByUserName(username);
    }
    
    public List getPostFollowees() {
        PostFolloweesJpaController pfC = new PostFolloweesJpaController(emf);
        return pfC.findPostFollowees(profile.getId());
    }
    
    public String elapsed(Date date) {
        LocalDateTime fromDateTime = LocalDateTime.of(2016, 12, 16, 7, 45, 55);
        LocalDateTime toDateTime = LocalDateTime.of(2017, 4, 28, 21, 16, 34);
        
        String diff = "";
        long days = fromDateTime.until(toDateTime, ChronoUnit.DAYS);
        long hours = fromDateTime.until(toDateTime, ChronoUnit.HOURS);

        if(days > 0) diff = days + " d"; 
            else diff = hours + " h";
        
        return diff;
    }
}
