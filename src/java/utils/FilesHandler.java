package utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;

/**
 *
 * @author Alberto G. Lagos
 */
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
}
