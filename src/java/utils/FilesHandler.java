package utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import jpa.entities.Profile;

/**
 *
 * @author Alberto G. Lagos
 */
@ManagedBean
@RequestScoped
public class FilesHandler {
    
    public static boolean imageExists(String image) {
        try  {
            String imagePath = "faces/javax.faces.resource/" + image + "?ln=images";
            URL resource = FacesContext.getCurrentInstance().getExternalContext().getResource(imagePath);
            return (resource != null);
        } catch (MalformedURLException ex) {
            Logger.getLogger(FilesHandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public String getAvatar(Profile profile) {
        if(FilesHandler.imageExists(profile.getPhoto())) 
            return profile.getPhoto();
        return "user-blue.png";
    }
}
