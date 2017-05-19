package authentication;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import jpa.entities.User;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.controllers.ProfileJpaController;
import jpa.controllers.UserJpaController;
import jpa.controllers.exceptions.PreexistingEntityException;
import jpa.entities.Profile;
import org.apache.commons.codec.digest.DigestUtils;
import utils.MessageHandler;

/**
 * This class manages the login page for JSF.
 * It allows logging in and out, as well as retrieving the current user (if any) and more.
 *
 * It also works as a substitute for the j_security_check/j_username/j_password form (which is buggy!). Having
 * this bean, our login form can be pure JSF, allowing a more fine-grained control and all (such as embedding
 * validation/error messages in the form itself; custom {@link User} instances building and so on).
 */
@ManagedBean
@ViewScoped
@URLMapping(id="homepage", pattern="/", viewId="#{loginManager.getViewPath}")
public class LoginManager implements Serializable {

    private static final String HOMEPAGE = "/";
    private static final String PAGE_AFTER_LOGOUT = HOMEPAGE; // Another good option is the login page back again
   
    private final EntityManagerFactory emf;
    private String username;
    private String password;
    private String email;
    private String forwardUrl;
    

    public LoginManager() {
        emf = Persistence.createEntityManagerFactory("ChirperDbPU");
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @PostConstruct
    public void init() {
        this.forwardUrl = extractRequestedUrlBeforeLogin();
    }

    private String extractRequestedUrlBeforeLogin() {
        ExternalContext externalContext = externalContext();
        String requestedUrl = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);
        if (requestedUrl == null) {
            return externalContext.getRequestContextPath() + HOMEPAGE;
        }
        String queryString = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_QUERY_STRING);
        return requestedUrl + (queryString == null ? "" : "?" + queryString);
    }

    private ExternalContext externalContext() {
        return facesContext().getExternalContext();
    }

    private FacesContext facesContext() {
        return FacesContext.getCurrentInstance();
    }

    /**
     * Performs user login accordingly to the username/password set.
     *
     * @throws IOException from {@link ExternalContext#redirect(String)}
     */
    public void login() throws IOException {
        ExternalContext externalContext = externalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        try {
            String hashed_pass = DigestUtils.sha256Hex(password);
            request.login(username, hashed_pass);
            UserJpaController uC = new UserJpaController(emf);
            User user = (User) uC.findUser(username);
            externalContext.getSessionMap().put("user", user);
            externalContext.redirect(forwardUrl);
        } catch (ServletException e) {
            /*
             * The ServletException is thrown if the configured login mechanism does not support
             * username password authentication or if validation of the provided username
             * and password fails.
             */
            MessageHandler.addErrorMessage("The password you introduced for user " + username + " is incorrect.", null);
        }
    }
    
    public void signup() {
        try {
            UserJpaController uC = new UserJpaController(emf);
            ProfileJpaController pC = new ProfileJpaController(emf);
            
            User user = new User(username, DigestUtils.sha256Hex(password));            
            uC.create(user);
            
            Profile profile = new Profile();
            profile.setUser(user);
            profile.setEmail(email);
            pC.create(profile);            
            login();
        } catch (PreexistingEntityException ex) {
            MessageHandler.addErrorMessage("This username is already in use. Try another username.", null);
        } catch (Exception e) {
            MessageHandler.addErrorMessage("Failed to create account: " + e.getLocalizedMessage(), null);
        }
    }


    /**
     * Invalidates the current session, logging out the current user.
     *
     * @throws IOException from {@link ExternalContext#redirect(String)}
     */
    public void logout() throws IOException {
        ExternalContext externalContext = externalContext();
        externalContext.invalidateSession();
        externalContext.redirect(externalContext.getRequestContextPath() + PAGE_AFTER_LOGOUT);
    }


    /**
     * Makes the current logged in user available through #{loginManager.user}. Notice as the user is also placed in
     * the session map, it also is available through #{user}.
     *
     * @return The currently logged in {@link User}, or {@code null} if no user is logged in.
     */
    public User getUser() {
        FacesContext context = facesContext();
        ExternalContext externalContext = context.getExternalContext();
        return (User) externalContext.getSessionMap().get("user");
    }


    /**
     * Verifies if there is a currently logged in user.
     *
     * @return {@code true} if there's a logged in {@link User}, {@code false} otherwise.
     */
    public boolean isUserLoggedIn() {
        return getUser() != null;
    }


    /**
     * Verifies if the currently logged in user is in the given Role.
     *
     * @param role The Role to verify if the user has.
     * @return {@code true} if the user is logged in and has the given Role. {@code false} otherwise.
     */
    public boolean isUserInRole(String role) {
        FacesContext context = facesContext();
        ExternalContext externalContext = context.getExternalContext();
        return externalContext.isUserInRole(role);
    }
    
    /**
     * Decides which is the next page to redirect the user depending on successful/unsuccessful login. If the {@link User} 
     * is logged in he will be redirected to the home page, otherwise he will stay in the login page.
     *
     * @return the url the user will be redirected to.
     */
    public String getViewPath() {
        if(isUserLoggedIn())
            return "/faces/home/home.xhtml";
        return "/faces/login.xhtml";
    }

}