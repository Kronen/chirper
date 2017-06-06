package home;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import static java.lang.Math.toIntExact;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudItem;
import org.primefaces.model.tagcloud.TagCloudModel;
import utils.ContextHandler;
import utils.MailHandler;
import utils.MessageHandler;
import utils.TextHandler;

@ManagedBean
@ViewScoped
public class HomePage implements Serializable {
    
    transient private EntityManagerFactory emf;
    private User user;
    private Profile profile;
    private Post newPost, newReply;
    private TagCloudModel tagCloudModel;
    private UploadedFile image;

    public HomePage() {
          
    }
    
    @PostConstruct
    public void init() {
        emf = Persistence.createEntityManagerFactory("ChirperDbPU");
        newPost = new Post();
        newReply = new Post();
        user = (User)ContextHandler.externalContext().getSessionMap().get("user");
        if(user != null)
            loadProfile(user.getUserName()); 
        
        TagJpaController tC = new TagJpaController(emf);
        List<Object[]> tts = tC.findTrendingTopics(30);
        tagCloudModel = new DefaultTagCloudModel();
        for(Object[] tt : tts) {
            tagCloudModel.addTag(new DefaultTagCloudItem((String)tt[0], toIntExact((Long)tt[1])));
        }
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

    public Post getNewReply() {
        return newReply;
    }

    public void setNewReply(Post newReply) {
        this.newReply = newReply;
    }

    public UploadedFile getImage() {
        return image;
    }

    public void setImage(UploadedFile image) {
        this.image = image;
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
    
    public String publish() {        
        createChirp(newPost);
        processTags(newPost);
        processMentions(newPost);
        newPost = new Post();
        MessageHandler.addInfoMessage("Your Chirp has been published!", null);
        return null;
    }
    
    public String publishReply(int idPost) {        
        newReply.setOriginalPost(idPost);
        createChirp(newReply);
        processTags(newReply);
        processMentions(newReply);
        newReply = new Post();
        MessageHandler.addInfoMessage("Your reply has been published!", null);
        return null;
    }
   
    private void createChirp(Post post) {
        PostJpaController postC = new PostJpaController(emf);                
        post.setAuthor(profile);
        post.setPubDate(new Date());        
        if(image != null && !image.getFileName().isEmpty()) {
            String filename = saveImageToDisk();
            post.setImage(filename);
            image = null;
        }
        postC.create(post);        
    }
    
    private String saveImageToDisk() {          
        String filename = FilenameUtils.getBaseName(image.getFileName()); 
        String extension = FilenameUtils.getExtension(image.getFileName());            

        try {
            Path folder = Paths.get(System.getenv("UPLOAD_PATH"));
            Path file = Files.createTempFile(folder, filename + "-", "." + extension);
            InputStream input = image.getInputstream();
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
            return file.getFileName().toString();
        } catch (IOException ex) {
            MessageHandler.addErrorMessage("Error uploading file", null);
        }
        return null;
    }
    
    private void processMentions(Post post) {
        ProfileJpaController pC = new ProfileJpaController(emf);
        List<String> users = TextHandler.extractMentions(post.getText());
        for(String username : users) {
            Profile p = pC.findProfileByUserName(username.substring(1));
            if(p != null)
                sendMentionNotification(p.getEmail());
        }
    }
    
    private void processTags(Post post) {
        TagJpaController tagC = new TagJpaController(emf);
        List<String> tags = TextHandler.extractTags(post.getText());        
        
        // Add tags to database with the post associated
        try {
            tagC.createTagsWithPost(post, tags);
        } catch (Exception ex) {
            MessageHandler.addErrorMessage("Error registering tags for this post", null);
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
            // Uso getResourceAsStream puesto que el fichero no tiene pq estar físicamente en disco,
            // según el servidor usado al desplegar el WAR puede estar en memoria únicamente
            InputStream content = ContextHandler.externalContext().getResourceAsStream("/resources/html/mentions.html");
            System.out.println(content);
            String text = IOUtils.toString(content, "UTF-8");
            text = text.replace("{fullname}", profile.getFullName());
            text = text.replace("{name}", user.getUserName());
            text = text.replace("{text}", newPost.getText());
            text = text.replace("{url_post}", url_post);
            text = text.replace("{url_user}", url_user);

            mh.sendMail(email, "Chirper | Mention Notification", text);
        } catch (MessagingException ex) {
            MessageHandler.addErrorMessage("Error sending email. Try again later or contact the admin if the problem persist.", null);
        } catch (IOException ex) {
            MessageHandler.addErrorMessage("Error creating email template.", null);
        }
    }
    
    public TagCloudModel getTagCloudModel() {
        return tagCloudModel;
    }
     
    public void onSelect(SelectEvent event) {
        TagCloudItem item = (TagCloudItem) event.getObject();
    }
}
