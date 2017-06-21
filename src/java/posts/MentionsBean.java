package posts;

import authentication.LoginManager;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.controllers.PostJpaController;
import jpa.entities.Post;
import jpa.entities.User;
import utils.ContextHandler;

/**
 *
 * @author Alberto G. Lagos
 */
@ManagedBean
@ViewScoped
@URLMapping(id="viewMentions", pattern="/mentions", viewId="/faces/post/mentions-view.xhtml")
public class MentionsBean {
    private EntityManagerFactory emf;
    ArrayList<Post> postMentions;
    
    @ManagedProperty("#{loginManager}")
    private LoginManager loginManager;
    
    public MentionsBean() {
    }

    public LoginManager getLoginManager() {
        return loginManager;
    }

    public void setLoginManager(LoginManager loginManager) {
        this.loginManager = loginManager;
    }
    
    @PostConstruct
    public void init() {
        emf = Persistence.createEntityManagerFactory("ChirperDbPU");
        postMentions = new ArrayList<>();
    }
    
    public ArrayList<Post> getPostMentions() {
        PostJpaController postC = new PostJpaController(emf);
        List<Post> posts = postC.findPostEntities();
        
        ExternalContext externalContext = ContextHandler.externalContext();
        User loggedUser = (User)externalContext.getSessionMap().get("user");
        String username = "@" + loggedUser.getUserName();
        
        for(Post p : posts) {
            if(p.getText().contains(username))
                postMentions.add(p);
        }
        return postMentions;
    }
}
