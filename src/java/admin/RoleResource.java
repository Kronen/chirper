package admin;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.controllers.RoleJpaController;
import jpa.controllers.exceptions.NonexistentEntityException;
import jpa.entities.Role;
import org.primefaces.event.RowEditEvent;
import utils.MessageHandler;

/**
 *
 * @author Alberto G. Lagos
 */
@ManagedBean
@ViewScoped
public class RoleResource {
    
    private EntityManagerFactory emf;
    private List<Role> roles;
    private Role newRole;

    public RoleResource() {
    }
        
    @PostConstruct
    public void init() {
        emf = Persistence.createEntityManagerFactory("ChirperDbPU");
        RoleJpaController uC = new RoleJpaController(emf);
        roles = uC.findRoleEntities();
        newRole = new Role();
    }
    
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Role getNewRole() {
        return newRole;
    }

    public void setNewRole(Role newRole) {
        this.newRole = newRole;
    }
        
    public void create() {
        RoleJpaController uC = new RoleJpaController(emf);
        try {
            uC.create(newRole);
            roles = uC.findRoleEntities();
            MessageHandler.addCustomMessage("Role Created", newRole.getRoleName(), null);
        } catch (Exception ex) {
            MessageHandler.addErrorMessage(
                "An unexpected error occurred while processing your request. Contact with administrator.",
                null
            );
        }
    }
    
    public void remove(Role role) {
        try {            
            RoleJpaController uC = new RoleJpaController(emf);
            uC.destroy(role.getRoleName());
            roles = uC.findRoleEntities();
            MessageHandler.addCustomMessage("Role Deleted", role.getRoleName(), null);
        } catch (NonexistentEntityException ex) {
            MessageHandler.addErrorMessage("Role doesn't exist", null);
        }
    }
        
    public void onRowEdit(RowEditEvent event) {
        try {
            Role role = ((Role) event.getObject());
            RoleJpaController uC = new RoleJpaController(emf);
            uC.edit(role);
            MessageHandler.addCustomMessage("Role Edited", role.getRoleName(), null);
        } catch (NonexistentEntityException ex) {
            MessageHandler.addErrorMessage("Role doesn't exist in database.", null);
        } catch (Exception ex) {
            MessageHandler.addErrorMessage(
                "An unexpected error occurred while processing your request. Contact with administrator.", 
                null
            );
        }
    }
}
