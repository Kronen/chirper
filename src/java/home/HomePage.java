package home;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
import jpa.controllers.PostJpaController;
import jpa.controllers.ProfileJpaController;
import jpa.entities.Post;
import jpa.entities.Profile;
import jpa.entities.User;

@ManagedBean
@ViewScoped
public class HomePage implements Serializable {
    
    private final EntityManagerFactory emf;
    private User user;
    private Profile profile;
    private Post newPost;
    

    public HomePage() {
        emf = Persistence.createEntityManagerFactory("ChirperDbPU");  
    }
    
    @PostConstruct
    public void init() {
       ExternalContext externalContext = externalContext();
       user = (User)externalContext.getSessionMap().get("user");
       loadProfile(user.getUserName());
       newPost = new Post();
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

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Post getNewPost() {
        return newPost;
    }

    public void setNewPost(Post post) {
        this.newPost = post;
    }
    
    
    
    public List getPostsFromFollowees() {
        PostFolloweesJpaController pfC = new PostFolloweesJpaController(emf);
        return pfC.findPostsFollowees(profile.getId());
    }
    
    public String getTheme() {
        return "blue-theme";
    }
    
//    public List countPosts() {
//        PostJpaController postC = new PostJpaController(emf);
//        return postC.getPostCountByAuthor(profile.getId());
//    }
    
    public void loadProfile(String username) {        
        ProfileJpaController profileC = new ProfileJpaController(emf);
        profile = profileC.findProfileByUserName(username);
    }
    
    public String elapsed(Date date) {
        
        LocalDateTime fromDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDateTime toDateTime = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
        
        String diff = "";
        long days = fromDateTime.until(toDateTime, ChronoUnit.DAYS);
        long hours = fromDateTime.until(toDateTime, ChronoUnit.HOURS);
        long minutes = fromDateTime.until(toDateTime, ChronoUnit.MINUTES);

        if(days > 0) diff = days + " d"; 
        else if(hours > 0) diff = hours + " h";
        else diff = minutes + " m";
            
        return diff;
    }
    
    public void publish() {
        newPost.setAuthor(profile);
         
        // La version de JPA es anterior a Java8 y no soporta las nuevas clases Date, Time, LocalDateTime, etc... 
        // Habría que añadir un conversor a la entidad para usarlas.        
        newPost.setPubDate(new Date());
        PostJpaController postC = new PostJpaController(emf);
        postC.create(newPost);
    }
}
