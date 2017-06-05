package admin;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.controllers.UserRoleJpaController;
import jpa.controllers.exceptions.NonexistentEntityException;
import jpa.entities.UserRole;
import org.primefaces.event.RowEditEvent;
import utils.MessageHandler;

/**
 *
 * @author Alberto G. Lagos
 */
@ManagedBean
@ViewScoped
public class UserRoleResource {
    
    private EntityManagerFactory emf;
    private List<UserRole> userRoles;
    private UserRole newUserRole;

    public UserRoleResource() {
    }
        
    @PostConstruct
    public void init() {
        emf = Persistence.createEntityManagerFactory("ChirperDbPU");
        UserRoleJpaController uC = new UserRoleJpaController(emf);
        userRoles = uC.findUserRoleEntities();
        newUserRole = new UserRole();
    }
    
    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public UserRole getNewUserRole() {
        return newUserRole;
    }

    public void setNewUserRole(UserRole newUserRole) {
        this.newUserRole = newUserRole;
    }
        
    public void create() {
        UserRoleJpaController uC = new UserRoleJpaController(emf);
        try {
            uC.create(newUserRole);
            userRoles = uC.findUserRoleEntities();
            MessageHandler.addCustomMessage("UserRole Created", newUserRole.toString(), null);
        } catch (Exception ex) {
            MessageHandler.addErrorMessage(
                "An unexpected error occurred while processing your request. Contact with administrator.",
                null
            );
        }
    }
    
    public void remove(UserRole userRole) {
        try {            
            UserRoleJpaController uC = new UserRoleJpaController(emf);
            uC.destroy(userRole.getUserRolePK());
            userRoles = uC.findUserRoleEntities();
            MessageHandler.addCustomMessage("UserRole Deleted", userRole.toString(), null);
        } catch (NonexistentEntityException ex) {
            MessageHandler.addErrorMessage("UserRole doesn't exist", null);
        }
    }
        
    public void onRowEdit(RowEditEvent event) {
        try {
            UserRole userRole = ((UserRole) event.getObject());
            UserRoleJpaController uC = new UserRoleJpaController(emf);
            uC.edit(userRole);
            MessageHandler.addCustomMessage("UserRole Edited", userRole.toString(), null);
        } catch (NonexistentEntityException ex) {
            MessageHandler.addErrorMessage("UserRole doesn't exist in database.", null);
        } catch (Exception ex) {
            MessageHandler.addErrorMessage(
                "An unexpected error occurred while processing your request. Contact with administrator.", 
                null
            );
        }
    }
}
