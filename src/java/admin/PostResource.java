package admin;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.controllers.PostJpaController;
import jpa.controllers.exceptions.NonexistentEntityException;
import jpa.entities.Post;
import org.primefaces.event.RowEditEvent;
import utils.MessageHandler;

/**
 *
 * @author Alberto G. Lagos
 */
@ManagedBean
@ViewScoped
public class PostResource {
    
    private EntityManagerFactory emf;
    private List<Post> posts;
    private Post newPost;

    public PostResource() {
    }
        
    @PostConstruct
    public void init() {
        emf = Persistence.createEntityManagerFactory("ChirperDbPU");
        PostJpaController postC = new PostJpaController(emf);
        posts = postC.findPostEntities();
        newPost = new Post();
    }
    
    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Post getNewPost() {
        return newPost;
    }

    public void setNewPost(Post newPost) {
        this.newPost = newPost;
    }
        
    public void create() {
        PostJpaController uC = new PostJpaController(emf);
        try {
            uC.create(newPost);
            posts = uC.findPostEntities();
            MessageHandler.addCustomMessage("Post Created", newPost.getId().toString(), null);
        } catch (Exception ex) {
            MessageHandler.addErrorMessage(
                "An unexpected error occurred while processing your request. Contact with administrator.",
                null
            );
        }
    }
    
    public void remove(Post post) {
        try {            
            PostJpaController uC = new PostJpaController(emf);
            uC.destroy(post.getId());
            posts = uC.findPostEntities();
            MessageHandler.addCustomMessage("Post Deleted", post.getId().toString(), null);
        } catch (NonexistentEntityException ex) {
            MessageHandler.addErrorMessage("Post doesn't exist", null);
        }
    }
        
    public void onRowEdit(RowEditEvent event) {
        try {
            Post post = ((Post) event.getObject());
            PostJpaController postC = new PostJpaController(emf);
            postC.edit(post);
            MessageHandler.addCustomMessage("Post Edited", post.getId().toString(), null);
        } catch (NonexistentEntityException ex) {
            MessageHandler.addErrorMessage("Post doesn't exist in database.", null);
        } catch (Exception ex) {
            MessageHandler.addErrorMessage(
                "An unexpected error occurred while processing your request. Contact with administrator.", 
                null
            );
        }
    }
}
