package posts;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.controllers.PostJpaController;
import jpa.controllers.ProfileJpaController;
import jpa.entities.Post;

/**
 *
 * @author Alberto G. Lagos
 */
@ManagedBean
@ViewScoped
@URLMapping(id="viewPost", pattern="/post/#{ postId : postBean.postId}", viewId="/faces/post/post-view.xhtml")
public class PostBean {
    private final EntityManagerFactory emf;
    private Integer postId;
    private Post post;
    
    public PostBean() {
        emf = Persistence.createEntityManagerFactory("ChirperDbPU");
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
    
    @URLAction
    public String loadPostData() {        
        PostJpaController postC = new PostJpaController(emf);
        post = postC.findPost(postId);
        return null;
    }
    
    public List getPostReplies() {
        PostJpaController postC = new PostJpaController(emf);
        return postC.findPostReplies(postId);        
    }
}

