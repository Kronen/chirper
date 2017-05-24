package home;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.controllers.PostFolloweesJpaController;
import jpa.controllers.PostJpaController;
import jpa.controllers.ProfileJpaController;
import jpa.controllers.TagJpaController;
import jpa.entities.Post;
import jpa.entities.Profile;
import jpa.entities.User;
import org.apache.commons.io.IOUtils;
import utils.MailHandler;
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
    
    /* Loads the profile for the current logged in user */
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
   
    private void createChirps() {
        PostJpaController postC = new PostJpaController(emf);
        TagJpaController tagC = new TagJpaController(emf);
        List<String> tags = TextHandler.extractTags(newPost.getText());        
        
        // Add post to database
        newPost.setAuthor(profile);
        newPost.setPubDate(new Date());
        postC.create(newPost);
        
        // Add tags to database with the post associated
        try {
            tagC.createTagsWithPost(newPost, tags); 
        } catch (Exception ex) {
            MessageHandler.addErrorMessage("Error registering tags for new post", null);
        }
    }
    
    private void processMentions() {
        ProfileJpaController pC = new ProfileJpaController(emf);
        List<String> users = TextHandler.extractMentions(newPost.getText());
        for(String username : users) {
            System.out.println(username);
            Profile p = pC.findProfileByUserName(username.substring(1));
            if(p != null)
                sendMentionNotification(p.getEmail());
        }
    }
    
    public List trendingTopics(int dias) {
        TagJpaController tC = new TagJpaController(emf);
        return tC.findTrendingTopics(dias);
    }
    
    public void sendMentionNotification(String email) {
        try {
            MailHandler mh = new MailHandler();
            String url_post = "http://localhost/Chirper/post/" + newPost.getId();
            String url_user = "http://localhost/Chirper/user/" + user.getUserName();
            // Uso getResourceAsStream puesto que el fichero no tiene pq estar fisicamente en disco,
            // según el servidor usado al desplegar el WAR puede estar en memoria únicamente
            InputStream content = externalContext().getResourceAsStream("/resources/html/mentions.html");
            System.out.println(content);
            String text = IOUtils.toString(content, "UTF-8");
            text = text.replace("{fullname}", profile.getFullName());
            text = text.replace("{name}", user.getUserName());
            text = text.replace("{text}", newPost.getText());
            text = text.replace("{url_post}", url_post);
            text = text.replace("{url_user}", url_user);

            mh.sendMail(email, "Chirper | Mention Notification", text);
        } catch (MessagingException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
