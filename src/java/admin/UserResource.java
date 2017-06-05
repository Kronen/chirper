package admin;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.controllers.UserJpaController;
import jpa.controllers.exceptions.IllegalOrphanException;
import jpa.controllers.exceptions.NonexistentEntityException;
import jpa.controllers.exceptions.PreexistingEntityException;
import jpa.entities.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.event.RowEditEvent;
import utils.MessageHandler;

/**
 *
 * @author Alberto G. Lagos
 */
@ManagedBean
@ViewScoped
public class UserResource {
    
    private EntityManagerFactory emf;
    private List<User> users;
    private User newUser;

    public UserResource() {
    }
        
    @PostConstruct
    public void init() {
        emf = Persistence.createEntityManagerFactory("ChirperDbPU");
        UserJpaController uC = new UserJpaController(emf);
        users = uC.findUserEntities();
        newUser = new User();
    }
    
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }
        
    public void create() {
        UserJpaController uC = new UserJpaController(emf);
        try {
            newUser.setPassword(DigestUtils.sha256Hex(newUser.getPassword()));
            uC.create(newUser);
            users = uC.findUserEntities();
            MessageHandler.addCustomMessage("User Created", newUser.getUserName(), null);
        } catch (PreexistingEntityException ex) {
            MessageHandler.addErrorMessage("User already exists.", null);
        } catch (Exception ex) {
            MessageHandler.addErrorMessage(
                "An unexpected error occurred while processing your request. Contact with administrator.",
                null
            );
        }
    }
    
    public void remove(User user) {
        try {            
            UserJpaController uC = new UserJpaController(emf);
            uC.destroy(user.getUserName());
            users = uC.findUserEntities();
            MessageHandler.addCustomMessage("User Deleted", user.getUserName(), null);
        } catch (IllegalOrphanException ex) {
            MessageHandler.addErrorMessage("Usercan't be deleted", null);
        } catch (NonexistentEntityException ex) {
            MessageHandler.addErrorMessage("User doesn't exist", null);
        }
    }
        
    public void onRowEdit(RowEditEvent event) {
        try {
            User user = ((User) event.getObject());
            UserJpaController uC = new UserJpaController(emf);
            uC.edit(user);
            MessageHandler.addCustomMessage("User Edited", user.getUserName(), null);
        } catch (NonexistentEntityException ex) {
            MessageHandler.addErrorMessage("User doesn't exist in database.", null);
        } catch (Exception ex) {
            MessageHandler.addErrorMessage(
                "An unexpected error occurred while processing your request. Contact with administrator.", 
                null
            );
        }
    }
}
