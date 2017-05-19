package home;

import java.io.Serializable;
import java.util.ArrayList;
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
import jpa.controllers.TagJpaController;
import jpa.entities.Post;
import jpa.entities.Profile;
import jpa.entities.User;
import utils.FilesHandler;
import utils.MessageHandler;
import utils.TextHandler;

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
        newPost = new Post();
        user = (User)externalContext().getSessionMap().get("user");
        if(user != null)
            loadProfile(user.getUserName());   
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
    
    public String getName() {
        return profile.getFullName() != null ? profile.getFullName() : user.getUserName();
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
        return "theme-blue" + ".css";
    }
    
//    public List countPosts() {
//        PostJpaController postC = new PostJpaController(emf);
//        return postC.getPostCountByAuthor(profile.getId());
//    }
    
    public void loadProfile(String username) {        
        ProfileJpaController profileC = new ProfileJpaController(emf);
        profile = profileC.findProfileByUserName(username);
    }
    
    // La version de JPA es anterior a Java8 y no soporta las nuevas API de Date y Time.
    // Habría que añadir un conversor de atributo a la entidad para usarlas o usar un
    // framework que de soporte a la API como Hibernate 5
    public void publish() {
        createChirps();
        processMentions();        
    }
        
    public String getAvatar() {
        if(FilesHandler.imageExists(profile.getPhoto())) 
            return profile.getPhoto();
        return "user-blue.png";
    }
    
    private void createChirps() {
        PostJpaController postC = new PostJpaController(emf);
        TagJpaController tagC = new TagJpaController(emf);
        List<String> tags = TextHandler.extractTags(newPost.getText());        
        
        // Add post to database
        newPost.setAuthor(profile);
        newPost.setPubDate(new Date());
        postC.create(newPost);        
        System.out.println(newPost.getId());
        // Add tags to database with the post associated
        try {
            tagC.createTagsWithPost(newPost, tags); 
        } catch (Exception ex) {
            MessageHandler.addErrorMessage("Error registering tags for new post", null);
        }
        
    }
    
    private void processMentions() {
        List<String> tags = TextHandler.extractTags(newPost.getText());
    }
}
