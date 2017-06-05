package admin;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.controllers.TagJpaController;
import jpa.controllers.exceptions.NonexistentEntityException;
import jpa.entities.Tag;
import org.primefaces.event.RowEditEvent;
import utils.MessageHandler;

/**
 *
 * @author Alberto G. Lagos
 */
@ManagedBean
@ViewScoped
public class TagResource {
    
    private EntityManagerFactory emf;
    private List<Tag> tags;
    private Tag newTag;

    public TagResource() {
    }
        
    @PostConstruct
    public void init() {
        emf = Persistence.createEntityManagerFactory("ChirperDbPU");
        TagJpaController uC = new TagJpaController(emf);
        tags = uC.findTagEntities();
        newTag = new Tag();
    }
    
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Tag getNewTag() {
        return newTag;
    }

    public void setNewTag(Tag newTag) {
        this.newTag = newTag;
    }
        
    public void create() {
        TagJpaController uC = new TagJpaController(emf);
        try {
            uC.create(newTag);
            tags = uC.findTagEntities();
            MessageHandler.addCustomMessage("Tag Created", newTag.getTagName(), null);
        } catch (Exception ex) {
            MessageHandler.addErrorMessage(
                "An unexpected error occurred while processing your request. Contact with administrator.",
                null
            );
        }
    }
    
    public void remove(Tag tag) {
        try {            
            TagJpaController uC = new TagJpaController(emf);
            uC.destroy(tag.getId());
            tags = uC.findTagEntities();
            MessageHandler.addCustomMessage("Tag Deleted", tag.getTagName(), null);
        } catch (NonexistentEntityException ex) {
            MessageHandler.addErrorMessage("Tag doesn't exist", null);
        }
    }
        
    public void onRowEdit(RowEditEvent event) {
        try {
            Tag tag = ((Tag) event.getObject());
            TagJpaController uC = new TagJpaController(emf);
            uC.edit(tag);
            MessageHandler.addCustomMessage("Tag Edited", tag.getTagName(), null);
        } catch (NonexistentEntityException ex) {
            MessageHandler.addErrorMessage("Tag doesn't exist in database.", null);
        } catch (Exception ex) {
            MessageHandler.addErrorMessage(
                "An unexpected error occurred while processing your request. Contact with administrator.", 
                null
            );
        }
    }
}
