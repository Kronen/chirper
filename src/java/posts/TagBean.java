package posts;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.controllers.TagJpaController;
import jpa.entities.Tag;

/**
 *
 * @author Alberto G. Lagos
 */
@ManagedBean
@ViewScoped
@URLMapping(id="viewTag", pattern="/tag/#{ tagId : tagBean.tagId}", viewId="/faces/post/tag-view.xhtml")
public class TagBean {
    private EntityManagerFactory emf;
    private Integer tagId;
    private Tag tag;
    private List taggedPosts;
    
    public TagBean() {
    }
    
    @PostConstruct
    public void init() {
        emf = Persistence.createEntityManagerFactory("ChirperDbPU");
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public List getTaggedPosts() {
        return taggedPosts;
    }

    public void setTaggedPosts(List taggedPosts) {
        this.taggedPosts = taggedPosts;
    }
    
    @URLAction
    public String loadTaggedPosts() {
        TagJpaController tagC = new TagJpaController(emf);
        tag = tagC.findTag(tagId);
        taggedPosts = new ArrayList(tag.getPostCollection());
        return null;
    }
}
